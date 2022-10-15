
import icu.ootime.jwintoast.*;
import org.bytedeco.javacpp.CharPointer;
import org.bytedeco.javacpp.IntPointer;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        IWinToastHandler iWinToastHandler = new IWinToastHandler() {
            @Override
            public void toastActivated() {
                System.out.println("toast activated");
            }

            @Override
            public void toastActivated(int actionIndex) {
                System.out.println("toast activated: " + actionIndex);
            }

            @Override
            public void toastDismissed(int state) {
                System.out.println("toast dismissed: " + state);
            }

            @Override
            public void toastFailed() {
                System.out.println("toast failed");
            }
        };

        WinToast winToast = WinToast.instance();
        winToast.setAppName(new CharPointer("foo bar"));
        CharPointer aumi = winToast.configureAUMI(new CharPointer("Foo"), new CharPointer("Bar13"), new CharPointer("SubProduct"), new CharPointer("VersionInformation"));
        System.out.println("aumi: " + aumi.getString());
        winToast.setAppUserModelId(aumi);
        System.out.println("initialize: " + winToast.initialize());
        winToast.setAppGroup(new CharPointer("ddd"));
        winToast.setAppTag(new CharPointer("aaa"));

        WinToastTemplate winToastTemplate = new WinToastTemplate(WinToastTemplate.WinToastTemplateType.TOASTIMAGEANDTEXT02);
        System.out.println("template address: " + winToastTemplate.address());
        winToastTemplate.setImagePath(new CharPointer("src\\main\\resources\\images\\logo.png"));
        winToastTemplate.setExpiration(10000);
        winToastTemplate.LoadStringToXml(new CharPointer("<toast><visual><binding template=\"ToastGeneric\"><text>Downloading your weekly playlist...</text><progress title=\"Weekly playlist\" value=\"{pvalue}\" valueStringOverride=\"{pdesc}\" status=\"Downloading...\"/></binding></visual></toast>"));

        {
            HStringMap data = new HStringMap();
            data.put(new HString(new CharPointer("pdesc")), new HString(new CharPointer("0%")));
            winToastTemplate.setInitNotificationData(data);
            // We can add action buttons like this:
            // winToastTemplate.addAction(new CharPointer("yes"));
            // winToastTemplate.addAction(new CharPointer("no"));
        }

        {
            IntPointer errorCode = new IntPointer(0);
            int uid = winToast.showToast(winToastTemplate, iWinToastHandler, errorCode);
            System.out.println("toast uid: " + uid);
            System.out.println("error: " + winToast.strerror(errorCode.get()).getString());
        }

        Thread.sleep(1000);

        {
            HStringMap data = new HStringMap();
            data.put(new HString(new CharPointer("pvalue")), new HString(new CharPointer("0.1")));
            data.put(new HString(new CharPointer("pdesc")), new HString(new CharPointer("10%")));
            IntPointer errorCode = new IntPointer(0);
            // 0 = success, 1 = failure, 2 = not found
            int ret = winToast.update(data, errorCode);
            System.out.println("update return code: " + ret);
            System.out.println("error: " + winToast.strerror(errorCode.get()).getString());
        }

        Thread.sleep(2000);

        {
            HStringMap data = new HStringMap();
            data.put(new HString(new CharPointer("pvalue")), new HString(new CharPointer("1")));
            data.put(new HString(new CharPointer("pdesc")), new HString(new CharPointer("100%")));
            IntPointer errorCode = new IntPointer(0);
            System.out.println(winToast.update(data, errorCode));
        }

        Thread.sleep(15000);

        // We can hide the toast with such a line without waiting for the timeout:
        // System.out.println(winToast.hideToast(uid));

        System.out.println("done");
    }
}
