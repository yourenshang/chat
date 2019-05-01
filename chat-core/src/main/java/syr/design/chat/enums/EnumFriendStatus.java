package syr.design.chat.enums;

public enum  EnumFriendStatus {

    //申请添加朋友状态
    apply(0, "已申请"), agree(1, "已同意"), refuse(2, "已拒绝");

    private Integer value;

    private String desc;

    public Integer value(){
        return value;
    }

    public String desc(){
        return desc;
    }

    EnumFriendStatus(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }
}
