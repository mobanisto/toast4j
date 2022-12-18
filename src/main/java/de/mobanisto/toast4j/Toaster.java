package de.mobanisto.toast4j;

import de.mobanisto.wintoast.Aumi;
import de.mobanisto.wintoast.IWinToastHandler;
import de.mobanisto.wintoast.WinToast;
import de.mobanisto.wintoast.WinToastTemplate;
import org.bytedeco.javacpp.CharPointer;
import org.bytedeco.javacpp.IntPointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Toaster {

    final static Logger logger = LoggerFactory.getLogger(Toaster.class);
    private static class AppName {

        private String appName;

        public AppName(String appName) {
            this.appName = appName;
        }

    }

    private final WinToast winToast;

    private final String aumi;

    public static Toaster forAumi(String aumi) {
        return new Toaster(aumi);
    }

    public static Toaster forAumi(Aumi aumi) {
        return new Toaster(aumi);
    }

    public static Toaster forAppName(String appName) {
        return new Toaster(new AppName(appName));
    }

    private Toaster(AppName appName) {
        winToast = WinToast.instance();
        CharPointer aumiResult = new CharPointer();
        boolean aumiFound = winToast.getAumiFromShellLink(new CharPointer(appName.appName), aumiResult);
        if (!aumiFound) throw new IllegalArgumentException(String.format("Invalid app '%s'", appName));
        aumi = aumiResult.getString();
    }

    private Toaster(String aumi) {
        this.aumi = aumi;
        winToast = WinToast.instance();
    }

    private Toaster(Aumi aumi) {
        winToast = WinToast.instance();
        CharPointer aumiResult = winToast.configureAUMI(new CharPointer(aumi.getCompanyName()),
                new CharPointer(aumi.getProductName()), new CharPointer(aumi.getSubProduct()),
                new CharPointer(aumi.getVersionInformation()));
        this.aumi = aumiResult.getString();
        logger.info("aumi: " + aumiResult.getString());
    }

    public boolean initialize() {
        boolean success = winToast.initialize();
        logger.info("initialize: " + success);
        return success;
    }

    private final IWinToastHandler defaultWinToastHandler = new IWinToastHandler() {
        @Override
        public void toastActivated() {
            logger.info("toast activated");
        }

        @Override
        public void toastActivated(int actionIndex) {
            logger.info("toast activated: " + actionIndex);
        }

        @Override
        public void toastDismissed(int state) {
            logger.info("toast dismissed: " + state);
        }

        @Override
        public void toastFailed() {
            logger.info("toast failed");
        }
    };

    public ToastHandle showToast(WinToastTemplate template) {
        IntPointer errorCode = new IntPointer(0);
        long uid = winToast.showToast(new CharPointer(aumi), template, defaultWinToastHandler, errorCode);
        logger.info("toast uid: " + uid);
        logger.info("error code: " + winToast.strerror(errorCode.get()).getString());
        return new ToastHandle(this, uid);
    }

    public void hideToast(long uid) {
        winToast.hideToast(new CharPointer(aumi), uid);
    }

    public boolean doesShellLinkExist(String appName) {
        return winToast.doesShellLinkExist(new CharPointer(appName));
    }

    public String getAumiFromShellLink(String appName) {
        CharPointer aumi = new CharPointer();
        boolean success = winToast.getAumiFromShellLink(new CharPointer(appName), aumi);
        if (success) {
            return aumi.getString();
        }
        return null;
    }

    public void initializeShortcut(String appName, boolean updateExisting) {
        winToast.initializeShortcut(new CharPointer(appName), new CharPointer(aumi), updateExisting);
    }

    public void setProcessAumi() {
        winToast.setProcessAumi(new CharPointer(aumi));
    }
}
