package de.mobanisto.wintoast;

import de.mobanisto.wintoast.presets.WinToastLib;
import org.bytedeco.javacpp.CharPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.*;

@NoOffset
@Properties(inherit = WinToastLib.class)
public class WinToast extends Pointer {
    static {
        Loader.load();
    }

    public WinToast() {
        allocate();
    }

    public static native WinToast instance();

    public native @Cast("uint32_t") long showToast(
            @Const @ByRef WinToastTemplate winToastTemplate,
            IWinToastHandler iWinToastHandler,
            @Cast("WinToastLib::WinToast::WinToastError *") IntPointer erro);

    public native void setAppName(@Const @StdWString CharPointer name);

    public native @StdWString CharPointer configureAUMI(@Const @StdWString CharPointer companyName,
                                                        @Const @StdWString CharPointer productName,
                                                        @Const @StdWString CharPointer subProduct,
                                                        @Const @StdWString CharPointer versionInformation);

    public native void setAppUserModelId(@Const @StdWString CharPointer appUserModelId);

    public native void setShortcutPolicy(@Cast("WinToastLib::WinToast::ShortcutPolicy") int code);

    public native boolean initialize();

    public native void clear();

    public native @StdWString
    @Const CharPointer strerror(@Cast("WinToastLib::WinToast::WinToastError") int code);

    public native boolean isCompatible();

    public native boolean isSupportingModernFeatures();

    public native boolean hideToast(long id);

    public native void allocate();

    /**
     * enum WinToastLib::WinToast::ShortcutPolicy
     */
    public static class ShortcutPolicy {
        public static final int ShortcutPolicyIgnore = 0,
                ShortcutPolicyRequireNoCreate = 1,
                ShortcutPolicyRequireCreate = 2;
    }
}
