package de.mobanisto.toast4j;

public class ToastHandle {

    private Toaster toaster;
    private long uid;

    public ToastHandle(Toaster toaster, long uid) {
        this.toaster = toaster;
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }

    public void hide() {
        toaster.hideToast(uid);
    }
}
