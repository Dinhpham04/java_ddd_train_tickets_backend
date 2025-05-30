package com.xxxx.ddd.infrastructure.distributed.redisson;

import java.util.concurrent.TimeUnit;

public interface RedisDistributedLocker {
    // lấy khóa với thời gian chờ tối đa và thời gian giữ khóa
    boolean tryLock(long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException;

    // lấy khóa và chặn cho đến khi lấy được khóa
    void lock(long leaseTime, TimeUnit timeUnit);

    // giải phóng khóa
    void unlock();

    // Kiểm tra khóa có đang bị giữ hay không
    boolean isLocked();

    // kiểm tra xem khoá có bị dữ bởi thread có id cụ thể nào không
    boolean isHelpByThread(long threadId);

    // kiểm tra xem khóa có đang bị giữ bởi thread hiện tại không
    boolean isHelpByCurrentThread();
}
