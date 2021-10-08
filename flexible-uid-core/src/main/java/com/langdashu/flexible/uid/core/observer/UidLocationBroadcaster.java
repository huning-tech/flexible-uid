package com.langdashu.flexible.uid.core.observer;

import com.langdashu.flexible.uid.core.model.UidLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * uid定位符广播器
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class UidLocationBroadcaster {

    private final Logger logger = LoggerFactory.getLogger(UidLocationBroadcaster.class);

    private final List<UidLocationListener> listeners = new ArrayList<>();

    private static final UidLocationBroadcaster instance = new UidLocationBroadcaster();

    private UidLocationBroadcaster(){}

    public static UidLocationBroadcaster getInstance() {
        return instance;
    }

    public void broadcast(UidLocation uidLocation){
        logger.info("broadcast: {}", uidLocation);
        if(uidLocation == null) {
            return;
        }
        listeners.forEach(listener -> listener.onEvent(uidLocation));
    }

    public void register(UidLocationListener listener) {
        if(listeners.contains(listener)) {
            return;
        }
        listeners.add(listener);
    }

}
