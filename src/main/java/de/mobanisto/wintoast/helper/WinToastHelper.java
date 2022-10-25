package de.mobanisto.wintoast.helper;

import de.mobanisto.wintoast.Aumi;
import de.mobanisto.wintoast.IWinToastHandler;
import de.mobanisto.wintoast.WinToast;
import de.mobanisto.wintoast.WinToastTemplate;
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

    public ToastHandle showTextToast(String line1, String line2) {
        WinToastTemplate winToastTemplate =
                new WinToastTemplate(WinToastTemplate.WinToastTemplateType.ToastText02);
        System.out.println("template address: " + winToastTemplate.address());
        winToastTemplate.setTextField(new CharPointer(line1), WinToastTemplate.TextField.FirstLine);
        winToastTemplate.setTextField(new CharPointer(line2), WinToastTemplate.TextField.SecondLine);
        winToastTemplate.setAudioOption(WinToastTemplate.AudioOption.Silent);
        winToastTemplate.setExpiration(10000);

        IntPointer errorCode = new IntPointer(0);
        long uid = winToast.showToast(winToastTemplate, iWinToastHandler, errorCode);
        System.out.println("toast uid: " + uid);
        System.out.println("error code: " + winToast.strerror(errorCode.get()).getString());
        return new ToastHandle(this, uid);
    }

    public ToastHandle showImageToast(String line1, String line2, String image) {
        WinToastTemplate winToastTemplate =
                new WinToastTemplate(WinToastTemplate.WinToastTemplateType.ToastImageAndText02);
        System.out.println("template address: " + winToastTemplate.address());
        winToastTemplate.setTextField(new CharPointer(line1), WinToastTemplate.TextField.FirstLine);
        winToastTemplate.setTextField(new CharPointer(line2), WinToastTemplate.TextField.SecondLine);
        winToastTemplate.setImagePath(new CharPointer(image));
        winToastTemplate.setAudioOption(WinToastTemplate.AudioOption.Silent);
        winToastTemplate.setExpiration(10000);

        IntPointer errorCode = new IntPointer(0);
        long uid = winToast.showToast(winToastTemplate, iWinToastHandler, errorCode);
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
