package com.domo.ecommerce.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ErrorMsg {
    private String msg;
    private String errorCode;
    private LocalDateTime timestamp;
}
