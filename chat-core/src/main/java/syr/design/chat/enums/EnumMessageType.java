package syr.design.chat.enums;

public enum EnumMessageType {

    //发送的消息类型
    text(1, "文本消息"), url(2, "url消息");

    private Integer value;

    private String desc;

    public Integer value(){
        return value;
    }

    public String desc(){
        return desc;
    }

    EnumMessageType(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }
}
