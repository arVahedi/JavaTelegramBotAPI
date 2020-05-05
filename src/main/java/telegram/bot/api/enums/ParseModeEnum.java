package telegram.bot.api.enums;

/**
 * Created by Gladiator on 8/26/2016 AD.
 */
public enum  ParseModeEnum {
    MARKDOWN("Markdown"),
    HTML("HTML");

    private String value;

    ParseModeEnum(String value){
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}
