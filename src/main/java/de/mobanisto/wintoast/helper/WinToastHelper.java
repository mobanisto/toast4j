package de.mobanisto.wintoast.helper;

import de.mobanisto.wintoast.Aumi;
import de.mobanisto.wintoast.IWinToastHandler;
import de.mobanisto.wintoast.WinToast;
import de.mobanisto.wintoast.WinToastTemplate;
import de.mobanisto.wintoast.WinToastTemplate.WinToastTemplateType;
import org.bytedeco.javacpp.CharPointer;
import org.bytedeco.javacpp.IntPointer;

public class WinToastHelper {

    private final WinToast winToast;

    public WinToastHelper(String appName) {
        winToast = WinToast.instance();
        winToast.setAppName(new CharPointer(appName));
        CharPointer aumiResult = new CharPointer(appName);
        winToast.setAppUserModelId(aumiResult);
    }

    public WinToastHelper(String aumi, String appName) {
        winToast = WinToast.instance();
        winToast.setAppName(new CharPointer(appName));
        winToast.setAppUserModelId(new CharPointer(aumi));
    }

    public WinToastHelper(Aumi aumi, String appName) {
        winToast = WinToast.instance();
        winToast.setAppName(new CharPointer(appName));
        CharPointer aumiResult = winToast.configureAUMI(new CharPointer(aumi.getCompanyName()),
                new CharPointer(aumi.getProductName()), new CharPointer(aumi.getSubProduct()),
                new CharPointer(aumi.getVersionInformation()));
        System.out.println("aumi: " + aumiResult.getString());
        winToast.setAppUserModelId(aumiResult);
    }

    public boolean initialize()
    {
        boolean success = winToast.initialize();
        System.out.println("initialize: " + success);
        return success;
    }

    private IWinToastHandler iWinToastHandler = new IWinToastHandler() {
        @Override
        public void toastActivated() {
            System.out.println("toast activated");
        }

        @Override
        public void toastActivated(int actionIndex) {
            System.out.println("toast activated: " + actionIndex);
        }

        @Override
        public void toastDismissed(int state) {
            System.out.println("toast dismissed: " + state);
        }

        @Override
        public void toastFailed() {
            System.out.println("toast failed");
        }
    };

    public ToastHandle showToast(WinToastTemplate template) {
        IntPointer errorCode = new IntPointer(0);
        long uid = winToast.showToast(template, iWinToastHandler, errorCode);
        System.out.println("toast uid: " + uid);
        System.out.println("error code: " + winToast.strerror(errorCode.get()).getString());
        return new ToastHandle(this, uid);
    }

    public void hideToast(long uid) {
        winToast.hideToast(uid);
    }

    public boolean doesShellLinkExist() {
        return WinToast.instance().doesShellLinkExist();
    }

    public String getAumiFromShellLink() {
        CharPointer aumi = new CharPointer();
        boolean success = WinToast.instance().getAumiFromShellLink(aumi);
        if (success) {
            return aumi.getString();
        }
        return null;
    }

    public void initializeShortcut() {
        WinToast.instance().initializeShortcut();
    }

    public void setProcessAumi() {
        WinToast.instance().setProcessAumi();
    }
}
