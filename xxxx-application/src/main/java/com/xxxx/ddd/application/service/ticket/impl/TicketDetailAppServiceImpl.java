package com.xxxx.ddd.application.service.ticket.impl;

import com.xxxx.ddd.application.model.TicketDetailDTO;
import com.xxxx.ddd.application.model.cache.TicketDetailCache;
import com.xxxx.ddd.application.service.ticket.TicketDetailAppService;
import com.xxxx.ddd.application.service.ticket.cache.TicketDetailCacheService;
import com.xxxx.ddd.domain.service.TicketDetailDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketDetailAppServiceImpl implements TicketDetailAppService {

    // Call Service Domain Module
    @Autowired
    private TicketDetailDomainService ticketDetailDomainService;

    // Call CACHE
    @Autowired
    private TicketDetailCacheService ticketDetailCacheService;

    @Override
    public TicketDetailDTO getTicketDetailById(Long ticketId, Long version) {
//        log.info("Implement Application : {}, {}: ", ticketId, version)
        return null;
    }

    @Override
    public boolean orderTicketByUser(Long ticketId) {
        return false;
    }
}
