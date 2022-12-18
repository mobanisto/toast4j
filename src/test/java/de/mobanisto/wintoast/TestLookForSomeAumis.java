package de.mobanisto.wintoast;

import org.bytedeco.javacpp.CharPointer;

public class TestLookForSomeAumis {

    public static void main(String[] args) {
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
                System.out.println(String.format("App name: %s. Found AUMI: %s", appName, aumi.getString()));
            } else {
                System.out.println(String.format("App name: %s. AUMI not found", appName));
            }
        }
    }

}

