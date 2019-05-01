package syr.design.chat.enums;

public enum EnumGroupStatus {

    //群的状态
    establish(0, "正常"), dissolution(1, "解散");

    private Integer value;

    private String desc;

    public Integer value(){
        return value;
    }

    public String desc(){
        return desc;
    }

    EnumGroupStatus(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }
}
