package com.mershellerivera_offleash_final;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        ActionBar ab = getActionBar();
        ab.hide();

        final EditText etName = findViewById(R.id.etName);
        final EditText etUsername = findViewById(R.id.etNewUsername);
        final EditText etPassword1 = findViewById(R.id.etPassword1);
        final EditText etPassword2 = findViewById(R.id.etPassword2);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String username = etUsername.getText().toString();
                String pw1 = etPassword1.getText().toString();
                String pw2 = etPassword2.getText().toString();

                if(!pw1.equals(pw2)){
                    Toast.makeText(Register.this, "Passwords Do Not Match", Toast.LENGTH_LONG).show();
                }else{
                    //Insert into Database
                    User user = new User();
                    user.setName(name);
                    user.setUsername(username);
                    user.setPassword(pw1);

                    helper.insertUser(user);
                    Toast.makeText(Register.this, "User Created", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
