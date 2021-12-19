package in.sunilpaulmathew.common.crashreporter.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.card.MaterialCardView;

import in.sunilpaulmathew.crashreporter.Utils.CrashReporter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CrashReporter("E-Mail: smartpack.org@gmail.com", this).initialize();

        MaterialCardView clickMe = findViewById(R.id.click_me);
        clickMe.setOnClickListener(view -> {
            AppCompatEditText clickMeText = findViewById(R.id.text);
            clickMeText.setText("Hello World");
        });
    }
}