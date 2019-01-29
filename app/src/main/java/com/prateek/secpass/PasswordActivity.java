package com.prateek.secpass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {

    Button button;
    EditText pass, confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        button = findViewById(R.id.pass_submit_button);
        pass = findViewById(R.id.password_first);
        confirmpass = findViewById(R.id.password_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    if (confirmpass.getText().toString().equals(pass.getText().toString())) {

                        final AlertDialog dialogBuilder = new AlertDialog.Builder(PasswordActivity.this).create();
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                        dialogBuilder.setCancelable(false);

                        final EditText editText = (EditText) dialogView.findViewById(R.id.alertedittext);
                        Button button1 = (Button) dialogView.findViewById(R.id.continue_dialog_button);
                        Button button2 = (Button) dialogView.findViewById(R.id.cancel_dialog_button);

                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogBuilder.dismiss();
                            }
                        });
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (editText.getText().toString() == null || editText.getText().toString().length() <= 0) {
                                    editText.setError("Set up a hint!");
                                    editText.requestFocus();

                                } else {

                                    SharedPreferences sharedPreferences= getSharedPreferences("user_password",MODE_PRIVATE);
                                    SharedPreferences.Editor editor= sharedPreferences.edit();
                                    editor.putString("Password", pass.getText().toString());
                                    editor.putString("Passwordhint", editText.getText().toString());
                                    editor.putBoolean("isSignUp",true);

                                    editor.apply();
                                    dialogBuilder.dismiss();
                                    Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }

                            }
                        });

                        dialogBuilder.setView(dialogView);
                        dialogBuilder.show();

                    } else {
                        Toast.makeText(PasswordActivity.this, "Password match Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean checkValidation() {
        if (pass.getText().toString() == null || pass.getText().toString().length() <= 0) {
            pass.setError("Password can not be empty.");
            pass.requestFocus();
            return false;
        }
        if (confirmpass.getText().toString() == null || confirmpass.getText().toString().length() <= 0) {
            confirmpass.setError("Incorrect match");
            confirmpass.requestFocus();
            return false;
        }
        return true;
    }
}
