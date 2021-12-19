package in.sunilpaulmathew.crashreporter.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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

        AppCompatEditText mCrashSteps = findViewById(R.id.crash_steps);
        AppCompatImageButton mBackButton = findViewById(R.id.back);
        AppCompatImageButton mInfoButton = findViewById(R.id.info);
        MaterialCardView mCancelButton = findViewById(R.id.cancel_button);
        MaterialCardView mReportButton = findViewById(R.id.report_button);
        MaterialTextView mRequestMessage = findViewById(R.id.request_message);
        MaterialTextView mContacts = findViewById(R.id.contact_details);
        MaterialTextView mCrashLog = findViewById(R.id.crash_log);

        mRequestMessage.setText(getString(R.string.request_message, PackageUtils.getAppName(this)));

        if (getIntent().getStringExtra("contacts") != null) {
            mContacts.setText(getIntent().getStringExtra("contacts"));
        }

        if (getIntent().getStringExtra("crashLog") != null) {
            mCrashLog.setText(getIntent().getStringExtra("crashLog"));
        }

        mBackButton.setOnClickListener(view -> finish());

        mCancelButton.setOnClickListener(view -> finish());

        mInfoButton.setOnClickListener(view -> {
            LayoutInflater mLayoutInflator = LayoutInflater.from(this);
            View aboutLayout = mLayoutInflator.inflate(R.layout.layout_about, null);
            MaterialTextView mAppTile = aboutLayout.findViewById(R.id.app_title);
            MaterialTextView mSource = aboutLayout.findViewById(R.id.source);

            mAppTile.setText(getString(R.string.lib_name));
            mSource.setText(getString(R.string.source_code, "https://github.com/sunilpaulmathew/CrashReporter"));

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
            share_log.putExtra(Intent.EXTRA_TEXT, "App Name: " + PackageUtils.getAppName(this) +
                    "\nPackage Name: " + getPackageName() + "\nApp Version: " + PackageUtils.getVersionName(this) +
                    "\nSDK Version: " + Build.VERSION.SDK_INT + "\n\nCrash Report\n\n" + mCrashLog.getText() +
                    "\n\nSteps to reproduce the issue: " + mSteps);
            share_log.setType("text/plain");
            Intent shareIntent = Intent.createChooser(share_log, "Share");
            startActivity(shareIntent);
            finish();
        });
    }

}