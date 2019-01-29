package com.prateek.secpass.drawerattr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prateek.secpass.MainActivity;
import com.prateek.secpass.R;
import com.prateek.secpass.settingsattr.ChangeHintActivity;
import com.prateek.secpass.settingsattr.ChangePassActivity;

public class SettingsActivity extends AppCompatActivity {


    private Button pass, hint, suggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        pass = findViewById(R.id.changepassword);
        hint = findViewById(R.id.changehint);
        suggestions = findViewById(R.id.suggestions);

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, ChangePassActivity.class));
            }
        });

        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, ChangeHintActivity.class));

            }
        });

        suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"secpass.privacy@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent.putExtra(Intent.EXTRA_TEXT, "mail body");
                startActivity(Intent.createChooser(intent, ""));
            }
        });

    }

}
