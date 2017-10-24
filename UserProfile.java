package com.mershellerivera_offleash_final;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserProfile extends Activity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView picture;
    User user;
    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        ActionBar ab = getActionBar();
        ab.hide();

        //Display username from login
        final String username = getIntent().getStringExtra("Username");
        TextView tvUsername = findViewById(R.id.tvUP_Username);
        tvUsername.setText(username);

        //Settings for dog
        String dogname = getIntent().getStringExtra("Dog Name");
        TextView tvDogName = findViewById(R.id.tvDogName);

        String dogpref = getIntent().getStringExtra("Dog Pref");
        TextView tvdogpref = findViewById(R.id.tvDogPref);
        if(dogname != null){
            tvDogName.setText("Dog Name: " + dogname);
            tvdogpref.setText("Likes To: " + dogpref);
        }

        //Change profile picture
        ImageButton imgbtnMaps = findViewById(R.id.imgbtnMap);
        imgbtnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton imgbtnSettings = findViewById(R.id.imgbtnSettings);
        imgbtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, UserSettings.class);
                intent.putExtra("Username", username);
                startActivity(intent);
            }
        });

        ImageView imgvProfile = findViewById(R.id.imgvUserProfilePic);
        imgvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Invoke image gallery using implicit intent
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();

                //Get URI (Universal Resource Indicator)
                Uri data = Uri.parse(pictureDirectoryPath);

                photoPickerIntent.setDataAndType(data, "image/*"); //Get all image types

                //Invoke activity, and get photo
                startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
            }
        });

        //Reference the ImageView
        picture = findViewById(R.id.imgvUserProfilePic);

        //Logout
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_GALLERY_REQUEST){
                //Image address
                Uri imageUri = data.getData();

                //InputStream to read the image data
                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    //Get Bitmap from inputStream
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    //Display chosen image
                    picture.setImageBitmap(bitmap);
                    picture.getLayoutParams().width = 160;
                    picture.getLayoutParams().height = 160;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //Message user of image error
                    Toast.makeText(this, "Unable To Open Image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
