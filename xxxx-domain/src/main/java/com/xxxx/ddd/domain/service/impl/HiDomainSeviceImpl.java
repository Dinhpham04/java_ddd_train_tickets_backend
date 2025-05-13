package com.xxxx.ddd.domain.service.impl;

import com.xxxx.ddd.domain.repository.HiDomainRepository;
import com.xxxx.ddd.domain.service.HIDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiDomainSeviceImpl implements HIDomainService {
    @Autowired
    private HiDomainRepository hiDomainRepository;
    @Override
    public String sayHi(String who) {
        return hiDomainRepository.sayHi(who);
    }
}
