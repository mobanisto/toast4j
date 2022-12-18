package de.mobanisto.toast4j;

import java.nio.file.Path;
import java.nio.file.Paths;

import static de.mobanisto.wintoast.WinToastTemplate.WinToastTemplateType.ToastImageAndText02;
import static de.mobanisto.wintoast.WinToastTemplate.WinToastTemplateType.ToastText02;

public class TestShowSomeNotifications {

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
        Toaster toastHelper = Toaster.forAumi(aumi);
        boolean initialized = toastHelper.initialize();
        if (!initialized) {
            return;
        }

        Path cwd = Paths.get(System.getProperty("user.dir"));
        String image = cwd.resolve("example/terminal.png").toAbsolutePath().toString();

        ToastHandle toast1 = toastHelper.showToast(
                new ToastBuilder(ToastText02).setLine1("You've got 7 new messages").build());
        Thread.sleep(3000);
        toast1.hide();

        ToastHandle toast2 = toastHelper.showToast(
                new ToastBuilder(ToastText02).setLine1("Foo").setLine2("Something important").setLine3("asdfasd").build());
        Thread.sleep(3000);
        toast2.hide();

        ToastHandle toast3 = toastHelper.showToast(
                new ToastBuilder(ToastImageAndText02).setLine1("Bar").setLine2("Goodbye").setImage(image).build());
        Thread.sleep(5000);
        toast3.hide();

        Thread.sleep(3000);
    }

}

