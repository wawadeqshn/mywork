package com.zetyun.mywork.common;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

@Data
@Log4j2
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -3948389268046368059L;

    private Integer ret_code;

    private String ret_msg;

    private T ret_data;

    public Result() {}

    public Result(Integer code, String msg) {
        this.ret_code = code;
        this.ret_msg = msg;
    }

    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setRet_data(data);
        return result;
    }

    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setRet_data(data);
        return result;
    }

    public static Result failure(Integer code, String message) {
        Result result = new Result();
        result.setRet_code(code);
        result.setRet_msg(message);
        return result;
    }

    public static Result failure(Integer code, String message, Object data) {
        Result result = new Result();
        result.setRet_code(code);
        result.setRet_msg(message);
        result.setRet_data(data);
        return result;
    }

    public void setResultCode(ResultCode code) {
        this.ret_code = code.code();
        this.ret_msg = code.message();
    }

}
