package com.vng.app.mobilelegendsitembuilds;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.vng.app.mobilelegendsitembuilds.databinding.ActivitySplashBinding;
import com.vng.app.mobilelegendsitembuilds.model.Hero;
import com.vng.app.mobilelegendsitembuilds.model.Item;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    final long ONE_MEGABYTE = 1024 * 1024;

    private ActivitySplashBinding binder;
    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private SimpleDateFormat dateFormat;
    private FileOutputStream outputStream;
    private long ItemCountToSync = 0;
    private float progressCount = 0;
    private float itemPercent = 0;
    private ArrayList<Hero> heros = new ArrayList<Hero>();
    private ArrayList<Item> items = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this,R.layout.activity_splash);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        databaseRef = database.getReference();
        dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        binder.textviewProgressDetails.setText("Getting hero resource count.");
        databaseRef.child("hero").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    SyncData("hero",data.getKey());
                    Hero temp = data.getValue(Hero.class);
                    temp.setName(data.getKey());
                    heros.add(temp);
                }
                ItemCountToSync = dataSnapshot.getChildrenCount();
                getServerItemCount();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO FAILED SYNC
            }
        });
    }

    public void getServerItemCount(){
        binder.textviewProgressDetails.setText("Getting item resource count.");
        databaseRef.child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    SyncData("item",data.getKey());
                    Item temp = data.getValue(Item.class);
                    temp.setName(data.getKey());
                    items.add(temp);
                }
                ItemCountToSync = ItemCountToSync + dataSnapshot.getChildrenCount();
                itemPercent = 100.0f/ItemCountToSync ;
                Log.d("PERCENT", String.valueOf(itemPercent));
                Log.d("TOTAL_ITEM", String.valueOf(ItemCountToSync));
                new InitialLoadData().execute();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO FAILED SYNC
            }
        });
    }

    public void SyncData(final String type, final String fileName){
        String temp = fileName.concat(".png");
        binder.textviewProgressDetails.setText("Synchronizing "+type+" resources.");
        storageRef.child(type+"/".concat(temp)).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                String ServerModDate = dateFormat.format(new Date(storageMetadata.getUpdatedTimeMillis()));
                String LocalModDate = getLastModDate(type, fileName) != null ? dateFormat.format(getLastModDate(type,fileName)) : null;
                if (LocalModDate == null || !ServerModDate.contentEquals(LocalModDate)){
                    Log.d("FILE DOESN'T EXIST",fileName);
                    saveImage(type,fileName);
                } else {
                    Log.d("FILE EXIST",fileName);
                    Log.d("META_LOC ".concat(fileName),dateFormat.format(getLastModDate(type,fileName)));
                    binder.textviewProgressDetails.setText(type+" resource "+fileName+" already synced.");
                    addProgress();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("STORAGE_METADATA "+fileName,exception.getMessage());
                //TODO FAILED SYNC
            }
        });
    }

    public Date getLastModDate(String type, String fileName){
        String temp = fileName.concat(".png");

        File file = new File(getFilesDir(), temp);
        if (file.exists()){
            return new Date(file.lastModified());
        } else {
            return null;
        }
    }

    public void saveImage(String type, final String fileName){
        final String temp = fileName.concat(".png");
        binder.textviewProgressDetails.setText("Saving "+type+" resource "+temp);
        Log.d("SAVING",temp);
        storageRef.child(type+"/".concat(temp)).getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            outputStream = openFileOutput(temp, Context.MODE_PRIVATE);
                            outputStream.write(bytes);
                            outputStream.close();
                            addProgress();
                        } catch (Exception e) {
                            e.printStackTrace();
                            //TODO FAILED SYNC
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO FAILED SYNC
                    }
                });
    }

    public void addProgress(){
        ItemCountToSync--;
        progressCount = progressCount + itemPercent;
        binder.textviewProgressPercentage.setText(String.valueOf((int)progressCount).concat("%"));
    }


    private class InitialLoadData extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... strings) {
            Boolean isSync = false;
            while(!isSync){
               if (ItemCountToSync == 0){
                   isSync = true;
               }
            }
            return "Sync success";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Checking Sync items", String.valueOf(progressCount));
            Log.d("Checking Sync items", "SHOULD BE DONE NOW");
            binder.textviewProgressDetails.setText("Synchronization complete.");

            finish();
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            i.putParcelableArrayListExtra("HERO_LIST",heros);
            i.putParcelableArrayListExtra("ITEMS",items);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }


}
