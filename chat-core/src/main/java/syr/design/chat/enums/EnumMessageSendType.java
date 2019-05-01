package syr.design.chat.enums;

public enum EnumMessageSendType {

    //发送信息类型
    user(1, "发送给个人"), group(2, "发送给群组");

    private Integer value;

    private String desc;

    public Integer value(){
        return value;
    }

    public String desc(){
        return desc;
    }

    EnumMessageSendType(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }
}
