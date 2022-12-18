package de.mobanisto.wintoast;

import de.mobanisto.wintoast.presets.WinToastLib;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.*;

@NoOffset
@Properties(inherit = WinToastLib.class)
// @Namespace("WinToastLib")
public class WinToastHandler extends Pointer {
    static {
        Loader.load();
    }

    public WinToastHandler() {
        allocate();
    }

    public native void allocate();

    public @Virtual(true)
    @Const({false, false, true})
    native void toastActivated();

    public @Virtual(true)
    @Const({false, false, true})
    native void toastActivated(int actionIndex);

    public @Virtual(true)
    @Const({false, false, true})
    native void toastDismissed(@Cast("WinToastHandler::WinToastDismissalReason") int state);

    public @Virtual(true)
    @Const({false, false, true})
    native void toastFailed();

}
