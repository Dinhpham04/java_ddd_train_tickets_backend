package com.xxxx.ddd.domain.repository;

public interface TickerOrderRepository {
    boolean decreaseStockLevel1(Long ticketId, int quantity);
    boolean decreaseStockLevel2(Long ticketId, int quantity);
    boolean decreaseStockLevel3CAS(Long tickerId, int oldStockAvailable, int quantity);

    int getStockAvailable(Long ticketId);
}
