package com.prateek.secpass.splash_welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.prateek.secpass.MainActivity;
import com.prateek.secpass.R;

public class LogInActivity extends AppCompatActivity {

    private TextView hint;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        hint= findViewById(R.id.hint_textview);
        password= findViewById(R.id.login_pass);
        login= findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences= getSharedPreferences("user_password",MODE_PRIVATE);
                String pass= preferences.getString("Password","");
                String phint= preferences.getString("Passwordhint","");
                String logpass= password.getText().toString();
                if(logpass.equals(pass)){
                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
                    finish();
                }else{
                    password.setError("Incorrect Password");
                    password.requestFocus();
                    hint.setText("Hint: "+ phint);
                }

            }
        });
    }


}
