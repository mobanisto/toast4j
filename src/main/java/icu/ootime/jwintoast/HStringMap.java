package icu.ootime.jwintoast;

import icu.ootime.jwintoast.presets.WinToastLib;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.*;

@Properties(inherit = WinToastLib.class)
@Name("std::map<winrt::hstring, winrt::hstring>")
public class HStringMap extends Pointer {
    static {
        Loader.load();
    }

    public HStringMap() {
        allocate();
    }

    private native void allocate();

    public native long size();

    public native void clear();

    public boolean isEmpty() {
        return size() == 0;
    }

    // @Index @Name("find") public native @StdWString CharPointer get(@StdWString CharPointer key);
    @ValueSetter
    @Index
    @Name("insert")
    public native void put(@ByRef HString pointer, @ByRef HString bytePointer);

    public native @ByVal Iterator begin();

    public native @ByVal Iterator end();

    @NoOffset
    @Name("iterator")
    public static class Iterator extends Pointer {
        public Iterator(Pointer p) {
            super(p);
        }

        public Iterator() {
        }

        public native @Name("operator++")
        @ByRef Iterator increment();

        public native @Name("operator==") boolean equals(@ByRef Iterator it);
        // public native @Name("operator*().first") @MemberGetter @StdWString CharPointer first();
        // public native @Name("operator*().second") @MemberGetter @StdWString CharPointer second();
    }
}
