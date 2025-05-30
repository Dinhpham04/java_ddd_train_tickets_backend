package com.xxxx.ddd.infrastructure.distributed.redisson.impl;

import com.xxxx.ddd.infrastructure.distributed.redisson.RedisDistributedLocker;
import com.xxxx.ddd.infrastructure.distributed.redisson.RedisDistributedService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisDistributedLockerImpl implements RedisDistributedService {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public RedisDistributedLocker getDistributedLocker(String lockKey) {
        // RLock là một interface chứa các phương thức làm việc với redisson
        // Tạo một distributed lock mới hoặc lấy lock đã tồn tại với key chỉ định
        RLock rLock = redissonClient.getLock(lockKey);

        return new RedisDistributedLocker() {
            @Override
            public boolean tryLock(long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
                boolean isLockSuccess = rLock.tryLock(waitTime, leaseTime, timeUnit);
//                log.info("{} get lock result: {}", lockKey, isLockSuccess);
                return isLockSuccess;
            }

            @Override
            public void lock(long leaseTime, TimeUnit timeUnit) {
                rLock.lock(leaseTime, timeUnit);
            }

            @Override
            public void unlock() {
                if (isLocked() && isHelpByCurrentThread()) {
                    rLock.unlock();
                }
            }

            @Override
            public boolean isLocked() {
                return rLock.isLocked();
            }

            @Override
            public boolean isHelpByThread(long threadId) {
                return rLock.isHeldByThread(threadId);
            }

            @Override
            public boolean isHelpByCurrentThread() {
                return rLock.isHeldByCurrentThread();
            }
        };
    }
}
