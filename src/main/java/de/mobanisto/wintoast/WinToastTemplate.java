package de.mobanisto.wintoast;

import de.mobanisto.wintoast.presets.WinToastLib;
import org.bytedeco.javacpp.CharPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.*;

@Properties(inherit = WinToastLib.class)
@Namespace("WinToastLib")
public class WinToastTemplate extends Pointer {
    static {
        Loader.load();
    }

    public WinToastTemplate() {
        allocate();
    }

    public WinToastTemplate(@Cast("WinToastTemplate::WinToastTemplateType") int winToastTemplateType) {
        allocate(winToastTemplateType);
    }
    // public WinToastTemplate(Pointer p) { super(p); }

    /**
     * Native array allocator. Access with {@link Pointer#position(long)}.
     */
    // public WinToastTemplate(long size) { super((Pointer)null); allocateArray(size); }
    // private native void allocateArray(long size);
    public native void allocate();

    public native void allocate(@Cast("WinToastTemplate::WinToastTemplateType") int winToastTemplateType);

    public native void setFirstLine(@Const @StdWString CharPointer text);

    public native void setAudioOption(@Cast("WinToastTemplate::AudioOption") int audioOption);

    public native void setTextField(@Const @StdWString CharPointer text,
                                    @Cast("WinToastTemplate::TextField") int pos);

    public native void addAction(@Const @StdWString CharPointer label);

    public native void setAttributionText(@Const @StdWString CharPointer text);

    public native void setDuration(@Cast("WinToastTemplate::Duration") int duration);

    public native void setImagePath(@Const @StdWString CharPointer imagepath);

    public native void setExpiration(int millisecondsFromNow);

    @Override
    public WinToastTemplate position(long position) {
        return super.position(position);
    }

    /**
     * enum WinToastLib::WinToastTemplate::Duration
     */
    public static class Duration {
        public static final int System = 0, Short = 1, Long = 2;
    }

    /**
     * enum WinToastLib::WinToastTemplate::AudioOption
     */
    public static class AudioOption {
        public static final int Default = 0, Silent = 1, Loop = 2;
    }

    /**
     * enum WinToastLib::WinToastTemplate::TextField
     */
    public static class TextField {
        public static final int FirstLine = 0, SecondLine = 1, ThirdLine = 2;
    }

    /**
     * enum WinToastLib::WinToastTemplate::WinToastTemplateType
     */
    public enum WinToastTemplateType {

        ToastImageAndText01(0, 1),
        ToastImageAndText02(1, 2),
        ToastImageAndText03(2, 2),
        ToastImageAndText04(3, 3),
        ToastText01(4, 1),
        ToastText02(5, 2),
        ToastText03(6, 2),
        ToastText04(7, 3);

        private int id;
        private int numTextFields;

        WinToastTemplateType(int id, int numTextFields) {
            this.id = id;
            this.numTextFields = numTextFields;
        }

        public int getId() {
            return id;
        }

        public int getNumTextFields() {
            return numTextFields;
        }
    }
}
