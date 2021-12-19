package in.sunilpaulmathew.crashreporter.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import in.sunilpaulmathew.crashreporter.Activities.CrashReporterActivity;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on December 20, 2021
 * Ref: https://stackoverflow.com/questions/601503/how-do-i-obtain-crash-data-from-my-android-application
 */
public class CrashReporter implements Thread.UncaughtExceptionHandler {

    private final Context mContext;
    private final String mContacts;
    private final Thread.UncaughtExceptionHandler mDefaultUEH;

    public CrashReporter(String contacts, Context context) {
        this.mContacts = contacts;
        this.mContext = context;
        this.mDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public void uncaughtException(@NonNull Thread t, Throwable e) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        String stacktrace = result.toString();
        printWriter.close();
        Utils.create(stacktrace, new File(mContext.getExternalFilesDir("logs"), "crashLog_" + Utils.getTimeStamp()));
        Intent intent = new Intent(mContext, CrashReporterActivity.class);
        intent.putExtra("crashLog", stacktrace);
        intent.putExtra("contacts", mContacts);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
        mDefaultUEH.uncaughtException(t, e);
    }

    public void initialize() {
        Thread.setDefaultUncaughtExceptionHandler(new CrashReporter(mContacts, mContext));
    }

}