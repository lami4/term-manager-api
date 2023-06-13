package com.selyuto.termbase.dto;

import com.selyuto.termbase.enums.Action;

public class NotificationMessage {

    private Action action;

    public NotificationMessage() {
    }

    public NotificationMessage(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
