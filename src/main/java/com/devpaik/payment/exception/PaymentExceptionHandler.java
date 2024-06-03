package com.devpaik.payment.exception;

import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice
public class PaymentExceptionHandler {
    /**
     * System Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleException Occurred: ", e);
        return ErrorResponse.of(ServiceCode.SERVER_ERROR);
    }


    /**
     * System Exception
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("handleRuntimeException Occurred: ", e);
        return ErrorResponse.of(ServiceCode.SERVER_ERROR, e.getMessage());
    }
    /**
     * System Exception
     */
    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        log.error("handleResponseStatusException Occurred: ", e);

        ServiceCode serviceCode = switch (e.getStatusCode().value()) {
            case 404 -> ServiceCode.NOT_FOUND;
            case 400 -> ServiceCode.BAD_REQUEST;
            default -> ServiceCode.SERVER_ERROR;
        };
        return ErrorResponse.of(serviceCode, e.getReason());
    }

    /**
     * db Exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = PersistenceException.class)
    public ResponseEntity<ErrorResponse> handlePersistenceException(PersistenceException e) {
        log.error("handlePersistenceException Occurred: ", e);

        return ErrorResponse.of(ServiceCode.SERVER_ERROR);
    }

    /**
     * filed type null exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(TypeMismatchException e) {
        log.error("handleTypeMismatchException :", e);

        Object obj = "null";
        String val = "null";
        if (e.getValue() != null) {
            obj = e.getValue().getClass().getTypeName();
            val = e.getValue().toString();
        }

        String desc = "Invalid " + e.getErrorCode() + " : [RequiredType=" + e.getRequiredType() + ", TypeName=" + obj + ", Value=" + val + "]";
        return ErrorResponse.of(ServiceCode.BAD_REQUEST, desc);
    }

    /**
     * Valid argumentException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();

        String result = getBindingResult(bindingResult);
        log.error("handleBindException [{}]", result, e);

        return ErrorResponse.of(ServiceCode.BAD_REQUEST, bindingResult);
    }

    private String getBindingResult(BindingResult bindingResult) {
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getField());
            builder.append(" : ");
            builder.append(fieldError.getDefaultMessage());
            builder.append("[");
            builder.append(fieldError.getRejectedValue());
            builder.append("], ");
        }
        return builder.toString();
    }
}
