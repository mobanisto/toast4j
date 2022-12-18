package de.mobanisto.toast4j;

import de.mobanisto.wintoast.WinToastHandler;

public class DefaultToastHandler extends WinToastHandler {

    @Override
    public void toastActivated() {
        // NO-OP
    }

    @Override
    public void toastActivated(int actionIndex) {
        // NO-OP
    }

    @Override
    public void toastDismissed(int state) {
        // NO-OP
    }

    @Override
    public void toastFailed() {
        // NO-OP
    }

}
