package de.mobanisto.toast4j;

import de.mobanisto.wintoast.WinToastTemplate;
import de.mobanisto.wintoast.WinToastTemplate.AudioOption;
import de.mobanisto.wintoast.WinToastTemplate.WinToastTemplateType;
import org.bytedeco.javacpp.CharPointer;

public class ToastBuilder {

    private WinToastTemplateType type = null;
    private String line1 = null;
    private String line2 = null;
    private String line3 = null;
    private String image = null;
    private String attribution = null;
    private int audioOption = AudioOption.Default;
    private int expiration = 0;

    public ToastBuilder(WinToastTemplateType type) {
        this.type = type;
    }

    public WinToastTemplate build() {
        WinToastTemplate winToastTemplate = new WinToastTemplate(type.getId());
        if (type.getNumTextFields() >= 1 && line1 != null) {
            winToastTemplate.setTextField(new CharPointer(line1), WinToastTemplate.TextField.FirstLine);
        }
        if (type.getNumTextFields() >= 2 && line2 != null) {
            winToastTemplate.setTextField(new CharPointer(line2), WinToastTemplate.TextField.SecondLine);
        }
        if (type.getNumTextFields() >= 3 && line3 != null) {
            winToastTemplate.setTextField(new CharPointer(line3), WinToastTemplate.TextField.ThirdLine);
        }
        if (attribution != null) {
            winToastTemplate.setAttributionText(new CharPointer(attribution));
        }
        if (image != null) {
            winToastTemplate.setImagePath(new CharPointer(image));
        }
        winToastTemplate.setAudioOption(audioOption);
        winToastTemplate.setExpiration(expiration);
        return winToastTemplate;
    }

    public ToastBuilder setType(WinToastTemplateType type) {
        this.type = type;
        return this;
    }

    public ToastBuilder setLine1(String line) {
        this.line1 = line;
        return this;
    }

    public ToastBuilder setLine2(String line) {
        this.line2 = line;
        return this;
    }

    public ToastBuilder setLine3(String line) {
        this.line3 = line;
        return this;
    }

    public ToastBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    public ToastBuilder setDefaultAudio() {
        return setAudioOption(AudioOption.Default);
    }

    public ToastBuilder setSilent() {
        return setAudioOption(AudioOption.Silent);
    }

    public ToastBuilder setLoopAudio() {
        return setAudioOption(AudioOption.Loop);
    }

    private ToastBuilder setAudioOption(int audioOption) {
        this.audioOption = audioOption;
        return this;
    }

    public ToastBuilder setExpiration(int expiration) {
        this.expiration = expiration;
        return this;
    }
}
