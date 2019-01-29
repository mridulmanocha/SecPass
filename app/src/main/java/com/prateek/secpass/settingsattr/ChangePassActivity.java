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

public class ChangePassActivity extends AppCompatActivity {

    EditText oldpass, newpass, connewpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        oldpass = findViewById(R.id.old_pass_edit);
        newpass = findViewById(R.id.new_pass_edit);
        connewpass = findViewById(R.id.confirm_new_pass_edit);

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
                SharedPreferences preferences= getSharedPreferences("user_password",MODE_PRIVATE);
                String pass= preferences.getString("Password","");
                //String phint= preferences.getString("Passwordhint","");
                if(oldpass.getText().toString()==null || newpass.getText().toString() == null || connewpass.getText().toString() == null || oldpass.getText().toString().length() <= 0 ||
                        newpass.getText().toString().length() <= 0 || connewpass.getText().toString().length() <= 0){
                    Toast.makeText(ChangePassActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                    oldpass.requestFocus();
                }else{
                    if(oldpass.getText().toString().equals(pass) && newpass.getText().toString().equals(connewpass.getText().toString())){
                        SharedPreferences.Editor editor= preferences.edit();
                        editor.putString("Password", newpass.getText().toString());
                        editor.apply();
                        Toast.makeText(ChangePassActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ChangePassActivity.this, SettingsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        /*startActivity(new Intent(ChangePassActivity.this, SettingsActivity.class));
                        finish();*/

                    }else{
                        Toast.makeText(ChangePassActivity.this, "Password can not be changed", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

            return true;
    }
}
