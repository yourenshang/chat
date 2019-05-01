package syr.design.chat.controller;

import syr.design.chat.enums.EnumResultCode;
import syr.design.chat.model.Result;

public class BaseController {

    public Result result(EnumResultCode resultCode, String message){
        Result result = new Result();
        result.setCode(resultCode.value());
        result.setMessage(message);
        result.setInfo(null);
        return result;
    }

    public Result result(EnumResultCode resultCode){
        Result result = new Result();
        result.setCode(resultCode.value());
        result.setInfo(null);
        result.setMessage(resultCode.desc());
        return result;
    }

    public Result result(Object object){
        Result result = new Result();
        result.setCode(EnumResultCode.SUCCESS.value());
        result.setInfo(object);
        result.setMessage(EnumResultCode.SUCCESS.desc());
        return result;
    }

}
