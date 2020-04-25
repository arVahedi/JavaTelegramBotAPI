package api.requestobject;

import api.entity.inline.InlineQueryResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequestAnswerInlineQuery implements Serializable {
    private String inline_query_id;
    private List<InlineQueryResult> results = new ArrayList<>();
    private int cache_time;
    private boolean is_personal;
    private String next_offset;
    private String switch_pm_text;
    private String switch_pm_parameter;

    //region Getter and Setter
    public String getInline_query_id() {
        return inline_query_id;
    }

    public void setInline_query_id(String inline_query_id) {
        this.inline_query_id = inline_query_id;
    }

    public int getCache_time() {
        return cache_time;
    }

    public void setCache_time(int cache_time) {
        this.cache_time = cache_time;
    }

    public boolean is_personal() {
        return is_personal;
    }

    public void setIs_personal(boolean is_personal) {
        this.is_personal = is_personal;
    }

    public String getNext_offset() {
        return next_offset;
    }

    public void setNext_offset(String next_offset) {
        this.next_offset = next_offset;
    }

    public String getSwitch_pm_text() {
        return switch_pm_text;
    }

    public void setSwitch_pm_text(String switch_pm_text) {
        this.switch_pm_text = switch_pm_text;
    }

    public String getSwitch_pm_parameter() {
        return switch_pm_parameter;
    }

    public void setSwitch_pm_parameter(String switch_pm_parameter) {
        this.switch_pm_parameter = switch_pm_parameter;
    }

    public List<InlineQueryResult> getResults() {
        return results;
    }

    public void setResults(List<InlineQueryResult> results) {
        this.results = results;
    }
    //endregion
}
