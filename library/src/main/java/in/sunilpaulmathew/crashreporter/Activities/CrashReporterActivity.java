package in.sunilpaulmathew.crashreporter.Activities;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;

import in.sunilpaulmathew.crashreporter.R;
import in.sunilpaulmathew.crashreporter.Utils.PackageUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on December 20, 2021
 */
public class CrashReporterActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_reporter);

        AppCompatImageButton mCopyButton = findViewById(R.id.copy);
        AppCompatImageButton mInfoButton = findViewById(R.id.info);
        MaterialAutoCompleteTextView mCrashSteps = findViewById(R.id.crash_steps);
        MaterialButton mCancelButton = findViewById(R.id.cancel_button);
        MaterialButton mReportButton = findViewById(R.id.report_button);
        MaterialTextView mRequestMessage = findViewById(R.id.request_message);
        MaterialTextView mContacts = findViewById(R.id.contact_details);
        MaterialTextView mCrashLog = findViewById(R.id.crash_log);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_root), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            view.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );

            return insets;
        });

        mRequestMessage.setText(getString(R.string.request_message, PackageUtils.getAppName(this)));

        if (getIntent().getStringExtra("contacts") != null) {
            mContacts.setText(getIntent().getStringExtra("contacts"));
        }

        if (getIntent().getStringExtra("crashLog") != null) {
            String mCrashText = "App Name: " + PackageUtils.getAppName(this) +
                    "\nPackage Name: " + getPackageName() + "\nApp Version: " + PackageUtils.getVersionName(this) +
                    "\nSDK Version: " + Build.VERSION.SDK_INT + "\n\n==========\n Stacktrace\n==========\n\n" + getIntent().getStringExtra("crashLog");
            mCrashLog.setText(mCrashText);
        }

        mCancelButton.setOnClickListener(view -> finish());

        mCopyButton.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied to clipboard", mCrashLog.getText() +
                        "\n\nSteps to reproduce this issue: " + mCrashSteps.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "This feature requires Android API level 11 or more", Toast.LENGTH_SHORT).show();
            }
        });

        mInfoButton.setOnClickListener(view -> {
            LayoutInflater mLayoutInflator = LayoutInflater.from(this);
            View aboutLayout = mLayoutInflator.inflate(R.layout.layout_about, null);
            MaterialButton mAppTile = aboutLayout.findViewById(R.id.app_title);
            MaterialButton mSource = aboutLayout.findViewById(R.id.source);

            mAppTile.setText(getString(R.string.library_name));

            mSource.setOnClickListener(v -> {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://github.com/sunilpaulmathew/CrashReporter"));
                    startActivity(i);
                } catch (ActivityNotFoundException ignored) {
                }
            });

            new MaterialAlertDialogBuilder(this)
                    .setIcon(R.drawable.ic_info).setView(aboutLayout)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.cancel), (dialogInterface, i) -> {
                    }).show();
        });

        mReportButton.setOnClickListener(view -> {
            String mSteps = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && mCrashSteps.getText() != null &&
                    !mCrashSteps.getText().toString().trim().isEmpty()) {
                mSteps = mCrashSteps.getText().toString();
            }
            Intent share_log = new Intent();
            share_log.setAction(Intent.ACTION_SEND);
            share_log.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crash_log) + "/" + PackageUtils.getAppName(this));
            share_log.putExtra(Intent.EXTRA_TEXT, mCrashLog.getText() +
                    "\n\nSteps to reproduce this issue: " + mSteps);
            share_log.setType("text/plain");
            Intent shareIntent = Intent.createChooser(share_log, "Share");
            startActivity(shareIntent);
            finish();
        });
    }

}