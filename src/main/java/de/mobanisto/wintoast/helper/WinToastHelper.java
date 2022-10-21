package de.mobanisto.wintoast.helper;

import de.mobanisto.wintoast.WinToast;
import de.mobanisto.wintoast.WinToastTemplate;
import de.mobanisto.wintoast.Aumi;
import de.mobanisto.wintoast.IWinToastHandler;
import org.bytedeco.javacpp.CharPointer;
import org.bytedeco.javacpp.IntPointer;

public class WinToastHelper {

    private final WinToast winToast;

    public WinToastHelper(String appName) {
        winToast = WinToast.instance();
        winToast.setAppName(new CharPointer(appName));
        CharPointer aumiResult = new CharPointer(appName);
        winToast.setAppUserModelId(aumiResult);
        System.out.println("initialize: " + winToast.initialize());
    }

    public WinToastHelper(Aumi aumi, String appName) {
        winToast = WinToast.instance();
        winToast.setAppName(new CharPointer(appName));
        CharPointer aumiResult = winToast.configureAUMI(new CharPointer(aumi.getCompanyName()),
                new CharPointer(aumi.getProductName()), new CharPointer(aumi.getSubProduct()),
                new CharPointer(aumi.getVersionInformation()));
        System.out.println("aumi: " + aumiResult.getString());
        winToast.setAppUserModelId(aumiResult);
        System.out.println("initialize: " + winToast.initialize());
    }

    public ToastHandle showToast(String message, String image) {
        WinToastTemplate winToastTemplate =
                new WinToastTemplate(WinToastTemplate.WinToastTemplateType.TOASTIMAGEANDTEXT02);
        System.out.println("template address: " + winToastTemplate.address());
        winToastTemplate.setTextField(new CharPointer(message), WinToastTemplate.TextField.FirstLine);
        winToastTemplate.setImagePath(new CharPointer(image));
        winToastTemplate.setAudioOption(WinToastTemplate.AudioOption.Silent);
        winToastTemplate.setExpiration(10000);

        IWinToastHandler iWinToastHandler = new IWinToastHandler() {
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

        IntPointer errorCode = new IntPointer(0);
        long uid = winToast.showToast(winToastTemplate, iWinToastHandler, errorCode);
        System.out.println("toast uid: " + uid);
        System.out.println("error code: " + winToast.strerror(errorCode.get()).getString());
        return new ToastHandle(this, uid);
    }

    public void hideToast(long uid) {
        winToast.hideToast(uid);
    }
}
