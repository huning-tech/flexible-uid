package com.langdashu.flexible.uid.core.generator.impl;

import com.langdashu.flexible.uid.core.configuration.Configurer;
import com.langdashu.flexible.uid.core.generator.UidLongGenerator;
import com.langdashu.flexible.uid.core.model.UidConfiguration;
import com.langdashu.flexible.uid.core.observer.UidLocationBroadcaster;
import com.langdashu.flexible.uid.core.observer.UidLocationListener;
import com.langdashu.flexible.uid.core.specs.Registry;
import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.model.UidLocation;
import com.langdashu.flexible.uid.core.model.UidRegistryContext;
import com.langdashu.flexible.uid.core.model.UidGeneratorInitParam;
import com.langdashu.flexible.uid.core.algorithm.Snowflake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Service;

import java.util.Iterator;
import java.util.Objects;

public class UidSnowflakeGenerator extends UidLongGenerator implements UidLocationListener {

    private final Logger logger = LoggerFactory.getLogger(UidSnowflakeGenerator.class);

    private UidLocation uidLocation;
    private Snowflake snowflake;
    private Registry registry;
    private UidConfiguration uidConfiguration;

    @Override
    public void init(UidGeneratorInitParam param) throws UidException {
        UidRegistryContext context = new UidRegistryContext();
        context.setIp(param.getIp());
        context.setPort(param.getPort());
        context.setEnv(param.getEnv());
        Iterator<Registry> registries = Service.providers(Registry.class);
        while(registries.hasNext()) {
            registry = registries.next();
            if(Objects.nonNull(registry) && registry.available(this)) {
                registry.init(context);
                this.uidLocation = registry.register(context.getIp(), context.getPort());
                doCheck(this.uidLocation);
                break;
            }
        }
        uidConfiguration = Configurer.getInstance().getConfiguration();
        UidLocationBroadcaster.getInstance().register(this);
    }

    @Override
    public Long generateLong() throws UidException {
        if(Objects.nonNull(snowflake)) {
            logger.info("generateLong-location:{},{}", uidLocation.getDataCenterId(), uidLocation.getWorkerId());
            return snowflake.nextId();
        }
        if(Objects.isNull(this.uidLocation)) {
            throw new UidException("location is null, register failed!");
        }
        Long startTimeMillis = uidConfiguration.getSnowflakeStartTimeMillis();
        if(Objects.isNull(startTimeMillis)) {
            snowflake = new Snowflake(this.uidLocation.getDataCenterId(), this.uidLocation.getWorkerId());
        }else{
            logger.info("startTimeMillis:{}", startTimeMillis);
            snowflake = new Snowflake(this.uidLocation.getDataCenterId(), this.uidLocation.getWorkerId(), startTimeMillis);
        }
        return generateLong();
    }

    @Override
    public void onEvent(UidLocation uidLocation) {
        if(logger.isDebugEnabled()) {
            logger.debug("onEvent: {}", uidLocation);
        }
        this.uidLocation = uidLocation;
    }

    private void doCheck(UidLocation uidLocation) throws UidException {
        if(Objects.isNull(uidLocation)) {
            throw new UidException("location is null, register failed!");
        }
        if(uidLocation.getDataCenterId() < 0 || uidLocation.getDataCenterId() > 31) {
            throw new UidException("invalid data center id, register failed!");
        }
        if(uidLocation.getWorkerId() < 0 || uidLocation.getWorkerId() > 31) {
            throw new UidException("invalid worker id, register failed!");
        }
    }

    @Override
    public void destroy() throws UidException {
        if(Objects.nonNull(registry)) {
            registry.destroy();
        }
    }

}
