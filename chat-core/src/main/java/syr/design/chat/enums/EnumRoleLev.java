package syr.design.chat.enums;

public enum EnumRoleLev {

    //用户角色等级
    owner(1, "群主"), manager(2, "管理员"), member(3, "成员");

    private Integer value;

    private String desc;

    public Integer value(){
        return value;
    }

    public String desc(){
        return desc;
    }

    EnumRoleLev(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }
}
