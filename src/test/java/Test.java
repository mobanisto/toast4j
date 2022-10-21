import icu.ootime.jwintoast.Aumi;
import icu.ootime.jwintoast.helper.ToastHandle;
import icu.ootime.jwintoast.helper.WinToastHelper;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        WinToastHelper toastHelper = new WinToastHelper("WinToast");
        Path cwd = Paths.get(System.getProperty("user.dir"));
        String image = cwd.resolve("example/terminal.png").toAbsolutePath().toString();
        ToastHandle toast1 = toastHelper.showToast("Foo", image);
        Thread.sleep(2000);
        toast1.hide();
        ToastHandle toast2 = toastHelper.showToast("Bar", image);
        Thread.sleep(5000);
        toast2.hide();
    }

}

