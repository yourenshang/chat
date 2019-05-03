package syr.design.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import syr.design.chat.enums.EnumResultCode;
import syr.design.chat.model.Result;

@Controller
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

    @GetMapping("/")
    public String index(){
        return "index";
    }

}
