package com.demo.service.service.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Slf4j
@Service
public class RedisLockService {

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    public Lock getLockVo(String key) {
        try {
            Lock lock = redisLockRegistry.obtain(key);
            boolean locked = lock.tryLock(5, TimeUnit.SECONDS);
            if(locked) {
                return lock;
            }
        } catch (Exception ex){
            log.error("[Lock error]", ex);
        }
        return null;
    }

    public void unlock(Lock lock) {
        try{
            lock.unlock();
        } catch (Exception ex){
            log.error("[unlock error] ",  ex);
        }
    }
}
