package telegram.bot.api.enums;

/**
 * Created by Gladiator on 9/4/2016 AD.
 */
public enum ChatTypeEnum {
    PRIVATE("private"),
    GROUP("group"),
    SUPERGROUP("supergroup"),
    CHANNEL("channel");

    private String value;

    ChatTypeEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
