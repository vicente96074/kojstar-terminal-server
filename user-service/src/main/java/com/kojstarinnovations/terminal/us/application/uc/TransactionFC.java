package com.kojstarinnovations.terminal.us.application.uc;

import com.kojstarinnovations.terminal.commons.data.payload.commons.IdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "transaction-service", url = "http://localhost:8082")
public interface TransactionFC {

    @GetMapping("/transaction-service/generate-transaction")
    ResponseEntity<IdResponse> generateTransaction(@RequestParam String prefix);
}