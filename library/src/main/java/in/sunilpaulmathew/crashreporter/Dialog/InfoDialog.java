package in.sunilpaulmathew.crashreporter.Dialog;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import in.sunilpaulmathew.crashreporter.R;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on May 02, 2026
 */
public class InfoDialog extends MaterialAlertDialogBuilder {

    private AlertDialog alertDialog = null;

    public InfoDialog(@NonNull Context context) {
        super(context);

        LayoutInflater layoutInflator = LayoutInflater.from(context);
        View aboutLayout = layoutInflator.inflate(R.layout.layout_about, null);
        MaterialTextView appTile = aboutLayout.findViewById(R.id.app_title);
        MaterialButton cancel = aboutLayout.findViewById(R.id.cancel);
        MaterialButton source = aboutLayout.findViewById(R.id.source);

        appTile.setText(context.getString(R.string.library_name));

        source.setOnClickListener(v -> {
            try {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/sunilpaulmathew/CrashReporter"));
                context.startActivity(i);
            } catch (ActivityNotFoundException ignored) {}
        });

        setIcon(R.drawable.ic_info).setView(aboutLayout);
        setCancelable(false);
        alertDialog = create();
        alertDialog.show();

        cancel.setOnClickListener(v -> alertDialog.dismiss());
    }

}