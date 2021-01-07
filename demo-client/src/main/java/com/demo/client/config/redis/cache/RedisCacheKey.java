package com.demo.client.config.redis.cache;

public enum RedisCacheKey {
    TOKEN(CacheKey.TOKEN, 60 * 60),
    ;

    RedisCacheKey(){
        //do nothing
    }

    RedisCacheKey(String name, int keepSecond){
        this.name = name;
        this.keepSecond = keepSecond;
    }

    private String name;
    private int keepSecond;

    public String getName() {
        return name;
    }

    public int getKeepSecond() {
        return keepSecond;
    }
}
