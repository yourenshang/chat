package syr.design.chat.enums;

public enum EnumResultCode {

    //返回状态
    SUCCESS(0, "成功"),
    FAIL(2, "失败");

    private Integer value;

    private String desc;

    public Integer value(){
        return value;
    }

    public String desc(){
        return desc;
    }

    EnumResultCode(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }
}
