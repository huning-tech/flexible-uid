package com.langdashu.flexible.uid.core.generator.impl;

import com.langdashu.flexible.uid.core.generator.UidStringGenerator;

import java.util.UUID;

public class UidUUIDGenerator extends UidStringGenerator {

    @Override
    public String generateString() {
        return UUID.randomUUID().toString();
    }

}
