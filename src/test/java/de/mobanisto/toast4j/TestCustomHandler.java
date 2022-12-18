package de.mobanisto.toast4j;

import de.mobanisto.wintoast.WinToastHandler;

import static de.mobanisto.wintoast.WinToastTemplate.WinToastTemplateType.ToastText02;

public class TestCustomHandler {

    public static void main(String[] args) throws InterruptedException {
        String aumi = "Test Notifications";
        Toaster toastHelper = Toaster.forAumi(aumi);
        boolean initialized = toastHelper.initialize();
        if (!initialized) {
            return;
        }

        ToastHandle toast1 = toastHelper.showToast(
                new ToastBuilder(ToastText02).setLine1("You've got 7 new messages").build(),
                new DefaultToastHandler() {
                    @Override
                    public void toastActivated() {
                        System.out.println("first toast activated!");
                    }
                });
        Thread.sleep(3000);
        toast1.hide();

        ToastHandle toast2 = toastHelper.showToast(
                new ToastBuilder(ToastText02).setLine1("Foo").setLine2("Something important").setLine3("asdfasd").build(),
                new DefaultToastHandler() {
                    @Override
                    public void toastActivated() {
                        System.out.println("second toast activated!");
                    }
                });
        Thread.sleep(3000);
        toast2.hide();

        Thread.sleep(3000);
    }

}

