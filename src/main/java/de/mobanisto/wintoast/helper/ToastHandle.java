package de.mobanisto.wintoast.helper;

public class ToastHandle {

    private WinToastHelper winToastHelper;
    private long uid;

    public ToastHandle(WinToastHelper winToastHelper, long uid) {
        this.winToastHelper = winToastHelper;
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }

    public void hide() {
        winToastHelper.hideToast(uid);
    }
}
