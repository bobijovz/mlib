package com.vng.app.mobilelegendsitembuilds;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    final long ONE_MEGABYTE = 1024 * 1024;
    private ArrayList<String> hero_list = new ArrayList<>();
    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private SimpleDateFormat dateFormat;
    private FileOutputStream outputStream;
    private long ItemCountToSync = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        databaseRef = database.getReference();
        dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

        databaseRef.child("hero").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    hero_list.add(data.getKey().replaceAll("\\W",""));
                    SyncMe(data.getKey().replaceAll("\\W",""));
                }
                ItemCountToSync = dataSnapshot.getChildrenCount();
                new InitialLoadData().execute();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       // Intent i = new Intent(SplashActivity.this, LoginActivity.class);
       // startActivity(i);

    }

    public void SyncMe(final String fileName){
        String temp = fileName.concat(".png");
        storageRef.child("hero/".concat(temp)).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                String ServerModDate = dateFormat.format(new Date(storageMetadata.getUpdatedTimeMillis()));
                String LocalModDate = getLastModDate(fileName) != null ? dateFormat.format(getLastModDate(fileName)) : null;
                if (LocalModDate == null || !ServerModDate.contentEquals(LocalModDate)){
                    Log.d("FILE DOESN'T EXIST",fileName);
                    saveImage(fileName);
                } else {
                    Log.d("FILE EXIST",fileName);
                    Log.d("META_LOC".concat(fileName),dateFormat.format(getLastModDate(fileName)));
                    ItemCountToSync--;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("STORAGE_METADATA",exception.getMessage());
            }
        });
    }

    public Date getLastModDate(String fileName){
        String temp = fileName.concat(".png");
        File file = new File(getFilesDir(), temp);
        if (file.exists()){
            return new Date(file.lastModified());
        } else {
            return null;
        }
    }

    public void saveImage(final String fileName){
        final String temp = fileName.concat(".png");
        Log.d("SAVING",temp);
        storageRef.child("hero/".concat(temp)).getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            outputStream = openFileOutput(temp, Context.MODE_PRIVATE);
                            outputStream.write(bytes);
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    private class InitialLoadData extends AsyncTask<String, Integer, Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean isSync = false;
            while(!isSync){
               if (ItemCountToSync == 0){
                   isSync = true;
                   return true;
               }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            Log.d("Checking Sync items", String.valueOf(s));
            Log.d("Checking Sync items", String.valueOf(ItemCountToSync));
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }


}
