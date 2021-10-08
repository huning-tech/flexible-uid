package com.langdashu.flexible.uid.core.factory;

import com.langdashu.flexible.uid.core.constant.Env;
import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.model.UidGeneratorInitParam;
import com.langdashu.flexible.uid.core.specs.UidGenerator;
import sun.misc.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Uid生成器工厂
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
@SuppressWarnings("unchecked,rawtypes")
public class UidGeneratorFactory {

    private final Map<Class,Object> cache = new HashMap<>();

    private  UidGeneratorFactory(){}

    private UidGeneratorInitParam param;

    public <T extends UidGenerator> T getGenerator(Class<T> clazz) throws UidException {
        if(Objects.nonNull(cache.get(clazz))) {
            return (T)cache.get(clazz);
        }
        Iterator<UidGenerator> generators = Service.providers(UidGenerator.class);
        while(generators.hasNext()) {
            UidGenerator generator = generators.next();
            if(generator.getClass().isAssignableFrom(clazz)) {
                generator.init(param);
                cache.put(clazz, generator);
                return (T)generator;
            }
        }
        return null;
    }

    public static UidGeneratorFactory build(String ip, int port) throws UidException {
        return build(ip, port, Env.NONE);
    }

    public static UidGeneratorFactory build(String ip, int port, Env env) throws UidException {
        UidGeneratorFactory factory = new UidGeneratorFactory();
        factory.setParam(new UidGeneratorInitParam.Builder()
                .ip(ip)
                .port(port)
                .env(env)
                .build());
        return factory;
    }

    public UidGeneratorInitParam getParam() {
        return param;
    }

    public void setParam(UidGeneratorInitParam param) {
        this.param = param;
    }

}
