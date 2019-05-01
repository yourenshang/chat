package syr.design.chat.model;

import com.alibaba.fastjson.JSON;

public class Result {

    private Integer code;

    private String message;

    private Object info;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }
}
