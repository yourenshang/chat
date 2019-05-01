package syr.design.chat.enums;

public enum EnumUserGroupStatus {

    //用户与群组关系状态
    apply(0, "已申请"), agree(1, "已同意"), refuse(2, "已拒绝");

    private Integer value;

    private String desc;

    public Integer value(){
        return value;
    }

    public String desc(){
        return desc;
    }

    EnumUserGroupStatus(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }
}
