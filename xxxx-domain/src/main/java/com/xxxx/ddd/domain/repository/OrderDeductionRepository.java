package com.xxxx.ddd.domain.repository;

import com.xxxx.ddd.domain.model.entity.TickerOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public interface OrderDeductionRepository {
    void insertOrder(String yearMonth, TickerOrder tickerOrder);
    List<Objects[]> findAll(String yearMonth);
    Objects[] findByOrderNumber(String yearMonth, String orderNumber);
    List<Objects[]> findByDateRange(String yearMonth, LocalDateTime startDate, LocalDateTime endDate);
}
