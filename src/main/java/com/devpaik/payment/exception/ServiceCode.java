package com.devpaik.payment.exception;

import org.springframework.http.HttpStatus;


public enum ServiceCode {

    OK(HttpStatus.OK, "0000", "OK"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "4000", "필수 파라미터가 없거나 형식이 잘못된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "4040", "찾을 수 없는 정보입니다."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5000", "처리중 오류가 발생했습니다. 다시 시도해주세요.");


    private final HttpStatus status;
    private final String code;
    private final String msg;

    private ServiceCode(HttpStatus status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    @Override
    public String toString() {
        return "ServiceCode{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
