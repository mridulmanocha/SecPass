package com.prateek.secpass.settingsattr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.prateek.secpass.R;
import com.prateek.secpass.drawerattr.SettingsActivity;

public class ChangeHintActivity extends AppCompatActivity {
    EditText oldhint, newhint, confirmnewhint;
    private String oldh, newh, cnewh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_hint);
        oldhint = findViewById(R.id.old_pass_hint_edit);
        newhint = findViewById(R.id.new_pass_hint_edit);
        confirmnewhint = findViewById(R.id.confirm_new_pass_hint_edit);
        oldh = oldhint.getText().toString();
        newh = newhint.getText().toString();
        cnewh = confirmnewhint.getText().toString();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings_save:
                SharedPreferences preferences = getSharedPreferences("user_password", MODE_PRIVATE);
                String phhint = preferences.getString("Passwordhint", "");
                if(oldhint.getText().toString() == null  || newhint.getText().toString() == null || confirmnewhint.getText().toString() == null || oldhint.getText().toString().length() <= 0 || newhint.getText().toString().length() <= 0 || confirmnewhint.getText().toString().length() <= 0){
                    Toast.makeText(ChangeHintActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    if(oldhint.getText().toString().equals(phhint) && newhint.getText().toString().equals(confirmnewhint.getText().toString())){

                        SharedPreferences.Editor editor= preferences.edit();
                        editor.putString("Passwordhint", newhint.getText().toString());
                        editor.apply();
                        Toast.makeText(ChangeHintActivity.this, "Password Hint Changed Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ChangeHintActivity.this, SettingsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(ChangeHintActivity.this, " Hint can not be Changed", Toast.LENGTH_SHORT).show();
                    }
                }


                break;


        }
        return true;
    }
}
