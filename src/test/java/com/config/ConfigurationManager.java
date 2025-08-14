package com.config;

import org.aeonbits.owner.ConfigCache;

import java.io.ObjectInputFilter;

public class ConfigurationManager {
    public static Configuration config(){
        return ConfigCache.getOrCreate(Configuration.class);
    }
}
