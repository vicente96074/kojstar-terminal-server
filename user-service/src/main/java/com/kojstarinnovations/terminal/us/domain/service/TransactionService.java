package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.payload.commons.IdResponse;
import com.kojstarinnovations.terminal.us.application.uc.TransactionFC;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {

    public IdResponse generateTransaction(String prefix) {
        return Objects.requireNonNull(transactionFC.generateTransaction(prefix).getBody());
    }

    private final TransactionFC transactionFC;
}