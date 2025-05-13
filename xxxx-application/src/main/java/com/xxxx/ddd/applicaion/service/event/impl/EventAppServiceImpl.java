package com.xxxx.ddd.applicaion.service.event.impl;

import com.xxxx.ddd.applicaion.service.event.EventAppService;
import com.xxxx.ddd.domain.service.HIDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventAppServiceImpl implements EventAppService {
    // call domain service
    @Autowired
    private HIDomainService hiDomainService;
    @Override
    public String sayHi(String who) {
        return hiDomainService.sayHi(who);
    }
}
