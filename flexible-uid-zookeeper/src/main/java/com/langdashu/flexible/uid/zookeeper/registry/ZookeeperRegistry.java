package com.langdashu.flexible.uid.zookeeper.registry;

import com.alibaba.fastjson.JSON;
import com.langdashu.flexible.uid.core.configuration.Configurer;
import com.langdashu.flexible.uid.core.constant.GlobalConstant;
import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.model.UidConfiguration;
import com.langdashu.flexible.uid.core.model.UidLocation;
import com.langdashu.flexible.uid.core.model.UidRegistryContext;
import com.langdashu.flexible.uid.core.observer.UidLocationBroadcaster;
import com.langdashu.flexible.uid.core.specs.Registry;
import com.langdashu.flexible.uid.core.specs.UidGenerator;
import com.langdashu.flexible.uid.core.util.EnvUtil;
import com.langdashu.flexible.uid.zookeeper.constant.PathConstant;
import com.langdashu.flexible.uid.zookeeper.core.ZookeeperAssistant;
import com.langdashu.flexible.uid.zookeeper.hook.Result;
import com.langdashu.flexible.uid.zookeeper.model.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Zookeeper注册器
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class ZookeeperRegistry implements Registry {

    private final Logger logger = LoggerFactory.getLogger(ZookeeperRegistry.class);

    private ZookeeperAssistant zkAssistant;
    private UidConfiguration configuration;
    private final Registration registration = new Registration();

    @Override
    public void init(UidRegistryContext uidRegistryContext) throws UidException {
        configuration = Configurer.getInstance().getConfiguration();
        zkAssistant = ZookeeperAssistant.getInstance(configuration.getZookeeperAddress());
    }

    @Override
    public UidLocation register(String ip, int port) throws UidException {
        UidLocation uidLocation;
        try{
            // 1.注册
            doRegister(ip, port);

            // 2.解析
            uidLocation = doAnalyse();

            // 3.监听
            doListen();
        } catch (UidException e) {
            throw e;
        }catch (Exception e) {
            throw new UidException(e);
        }
        return uidLocation;
    }

    @Override
    public boolean available(UidGenerator generator) {
        return true;
    }

    private void doRegister(String ip, int port) throws UidException {
        String dataCenter = EnvUtil.getEnvVariable(configuration.getEnvVariable().getDataCenter());
        if(Objects.isNull(dataCenter) || dataCenter.isEmpty()) {
            dataCenter = GlobalConstant.DEFAULT_DATA_CENTER;
        }
        String worker = getServiceAddress(ip, port);

        registration.setIp(ip);
        registration.setPort(port);
        registration.setDataCenter(dataCenter);
        registration.setWorker(worker);
        registration.setCreatedBy(System.currentTimeMillis());
        registration.setUpdatedBy(registration.getCreatedBy());
        registration.setVersion(1);

        zkAssistant.createEphSeqNode(PathConstant.WORKER, JSON.toJSONString(registration));
    }

    private UidLocation doAnalyse() throws UidException {
        UidLocation uidLocation = UidLocation.DEFAULT;

        // 1.获取列表
        Map<String,Registration> registrations =  getWorkers();

        // 2.解析datacenterId
        long dataCenterId = analyseDataCenterId(registrations);

        // 3.解析workerId
        long workerId = analyseWorkerId(registrations);

        // 4.更新注册信息
        refreshWorker();

        uidLocation.setDataCenterId(dataCenterId);
        uidLocation.setWorkerId(workerId);
        return uidLocation;
    }

    private void doListen() throws UidException {
        zkAssistant.watchChildren(PathConstant.WORKERS, () -> {
            Result result =  new Result();
            try{
                UidLocationBroadcaster.getInstance().broadcast(doAnalyse());
            }catch (UidException e) {
                result.setThrowable(e);
            }
            return result;
        });
    }

    private Map<String,Registration> getWorkers() throws UidException {
        Map<String,String> childrenData =  zkAssistant.getChildrenNodePathAndData(PathConstant.WORKERS);
        for(String path : childrenData.keySet()) {
            if(logger.isDebugEnabled()){
                logger.debug("{}->{}", path, childrenData.get(path));
            }
        }

        // 数据转换
        Map<String,Registration> registrations = new TreeMap<>();
        for(String key : childrenData.keySet()) {
            Registration value = JSON.parseObject(childrenData.get(key), Registration.class);
            value.setPath(PathConstant.ROOT + "/" + key);
            registrations.put(key, value);
        }
        for(String path : registrations.keySet()) {
            if(logger.isDebugEnabled()){
                logger.debug("{}->{}", path, registrations.get(path));
            }
        }
        return registrations;
    }

    public long analyseDataCenterId(Map<String,Registration> registrations) {
        Set<String> dataCenterSet = new HashSet<>();
        List<String> dataCenterList = new ArrayList<>();
        for(String path : registrations.keySet()) {
            Registration reg = registrations.get(path);
            if(reg == null || dataCenterSet.contains(reg.getDataCenter())) {
                continue;
            }
            dataCenterSet.add(reg.getDataCenter());
            dataCenterList.add(reg.getDataCenter());
            if(registration.getDataCenter().equals(reg.getDataCenter())) {
                break;
            }
        }

        long dataCenterId = dataCenterList.size() - 1;
        if(logger.isDebugEnabled()){
            logger.debug("dataCenterId: {}", dataCenterId);
        }

        registration.setDataCenterId(dataCenterId);
        return dataCenterId;
    }

    public long analyseWorkerId(Map<String,Registration> registrations) {
        List<String> workerList = new ArrayList<>();
        for(String path : registrations.keySet()) {
            Registration reg = registrations.get(path);
            if(Objects.isNull(reg)) {
                continue;
            }
            if(!registration.getDataCenter().equals(reg.getDataCenter())) {
                continue;
            }
            workerList.add(reg.getWorker());
            if(registration.getWorker().equals(reg.getWorker())) {
                registration.setPath(PathConstant.WORKERS + "/" + path);
                break;
            }
        }

        int workerId = workerList.size() - 1;
        if(logger.isDebugEnabled()){
            logger.debug("workerId: {}", workerId);
        }

        registration.setWorkerId(workerId);
        return workerId;
    }

    private void refreshWorker() throws UidException {
        registration.setVersion(registration.getVersion() + 1);
        registration.setUpdatedBy(System.currentTimeMillis());
        if(logger.isDebugEnabled()){
            logger.debug("refreshWorker: {}", registration);
        }
        zkAssistant.updateNode(registration.getPath(), JSON.toJSONString(registration));
    }

}
