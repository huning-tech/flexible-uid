package com.langdashu.flexible.uid.xml.registry;

import com.langdashu.flexible.uid.core.constant.Env;
import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.io.ResourceLoader;
import com.langdashu.flexible.uid.core.model.UidDataCenter;
import com.langdashu.flexible.uid.core.model.UidLocation;
import com.langdashu.flexible.uid.core.model.UidRegistryContext;
import com.langdashu.flexible.uid.core.model.UidWorker;
import com.langdashu.flexible.uid.core.specs.Registry;
import com.langdashu.flexible.uid.core.specs.UidGenerator;
import com.langdashu.flexible.uid.xml.constant.FileConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * XML注册器
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class XmlRegistry implements Registry {

    private final Logger logger = LoggerFactory.getLogger(XmlRegistry.class);

    private Map<String, UidLocation> locationMap = new HashMap<>();

    @Override
    public void init(UidRegistryContext context) throws UidException {
        String filename = FileConstant.DEFAULT_XML_FILE_NAME;
        if(!Env.NONE.equals(context.getEnv())) {
            filename = String.format(FileConstant.ENV_XML_FILE_NAME, context.getEnv().getCode());
        }
        if(logger.isDebugEnabled()) {
            logger.debug("init-filename:{}", filename);
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is =  ResourceLoader.getResourceAsStream(filename);
            Document document = builder.parse(is);
            Node rootNode = getRoot(document.getElementsByTagName("uid"));
            convert(getDatacenters(rootNode));
            if(logger.isDebugEnabled()) {
                logger.debug("init-locationMap:{}", locationMap);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new UidException(e);
        }
    }

    private Node getRoot(NodeList list) {
        return list.item(0);
    }

    private List<UidDataCenter> getDatacenters(Node rootNode) {
        NodeList datacenterNodes = rootNode.getChildNodes();
        List<UidDataCenter> uidDataCenters = new ArrayList<>();
        for (int i = 0; i <datacenterNodes.getLength() ; i++) {
            if (datacenterNodes.item(i).getNodeType()== Node.ELEMENT_NODE) {
                UidDataCenter uidDatacenter = new UidDataCenter();
                Node datacenterNode = datacenterNodes.item(i);
                NamedNodeMap namedNodeMap = datacenterNode.getAttributes();
                Node codeAttr = namedNodeMap.getNamedItem(FileConstant.NODE_NAME_CODE);
                Node groupAttr = namedNodeMap.getNamedItem(FileConstant.NODE_NAME_GROUP);
                if(Objects.nonNull(codeAttr)) {
                    uidDatacenter.setCode(codeAttr.getNodeValue());
                }
                if(Objects.nonNull(groupAttr)){
                    uidDatacenter.setGroup(groupAttr.getNodeValue());
                }
                uidDatacenter.setWorkers(getWorkers(datacenterNode));
                uidDataCenters.add(uidDatacenter);
            }
        }
        return uidDataCenters;
    }

    private List<UidWorker> getWorkers(Node datacenterNode) {
        NodeList workerNodes = datacenterNode.getChildNodes();
        List<UidWorker> uidWorkers = new ArrayList<>();
        for (int i = 0; i < workerNodes.getLength(); i++) {
            if (workerNodes.item(i).getNodeType()== Node.ELEMENT_NODE) {
                UidWorker uidWorker = new UidWorker();
                Node workerNode = workerNodes.item(i);
                NamedNodeMap namedNodeMap = workerNode.getAttributes();
                Node ipAttr = namedNodeMap.getNamedItem(FileConstant.NODE_ATTR_IP);
                Node portAttr = namedNodeMap.getNamedItem(FileConstant.NODE_ATTR_PORT);
                if(Objects.nonNull(ipAttr)) {
                    uidWorker.setIps(analysisIp(ipAttr.getNodeValue()));
                }
                if(Objects.nonNull(portAttr)){
                    uidWorker.setPort(Integer.parseInt(portAttr.getNodeValue()));
                }
                uidWorkers.add(uidWorker);
            }
        }
        return uidWorkers;
    }


    private List<String> analysisIp(String ipRawValue) {
        if(ipRawValue.contains(FileConstant.SYMBOL_COMMA)) {
            return Arrays.stream(ipRawValue.split(FileConstant.SYMBOL_COMMA)).collect(Collectors.toList());
        }else {
            return Collections.singletonList(ipRawValue);
        }
    }

    private void convert(List<UidDataCenter> uidDataCenters) {
         for(int i = 0; i < uidDataCenters.size(); i++) {
             UidDataCenter uidDatacenter = uidDataCenters.get(i);
             List<UidWorker> uidWorkers = uidDatacenter.getWorkers();
             for(int j = 0; j < uidWorkers.size(); j++) {
                 UidWorker uidWorker = uidWorkers.get(j);
                 construct(i, j, uidWorker.getPort(), uidWorker.getIps());
             }
         }
    }

    private void construct(long i, long j, int port, List<String> ips) {
        for(String ip : ips) {
            locationMap.put(getServiceAddress(ip, port), new UidLocation(i, j));
        }
    }

    @Override
    public UidLocation register(String ip, int port) throws UidException {
        return locationMap.get(getServiceAddress(ip, port));
    }

    @Override
    public boolean available(UidGenerator generator) {
        return true;
    }

}
