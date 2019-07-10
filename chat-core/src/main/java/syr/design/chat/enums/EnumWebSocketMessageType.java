package syr.design.chat.enums;

/**
 * @author shangyouren
 */
public enum  EnumWebSocketMessageType {

    //前后端交互类型
    message(1, "聊天消息"), dispay(2, "显示类型消息"), pushed(3, "推送消息");

    private Integer value;

    private String desc;

    public Integer value(){
        return value;
    }

    public String desc(){
        return desc;
    }

    EnumWebSocketMessageType(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }

}
