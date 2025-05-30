package com.xxxx.ddd.controller.http;


import com.xxxx.ddd.application.model.TicketDetailDTO;
import com.xxxx.ddd.application.service.ticket.TicketDetailAppService;
import com.xxxx.ddd.controller.model.enums.ResultUtil;
import com.xxxx.ddd.controller.model.vo.ResultMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
@Slf4j
public class TicketDetailController {

    // Call Service Application
    @Autowired
    private TicketDetailAppService ticketDetailAppService;

    @GetMapping("/ping/java")
    public ResponseEntity<Object> ping() throws InterruptedException {
        // Proposal tasks take time
        Thread.sleep(1000); // Simulate time.Sleep(1 * time.Second)

        // Return response with status 200 OK
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response("OK"));
    }

    // Class Response to return JSON response
    @Getter
    @Setter
    public static class Response {
        private String status;
        public Response(String status) {
            this.status = status;
        }
    }

    /**
     * Get ticket detail
     * @param ticketId
     * @param detailId
     * @return ResultUtil
     */
    @GetMapping("{ticketId}/detail/{detailId}")
    public ResultMessage<TicketDetailDTO> getTicketDetail(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("detailId") Long detailId,
            @RequestParam(name = "version", required = false) Long version
    ) {
        return ResultUtil.data(ticketDetailAppService.getTicketDetailById(detailId, version));
    }
}
