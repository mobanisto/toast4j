package icu.ootime.jwintoast;

import icu.ootime.jwintoast.presets.WinToastLib;
import org.bytedeco.javacpp.CharPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.annotation.StdWString;

@Properties(inherit = WinToastLib.class)
@Name("winrt::hstring")
public class HString extends Pointer {
    static {
        Loader.load();
    }

    public HString() {
        allocate();
    }

    public HString(@StdWString CharPointer charPointer) {
        allocate(charPointer);
    }

    public native void allocate();

    public native void allocate(@StdWString CharPointer charPointer);

}
