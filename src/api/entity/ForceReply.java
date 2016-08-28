package api.entity;

import java.io.Serializable;

public class ForceReply implements Serializable {
    private boolean force_reply;
    private boolean selective;

    public ForceReply() {
    }

    public ForceReply(boolean forceReply) {
        this.force_reply = forceReply;
    }

    public ForceReply(boolean forceReply, boolean selective) {
        this.force_reply = forceReply;
        this.selective = selective;
    }

    public boolean isForceReply() {
        return force_reply;
    }

    public void setForceReply(boolean forceReply) {
        this.force_reply = forceReply;
    }

    public boolean isSelective() {
        return selective;
    }

    public void setSelective(boolean selective) {
        this.selective = selective;
    }
}
