package com.mershellerivera_offleash_final;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hide Actionbar at top
        ActionBar ab = getActionBar();
        ab.hide();

        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etPassword = findViewById(R.id.etPassword);

        TextView tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                String pw = helper.searchPassword(username);
                if(password.equals(pw)){
                    Intent intent = new Intent(MainActivity.this, UserProfile.class);
                    intent.putExtra("Username", username);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Username And Password Not Found", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
