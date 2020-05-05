package telegram.bot.api.enums;

/**
 * Created by Gladiator on 9/1/2016 AD.
 */
public enum ChatActionEnum {
    TYPING("typing"),
    UPLOAD_PHOTO("upload_photo"),
    RECORD_VIDEO("record_video"),
    UPLOAD_VIDEO("upload_video"),
    RECORD_AUDIO("record_audio"),
    UPLOAD_AUDIO("upload_audio"),
    UPLOAD_DOCUMENT("upload_document"),
    FIND_LOCATION("find_location");

    private String value;

    ChatActionEnum(String value){
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}
