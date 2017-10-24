package com.mershellerivera_offleash_final;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserSettings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);


        ActionBar ab = getActionBar();
        ab.hide();

        final String username = getIntent().getStringExtra("Username");
        final EditText etDogName = findViewById(R.id.etDogName);

        final Spinner spinner = findViewById(R.id.spinDogPref);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dog_preferences, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dogName = etDogName.getText().toString();
                String dogPref = spinner.getSelectedItem().toString();

                Intent intent = new Intent(UserSettings.this, UserProfile.class);
                intent.putExtra("Username", username);
                intent.putExtra("Dog Name", dogName);
                intent.putExtra("Dog Pref", dogPref);
                startActivity(intent);
            }
        });
    }
}
