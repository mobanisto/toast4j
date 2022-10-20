package icu.ootime.jwintoast;

import icu.ootime.jwintoast.presets.WinToastLib;
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

    public native @Cast("uint32_t") int showToast(
            @Const @ByRef WinToastTemplate winToastTemplate,
            IWinToastHandler iWinToastHandler,
            @Cast("WinToastLib::WinToast::WinToastError *") IntPointer erro);

    // public native void notifier2(@Cast("bool *") boolean sessed, @ByRef HString hString);

    public native void setAppName(@Const @StdWString CharPointer name);

    public native void setAppTag(@Const @StdWString CharPointer tagname);

    public native void setAppGroup(@Const @StdWString CharPointer groupname);

    public native @Cast("::NotificationUpdateResult") int update(@ByRef HStringMap hStringMap, @Cast("WinToastLib::WinToast::WinToastError *") IntPointer erro);

    public native @StdWString CharPointer configureAUMI(@Const @StdWString CharPointer companyName,
                                                        @Const @StdWString CharPointer productName,
                                                        @Const @StdWString CharPointer subProduct,
                                                        @Const @StdWString CharPointer versionInformation);

    public native void setAppUserModelId(@Const @StdWString CharPointer appUserModelId);

    public native boolean initialize();

    public native void clear();

    public native @StdWString
    @Const CharPointer strerror(@Cast("WinToastLib::WinToast::WinToastError") int code);

    public native boolean isCompatible();

    public native boolean isSupportingModernFeatures();

    public native boolean hideToast(int id);

    // public native @Cast("uint32_t") int showToast(@Const @ByRef WinToastTemplate winToastTemplate, @ByRef IWinToastHandler handler);

    public native void allocate();

}
