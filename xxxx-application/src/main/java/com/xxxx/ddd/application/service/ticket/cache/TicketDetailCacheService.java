package com.xxxx.ddd.application.service.ticket.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xxxx.ddd.domain.model.entity.TicketDetail;
import com.xxxx.ddd.domain.service.TicketDetailDomainService;
import com.xxxx.ddd.infrastructure.cache.redis.RedisInfrasService;
import com.xxxx.ddd.infrastructure.distributed.redisson.RedisDistributedLocker;
import com.xxxx.ddd.infrastructure.distributed.redisson.RedisDistributedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TicketDetailCacheService {
    @Autowired
    private RedisDistributedService redisDistributedService;
    @Autowired // Declare cache
    private RedisInfrasService redisInfrasService;
    @Autowired
    private TicketDetailDomainService ticketDetailDomainService;

    // private static final Logger log = LoggerFactory.getLogger(TicketDetailCacheService.class);
    // use guava
    // Local cache for ticket detail
    private final static Cache<Long, TicketDetail> ticketDetailLocalCache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .concurrencyLevel(12)
            .expireAfterWrite(100, TimeUnit.MINUTES)
            .build();

    // Generate event key for ticket item
    private String genEventItemKey(Long itemId) {
        return "PRO_TICKET:ITEM:" + itemId;
    }

    public TicketDetail getTicketDefaultCacheNormal(Long id, Long version) {
        // 1. Get ticket item by redis
        TicketDetail ticketDetail = redisInfrasService.getObject(genEventItemKey(id), TicketDetail.class);
        // 2. Yes -> Hit cache (có dữ liệu trong cache)
        if (ticketDetail != null) {
//            log.info("FROM CACHE {}, {}, {}", id, version, ticketDetail);
            return ticketDetail;
        }
        // 3. If no, Missing cache (không có dữ liệu trong cache)

        // 3.1 Get data from DBS
        ticketDetail = ticketDetailDomainService.getTicketDetailById(id);
//        log.info("FROM DBS {}, {}, {}", id, version, ticketDetail);

        // 3.2 check ticketItem
        if (ticketDetail != null) {
            // 3.3 Set cache
            redisInfrasService.setObject(genEventItemKey(id), ticketDetail);
        }
        return ticketDetail;
        // => Code có vấn đề, nếu ticketItem là null thì sẽ query mãi hay sao
    }

    // CHƯA VIP - REVIEW SẼ PHẢI LÀM LẠI
    public TicketDetail getTicketDetailCacheVip(Long id, Long version) {
        // 1. Get ticket item by redis
        TicketDetail ticketDetail = redisInfrasService.getObject(genEventItemKey(id), TicketDetail.class);
//        log.info("FROM CACHE {}, {}, {}", id, version, ticketDetail);
        // 2. Yes -> Hit cache
        if (ticketDetail != null) {
//            log.info("FROM CACHE {}, {}, {}", id, version, ticketDetail);
            return ticketDetail;
        }
        // 3. If no, Missing cache
//        log.info("CACHE NO EXIST, START GET DB AND SET CACHE->, {}, {} ", id, version);
        // 3.1 Create a lock process by key
        RedisDistributedLocker locker = redisDistributedService.getDistributedLocker("PRO_LOCK_KEY_ITEM" + id);
        try {
            // 1. create lock
            boolean isLock = locker.tryLock(1, 5, TimeUnit.SECONDS);
            // Lưu ý cho dù thành công hay thất bại cũng phải unLock, bằng mọi giá phải unlock
            if (!isLock) {
                log.info("LOCK WAIT ITEM PLEASE, ..... {}", version);
                return ticketDetail;
            }
            // stub...
            // get cache
            // Check cache has been hit
            ticketDetail = redisInfrasService.getObject(genEventItemKey(id), TicketDetail.class);
            // 2. yes -> Hit cache
            if (ticketDetail != null) {
//                log.info("FROM CACHE GOOD A {}, {}, {}", id, version, ticketDetail);
                return ticketDetail;
            }
            // 3. If no, Missing cache query from DBS

            ticketDetail = ticketDetailDomainService.getTicketDetailById(id);
//            log.info("FROM DBS {}, {}, {}", id, version, ticketDetail);
            if (ticketDetail == null) {
//                log.info("TICKET DETAIL NOT EXIST, {}", id);
                // set
                redisInfrasService.setObject(genEventItemKey(id), ticketDetail);
                return ticketDetail;
            }

            // If ticketDetail is not null, set cache
            redisInfrasService.setObject(genEventItemKey(id), ticketDetail);
            // set local cache
            return ticketDetail;
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            // Lưu Ý: Cho dù thành công hay thất bại cũng phải unLock, bằng mọi giá phải unlock
            locker.unlock();
        }
    }
}
