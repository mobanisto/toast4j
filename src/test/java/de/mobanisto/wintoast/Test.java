package de.mobanisto.wintoast;

import de.mobanisto.wintoast.helper.ToastHandle;
import de.mobanisto.wintoast.helper.WinToastHelper;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {

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
        // WinToast will set the AUMI for an existing shortcut so that an app that
        // creates toasts with that AUMI will be decorated with an icon that is taken
        // from the shortcut with the same associated AUMI. When there are multiple
        // shortcuts that have a matching AUMI, the first one found will be used, which
        // seems to be searched in alphanumeric order of the file names.
        //
        // WinToast can also attempt to create a shortcut for you, however it won't
        // currently create subdirectories, i.e. you need to make sure that
        // the subdirectory 'Company' exists in '$ProgramData\Microsoft\Windows\Start Menu\Programs'.
        WinToastHelper toastHelper = new WinToastHelper(
                "Test Notifications",
                "Mobanisto\\Test Notifications"
        );
        boolean initialized = toastHelper.initialize();
        if (!initialized) {
            return;
        }
        boolean doesShellLinkExist = toastHelper.doesShellLinkExist();
        System.out.println("Found shell link? " + doesShellLinkExist);
        String aumi = toastHelper.getAumiFromShellLink();
        System.out.println("Found aumi: " + aumi);
        toastHelper.initializeShortcut();

        Path cwd = Paths.get(System.getProperty("user.dir"));
        String image = cwd.resolve("example/terminal.png").toAbsolutePath().toString();
        ToastHandle toast1 = toastHelper.showTextToast("Foo", "Something important");
        Thread.sleep(2000);
        toast1.hide();
        ToastHandle toast2 = toastHelper.showImageToast("Bar", "Goodbye", image);
        Thread.sleep(5000);
        toast2.hide();
    }

}

