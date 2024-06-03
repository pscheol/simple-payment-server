package com.devpaik.payment.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "message", "data"})
public class ErrorResponse {
    private final String code;
    private final String message;
    private List<FieldError> data;

    private ErrorResponse(final ServiceCode serviceCode) {
        this.message = serviceCode.getMsg();
        this.code = serviceCode.getCode();
        this.data = null;
    }

    public ErrorResponse(final ServiceCode serviceCode, final String msg) {
        this.code = serviceCode.getCode();
        this.message = msg;
    }

    public ErrorResponse(final ServiceCode serviceCode, final String msg, final BindingResult bindingResult) {
        this.code = serviceCode.getCode();
        this.message = msg;
        this.data = FieldError.of(bindingResult);
    }

    public static ResponseEntity<ErrorResponse> of(final ServiceCode code, final BindingResult bindingResult) {
        String msg = code.getMsg();
        if (ObjectUtils.isNotEmpty(bindingResult.getAllErrors().get(0))) {
            msg = bindingResult.getAllErrors().get(0).getDefaultMessage();
        }
        return ResponseEntity.status(code.getStatus()).body(new ErrorResponse(code, msg, bindingResult));
    }

    public static ResponseEntity<ErrorResponse> of(final ServiceCode code) {
        return ResponseEntity.status(code.getStatus()).body(new ErrorResponse(code));
    }

    public static ResponseEntity<ErrorResponse> of(final ServiceCode code, final String msg) {
        return ResponseEntity.status(code.getStatus()).body(new ErrorResponse(code, msg));
    }


    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream().map(error -> {
                Object obj = error.getRejectedValue();
                String value = obj == null ? "null" : error.getRejectedValue().toString();
                return FieldError.builder()
                        .field(error.getField())
                        .value(value)
                        .reason(error.getDefaultMessage())
                        .build();
            }).collect(Collectors.toList());
        }
    }
}
