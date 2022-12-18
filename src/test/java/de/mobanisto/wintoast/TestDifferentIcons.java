package de.mobanisto.wintoast;

import de.mobanisto.wintoast.helper.ToastBuilder;
import de.mobanisto.wintoast.helper.ToastHandle;
import de.mobanisto.wintoast.helper.WinToastHelper;
import org.bytedeco.javacpp.CharPointer;

import static de.mobanisto.wintoast.WinToastTemplate.WinToastTemplateType.ToastText02;

public class TestDifferentIcons {

    public static void main(String[] args) throws InterruptedException {
        WinToast wintoast = WinToast.instance();
        wintoast.initialize();
        for (String appName : new String[]{
                "OneDrive", "Word", "Excel", "PowerPoint", "Outlook", "Google Chrome",
                "Foo", "Mobanisto\\Lanchat", "Administrative Tools\\Component Services"
        }) {
            CharPointer aumi = new CharPointer();
            boolean shellLinkExists = wintoast.doesShellLinkExist(new CharPointer(appName));
            if (!shellLinkExists) {
                System.out.println(String.format("App name: %s. Shell link not found", appName));
                continue;
            }
            boolean aumiFound = wintoast.getAumiFromShellLink(new CharPointer(appName), aumi);
            if (aumiFound) {
                showNotification(appName, aumi.getString());
            }
        }
    }

    private static void showNotification(String appName, String aumi) throws InterruptedException {
        WinToastHelper toastHelper = WinToastHelper.forAumi(aumi);
        ToastHandle toast = toastHelper.showToast(new ToastBuilder(ToastText02)
                .setLine1(String.format("%s: You've got 7 new messages", appName)).build());
        Thread.sleep(3000);
        toast.hide();
        Thread.sleep(1000);
    }

}

