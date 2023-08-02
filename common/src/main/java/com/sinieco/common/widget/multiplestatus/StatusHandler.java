package com.sinieco.common.widget.multiplestatus;

public class StatusHandler {

    private StatusViewHelper helper;

    StatusHandler(StatusViewHelper helper) {
        this.helper = helper;
    }

    /**
     *  重试
     */
    public void onRetryingCommand() {
        helper.onRetrying();
    }

}
