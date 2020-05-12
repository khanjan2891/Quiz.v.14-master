package com.bvm.ui_example_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    private static String username = "18EL003";
    private static TextView Username, Email, Contact;
    private static EditText result;
    FloatingActionButton fab1, fab2, fab3;
    public static final int GET_FROM_GALLERY = 3;
    SharedPreferences sharedPref;
    SharedPreferences sharedPreferences;
    public static Boolean answered, buttonpressed;

    /************************************/

    public Profile(FloatingActionButton fab1) {
        this.fab1 = fab1;
    }
    public Profile(){

    }

    public static Boolean getButtonpressed() {
        return buttonpressed;
    }

    /************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        CircleImageView circleImageView=(CircleImageView)findViewById(R.id.profile_image);
        Username = findViewById(R.id.textView8);
        Email = findViewById(R.id.textView9);
        Contact = findViewById(R.id.textView10);
        fab1 = findViewById(R.id.floatingActionButton3);
        fab2 = findViewById(R.id.floatingActionButton6);
        fab3 = findViewById(R.id.floatingActionButton7);




        getDetails();
        sharedPref = getSharedPreferences("PrefsFile1", MODE_PRIVATE);

        String flag = sharedPref.getString("flag", "not found");
        String user = sharedPref.getString("user_p", "not found");

        if(flag.equals("true"))
        {loadImageFromStorage(user);}
        else{

        }

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonpressed = Boolean.FALSE;
                openDialog();
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonpressed = Boolean.TRUE;
                openDialog1();
            }
        });
    }

    private void openDialog1() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(),"example dialog1");
    }

    private void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }


    private void loadImageFromStorage(String path) {
        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            //ImageView img=(ImageView)findViewById(R.id.imageView3);
            CircleImageView circleImageView=(CircleImageView)findViewById(R.id.profile_image);
            circleImageView.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    private void getDetails() {
        MainActivity activity = new MainActivity();
        final String usr = MainActivity.getUsername1();
        String psd = MainActivity.getPassword1();
        Username.setText(usr);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String email_id = dataSnapshot.child("Login Info").child(usr).child("mail").getValue().toString();
                String contact_info = dataSnapshot.child("Login Info").child(usr).child("contact").getValue().toString();
                Email.setText(email_id);
                Contact.setText(contact_info);
                Username.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detect  request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK){
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try{
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            }catch(FileNotFoundException e){
                e.printStackTrace();

            }catch (IOException e){
                e.printStackTrace();
            }

            //imageView.setImageBitmap(bitmap);
            saveToInternalStorage(bitmap);
        }
    }

    private String saveToInternalStorage(Bitmap bitmap) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String path1 = directory.getAbsolutePath();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_p", path1);
        editor.putString("flag", "true");
        editor.apply();
        answered = true;

        return directory.getAbsolutePath();
    }
}
