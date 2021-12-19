package in.sunilpaulmathew.crashreporter.Utils;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on December 20, 2021
 */
public class Utils {

    @SuppressLint("SimpleDateFormat")
    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }

    public static void create(String text, File path) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(text);
            writer.close();
        } catch (IOException ignored) {
        }
    }

}