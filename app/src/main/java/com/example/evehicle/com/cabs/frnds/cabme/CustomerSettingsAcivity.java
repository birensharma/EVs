package com.example.evehicle.com.cabs.frnds.cabme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.evehicle.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomerSettingsAcivity extends AppCompatActivity {

    private EditText mNameField, mPhoneField;

    private Button nBack, mConfirm;
    private ImageView mProfileImage;

    private FirebaseAuth mAuth;
    private DatabaseReference mCustomerDatabase;

    private String userID;
    private String mName;
    private String mPhone;
    private String mProfileImageUrl;

    private Uri resultUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_settings_acivity);

        mNameField =  findViewById(R.id.name);
        mPhoneField =  findViewById(R.id.phone);

        mProfileImage =  findViewById(R.id.profileImage);


//        nBack =  findViewById(R.id.back);
        mConfirm = findViewById(R.id.confirm);

        mAuth = FirebaseAuth.getInstance();
        userID=mAuth.getCurrentUser().getEmail();
        mNameField.setText(userID);
//        getUserInfo();


//        mProfileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent,1);
//            }
//        });


        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                saveUserInformation();
                finish();
            }
        });

//        nBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                return;
//            }
//        });
    }



//    private void getUserInfo(){
//        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }
//    private void saveUserInformation() {
//        mName = mNameField.getText().toString();
//        mPhone = mPhoneField.getText().toString();
//
//
//        Map userInfo = new HashMap();
//        userInfo.put("name",mName);
//        userInfo.put("phone",mPhone);
//        mCustomerDatabase.updateChildren(userInfo);
//        if (resultUri != null){
//
//            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID);
//            Bitmap bitmap = null;
//
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            ByteArrayOutputStream baos  =  new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
//            byte[] data = baos.toByteArray();
//            UploadTask uploadTask = filepath.putBytes(data);
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    finish();
//                    return;
//                }
//            });
//
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();
//
//                    Map newImage = new HashMap();
//                    newImage.put("profileImageUrl",downloadUrl.toString());
//                    mCustomerDatabase.updateChildren(newImage);
//
//                    finish();
//                    return;
//                }
//            });
//
//        } else {
//            finish();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
//            final Uri imageUri = data.getData();
//            resultUri = imageUri;
//            mProfileImage.setImageURI(resultUri);


        }
}
