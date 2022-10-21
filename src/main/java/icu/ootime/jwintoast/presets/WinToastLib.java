package icu.ootime.jwintoast.presets;

import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(
        target = "icu.ootime.jwintoast",
        global = "icu.ootime.jwintoast.global.WinToastLib",
        value = {
                @Platform(
                        define = "NDEBUG",
                        value = {
                                "windows-x86",
                                "windows-x86_64"
                        },
                        // cinclude = "wintoastlib.h",
                        // library = "jniWinToastLib"
                        include = {
                                "wintoastlib.cpp",
                        }
                )
        }
)
@Namespace("WinToastLib")
public class WinToastLib implements InfoMapper {
    public void map(InfoMap infoMap) {
        infoMap.put(new Info("WinToastLib").pointerTypes("WinToastLib").skip());

        infoMap.put(new Info(
                "DllImporter::f_SetCurrentProcessExplicitAppUserModelID",
                "DllImporter::f_PropVariantToString",
                "DllImporter::f_RoGetActivationFactory",
                "DllImporter::f_WindowsCreateStringReference",
                "DllImporter::f_WindowsGetStringRawBuffer",
                "DllImporter::f_WindowsDeleteString",
                "DllImporter::initialize"
        ).skip());

        infoMap.put(new Info("DEFAULT_SHELL_LINKS_PATH", "DEFAULT_LINK_FORMAT", "STATUS_SUCCESS").define(false).skip());
        infoMap.put(new Info("Util::getRealOSVersion",
                "Util::defaultExecutablePath",
                "Util::defaultShellLinksDirectory",
                "Util::defaultShellLinkPath",
                "Util::AsString",
                "Util::setNodeStringValue",
                "Util::setEventHandlers",
                "Util::addAttribute",
                "Util::createElement"
        ).skip());

        infoMap.put(new Info("RtlGetVersionPtr").pointerTypes("RtlGetVersionPtr").skip());
        infoMap.put(new Info("WinToastStringWrapper").pointerTypes("WinToastStringWrapper").skip());

        infoMap.put(new Info("InternalDateTime").pointerTypes("InternalDateTime").skip());
        infoMap.put(new Info("f_WindowsGetStringRawBuffer").pointerTypes("f_WindowsGetStringRawBuffer").skip());
        infoMap.put(new Info("f_WindowsDeleteString").pointerTypes("f_WindowsDeleteString").skip());
        infoMap.put(new Info("f_WindowsCreateStringReference").pointerTypes("f_WindowsCreateStringReference").skip());
        infoMap.put(new Info("f_SetCurrentProcessExplicitAppUserModelID").pointerTypes("f_SetCurrentProcessExplicitAppUserModelID").skip());
        infoMap.put(new Info("f_RoGetActivationFactory").pointerTypes("f_RoGetActivationFactory").skip());
        infoMap.put(new Info("f_PropVariantToString").pointerTypes("f_PropVariantToString").skip());

        // infoMap.put(new Info("IWinToastHandler").purify(false).virtualize());
        // infoMap.put(new Info("WinToastLib::IWinToastHandler::toastActivated").annotations("const "));
    }
}
