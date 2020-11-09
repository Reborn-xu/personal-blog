package com.reborn.springboot.utils;

import com.reborn.springboot.entity.Result;

public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
    private static final int RESULT_CODE_SUCCESS = 200;
    private static final int RESULT_CODE_SERVER_ERROR = 500;

    public static Result getSuccessResult(Object data){
        Result result = new Result();
        result.setResultCode(RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    public static Result getFailResult(String message){
        Result result = new Result();
        result.setResultCode(RESULT_CODE_SERVER_ERROR);
        result.setMessage(message);
        return result;
    }

    public static Result getSuccessResult(String message){
        Result result = new Result();
        result.setResultCode(RESULT_CODE_SUCCESS);
        result.setMessage(message);
        return result;
    }
}
