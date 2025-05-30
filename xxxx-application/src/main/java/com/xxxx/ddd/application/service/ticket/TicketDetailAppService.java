package com.xxxx.ddd.application.service.ticket;

import com.xxxx.ddd.application.model.TicketDetailDTO;

public interface TicketDetailAppService {

    TicketDetailDTO getTicketDetailById(Long ticketId, Long version);
    // order ticket
    boolean orderTicketByUser(Long ticketId);
}
