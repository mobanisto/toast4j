#include "wintoastlib.h"
#include <string>

using namespace WinToastLib;

class CustomHandler : public IWinToastHandler {
public:
    void toastActivated() const {
        std::wcout << L"The user clicked in this toast" << std::endl;
        exit(0);
    }

    void toastActivated(int actionIndex) const {
        std::wcout << L"The user clicked on action #" << actionIndex << std::endl;
        exit(16 + actionIndex);
    }

    void toastDismissed(WinToastDismissalReason state) const {
        switch (state) {
        case UserCanceled:
            std::wcout << L"The user dismissed this toast" << std::endl;
            break;
        case TimedOut:
            std::wcout << L"The toast has timed out" << std::endl;
            break;
        case ApplicationHidden:
            std::wcout << L"The application hid the toast using ToastNotifier.hide()" << std::endl;
            break;
        default:
            std::wcout << L"Toast not activated" << std::endl;
            break;
        }
    }

    void toastFailed() const {
        std::wcout << L"Error showing current toast" << std::endl;
        exit(5);
    }
};

enum Results {
    ToastFailed,           // toast failed
    SystemNotSupported,    // system does not support toasts
    InitializationFailure, // toast notification manager initialization failure
};

int wmain(int argc, LPWSTR* argv)
{
    if (!WinToast::isCompatible()) {
        std::wcerr << L"Error, your system in not supported!" << std::endl;
        return Results::SystemNotSupported;
    }

    LPWSTR appName, appUserModelID;
    LPWSTR text = L"Hello, world!",
        imagePath = NULL,
        attribute = L"default";
    INT64 expiration = 1000;

    WinToastTemplate::AudioOption audioOption = WinToastTemplate::AudioOption::Default;

    // Try any of these combinations and send notifications like OneDrive, Chrome or Pidgin :)
    // Try `Get-StartApps` on the PowerShell to find more.
    //appName = L"OneDrive";
    //appUserModelID = L"Microsoft.SkyDrive.Desktop",
    //appName = L"Google Chrome";
    //appUserModelID = L"Chrome",
    //appName = L"Pidgin";
    //appUserModelID = L"Pidgin",

    // This is an app name that uses a shortcut within a subdirectory
    appName = L"Mobanisto\\Test Notifications";
    appUserModelID = L"Test Notifications";

    // WinToast::instance()->setShortcutPolicy(WinToast::SHORTCUT_POLICY_IGNORE);

    if (!WinToast::instance()->initialize()) {
        std::wcerr << L"Error, your system in not compatible!" << std::endl;
        return Results::InitializationFailure;
    }

    bool shellLinkExists = WinToast::instance()->doesShellLinkExist(appName);
    std::wcout << L"does shell link exist? " << shellLinkExists << std::endl;

    std::wstring aumi;
    WinToast::instance()->getAumiFromShellLink(appName, aumi);
    std::wcout << L"found aumi for shell link: " << aumi << std::endl;

    WinToast::instance()->initializeShortcut(appName, aumi);
    // WinToast::instance()->setProcessAumi(aumi);

    bool withImage = (imagePath != NULL);
    WinToastTemplate templ(withImage ? WinToastTemplate::ImageAndText02 : WinToastTemplate::Text02);
    templ.setTextField(text, WinToastTemplate::FirstLine);
    templ.setAudioOption(audioOption);
    templ.setAttributionText(attribute);
    templ.setExpiration(expiration);

    if (withImage)
        templ.setImagePath(imagePath);

    if (WinToast::instance()->showToast(aumi, templ, new CustomHandler()) < 0) {
        std::wcerr << L"Could not launch your toast notification!";
        return Results::ToastFailed;
    }

    // Give the handler a chance for 15 seconds
    Sleep(15000);

    WinToast::instance()->clear(aumi);
    exit(0);
}
