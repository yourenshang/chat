package syr.design.chat.enums;

public enum EnumMessageStatus {

    //发送的消息状态
    normal(0, "正常"), invalid(1, "实效");

    private Integer value;

    private String desc;

    public Integer value(){
        return value;
    }

    public String desc(){
        return desc;
    }

    EnumMessageStatus(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }
}
