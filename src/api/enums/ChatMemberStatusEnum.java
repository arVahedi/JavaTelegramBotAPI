package api.enums;

/**
 * Created by Gladiator on 8/26/2016 AD.
 */
public enum  ChatMemberStatusEnum {
    CREATOR("creator"),
    ADMINISTRATOR("administrator"),
    MEMBER("member"),
    LEFT("left"),
    KICKED("kicked");

    private String value;

    ChatMemberStatusEnum(String value){
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}
