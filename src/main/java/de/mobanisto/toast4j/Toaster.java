package de.mobanisto.toast4j;

import de.mobanisto.wintoast.Aumi;
import de.mobanisto.wintoast.WinToast;
import de.mobanisto.wintoast.WinToastHandler;
import de.mobanisto.wintoast.WinToastTemplate;
import org.bytedeco.javacpp.CharPointer;
import org.bytedeco.javacpp.IntPointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Toaster {

    final static Logger logger = LoggerFactory.getLogger(Toaster.class);

    private static class AppName {

        private final String appName;

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

    /**
     * Initialize the native library. Should be called once for the whole process. When using multiple instances of this
     * class, it is not necessary to repeat the initialization.
     */
    public boolean initialize() {
        boolean success = winToast.initialize();
        logger.info("initialize: " + success);
        return success;
    }

    private final WinToastHandler defaultWinToastHandler = new WinToastHandler() {
        @Override
        public void toastActivated() {
            logger.debug("toast activated");
        }

        @Override
        public void toastActivated(int actionIndex) {
            logger.debug("toast activated: " + actionIndex);
        }

        @Override
        public void toastDismissed(int state) {
            logger.debug("toast dismissed: " + state);
        }

        @Override
        public void toastFailed() {
            logger.warn("toast failed");
        }
    };

    /**
     * Show a toast using the default handler which does only print to the logger on activation and dismissal.
     * @return a toast handle that can be used to hide the toast later on.
     */
    public ToastHandle showToast(WinToastTemplate template) {
        return showToast(template, null);
    }

    /**
     * Show a toast using a custom handler.
     * @return a toast handle that can be used to hide the toast later on.
     */
    public ToastHandle showToast(WinToastTemplate template, WinToastHandler handler) {
        WinToastHandler h = handler != null ? handler : defaultWinToastHandler;
        IntPointer errorCode = new IntPointer(0);
        long uid = winToast.showToast(new CharPointer(aumi), template, h, errorCode);
        logger.debug("toast uid: " + uid);
        logger.debug("error code: " + winToast.strerror(errorCode.get()).getString());
        return new ToastHandle(this, uid);
    }

    public void hideToast(long uid) {
        winToast.hideToast(new CharPointer(aumi), uid);
    }

    /**
     * Find out if a shortcut in the start menu exists for the specified app name.
     */
    public boolean doesShellLinkExist(String appName) {
        return winToast.doesShellLinkExist(new CharPointer(appName));
    }

    /**
     * Find the AUMI that is registered for the specified app name. Resolves a shortcut in the start menu and finds
     * the associated AUMI.
     */
    public String getAumiFromShellLink(String appName) {
        CharPointer aumi = new CharPointer();
        boolean success = winToast.getAumiFromShellLink(new CharPointer(appName), aumi);
        if (success) {
            return aumi.getString();
        }
        return null;
    }

    /**
     * If no shortcut exists in the start menu, attempt to create one.
     */
    public void initializeShortcut(String appName, boolean updateExisting) {
        winToast.initializeShortcut(new CharPointer(appName), new CharPointer(aumi), updateExisting);
    }

    /**
     * Registers this Toaster's AUMI so that it identifies the current process to the taskbar.
     * This identifier allows an application to group its associated processes and windows under a single taskbar button.
     * See <a href="https://learn.microsoft.com/en-us/windows/win32/api/shobjidl_core/nf-shobjidl_core-setcurrentprocessexplicitappusermodelid">SetCurrentProcessExplicitAppUserModelID </a>
     */
    public void setProcessAumi() {
        winToast.setProcessAumi(new CharPointer(aumi));
    }
}
