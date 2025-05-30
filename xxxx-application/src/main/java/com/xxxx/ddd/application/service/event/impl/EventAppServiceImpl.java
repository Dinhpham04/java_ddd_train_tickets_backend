package com.xxxx.ddd.application.service.event.impl;

import com.xxxx.ddd.application.service.event.EventAppService;
import com.xxxx.ddd.domain.service.HIDomainService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EventAppServiceImpl implements EventAppService {
    @Resource
    private HIDomainService hiDomainService;
    @Override
    public String sayHi(String who) {
        return hiDomainService.sayHi(who);
    }
}
