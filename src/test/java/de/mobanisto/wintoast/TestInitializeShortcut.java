package de.mobanisto.wintoast;

import de.mobanisto.wintoast.helper.ToastBuilder;
import de.mobanisto.wintoast.helper.ToastHandle;
import de.mobanisto.wintoast.helper.WinToastHelper;

import java.nio.file.Path;
import java.nio.file.Paths;

import static de.mobanisto.wintoast.WinToastTemplate.WinToastTemplateType.ToastImageAndText02;
import static de.mobanisto.wintoast.WinToastTemplate.WinToastTemplateType.ToastText02;

public class TestInitializeShortcut {

    public static void main(String[] args) throws InterruptedException {
        // It is required to have a shortcut installed that matches the app name.
        // That shortcut can be located in either of two directories, either
        // '$APPDATA\Microsoft\Windows\Start Menu\Programs'
        // or
        // '$ProgramData\Microsoft\Windows\Start Menu\Programs'
        //
        // Tools like jpackage create installers that install shortcuts below a
        // subdirectory such that the shortcut will end up in a directory relative to
        // the path above such as 'Company/Product.lnk'. Hence, the appName needs
        // to be 'Company\\Product'.
        //
        // WinToast can set the AUMI for an existing shortcut so that an app that
        // creates toasts with that AUMI will be decorated with an icon that is taken
        // from the shortcut with the same associated AUMI. When there are multiple
        // shortcuts that have a matching AUMI, the first one found will be used, which
        // seems to be searched in alphanumeric order of the file names.
        //
        // WinToast can also attempt to create a shortcut for you, however it won't
        // currently create subdirectories, i.e. you need to make sure that
        // the subdirectory 'Company' exists in '$ProgramData\Microsoft\Windows\Start Menu\Programs'.
        String aumi = "Test Notifications";
        String appName = "Mobanisto\\Test Notifications";
        WinToastHelper toastHelper = WinToastHelper.forAumi(aumi);
        boolean initialized = toastHelper.initialize();
        if (!initialized) {
            return;
        }
        boolean doesShellLinkExist = toastHelper.doesShellLinkExist(appName);
        System.out.println("Found shell link? " + doesShellLinkExist);
        String aumiFound = toastHelper.getAumiFromShellLink(appName);
        System.out.println("Found aumi: " + aumiFound);
        toastHelper.initializeShortcut(appName, false);

        // Wait a moment, otherwise the first notification will not have the icon instantly.
        Thread.sleep(3000);

        ToastHandle toast1 = toastHelper.showToast(
                new ToastBuilder(ToastText02).setLine1("You've got 7 new messages").build());
        Thread.sleep(3000);
        toast1.hide();

        ToastHandle toast2 = toastHelper.showToast(
                new ToastBuilder(ToastText02).setLine1("Foo").setLine2("Something important").setLine3("asdfasd").build());
        Thread.sleep(3000);
        toast2.hide();
    }

}

