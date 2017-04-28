package com.vng.app.mobilelegendsitembuilds.service;

import android.app.Service;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.databinding.LayoutFloatingWidgetBinding;
import com.vng.app.mobilelegendsitembuilds.model.Item;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Nico on 3/24/2017.
 */

public class FloatingViewService extends Service {
    private WindowManager mWindowManager;
    //private View mFloatingView;
    private ArrayList<Item> itemBuild = new ArrayList<>();
    private LayoutFloatingWidgetBinding binder;

    public FloatingViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        itemBuild = intent.getParcelableArrayListExtra("BUILD_SET");

        if (itemBuild.size() > 0) {
            Picasso.with(getApplicationContext())
                    .load(new File(getApplicationContext().getFilesDir(), itemBuild.get(0).getName().concat(".png")))
                    .fit()
                    .centerCrop()
                    .into(binder.imageItem1);
            Picasso.with(getApplicationContext())
                    .load(new File(getApplicationContext().getFilesDir(), itemBuild.get(1).getName().concat(".png")))
                    .fit()
                    .centerCrop()
                    .into(binder.imageItem2);
            Picasso.with(getApplicationContext())
                    .load(new File(getApplicationContext().getFilesDir(), itemBuild.get(2).getName().concat(".png")))
                    .fit()
                    .centerCrop()
                    .into(binder.imageItem3);
            Picasso.with(getApplicationContext())
                    .load(new File(getApplicationContext().getFilesDir(), itemBuild.get(3).getName().concat(".png")))
                    .fit()
                    .centerCrop()
                    .into(binder.imageItem4);
            Picasso.with(getApplicationContext())
                    .load(new File(getApplicationContext().getFilesDir(), itemBuild.get(4).getName().concat(".png")))
                    .fit()
                    .centerCrop()
                    .into(binder.imageItem5);
            Picasso.with(getApplicationContext())
                    .load(new File(getApplicationContext().getFilesDir(), itemBuild.get(5).getName().concat(".png")))
                    .fit()
                    .centerCrop()
                    .into(binder.imageItem6);
        }
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onCreate() {
        super.onCreate();
        //Inflate the floating view layout we created
        binder = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.layout_floating_widget,
                null,
                false);

        //mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);

        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(binder.getRoot(), params);

        binder.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });


        binder.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binder.collapseView.setVisibility(View.VISIBLE);
                binder.expandedContainer.setVisibility(View.GONE);
                binder.selectBuildContainer.setVisibility(View.GONE);
                binder.selectBuildContainer2.setVisibility(View.GONE);
                binder.selectBuildContainer3.setVisibility(View.GONE);
            }
        });


        binder.openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binder.selectBuildContainer.getVisibility() == View.GONE) {
                    binder.selectBuildContainer.setVisibility(View.VISIBLE);
                    binder.selectBuildContainer2.setVisibility(View.VISIBLE);
                    binder.selectBuildContainer3.setVisibility(View.VISIBLE);
                    binder.openButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_expand_less_white_24dp, null));
                } else {
                    binder.selectBuildContainer.setVisibility(View.GONE);
                    binder.selectBuildContainer2.setVisibility(View.GONE);
                    binder.selectBuildContainer3.setVisibility(View.GONE);
                    binder.openButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_expand_more_white_24dp, null));
                }
                binder.collapseView.setVisibility(View.GONE);
                binder.expandedContainer.setVisibility(View.VISIBLE);

            }
        });

        binder.rootContainer.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:


                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;


                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);


                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(binder.getRoot(), params);
                        return true;

                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);


                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
                                //When user clicks on the image view of the collapsed layout,
                                //visibility of the collapsed layout will be changed to "View.GONE"
                                //and expanded view will become visible.
                                binder.collapseView.setVisibility(View.GONE);
                                binder.expandedContainer.setVisibility(View.VISIBLE);
                            }
                        }
                        return true;
                }
                return false;
            }
        });

    }

    private boolean isViewCollapsed() {
        return binder.collapseView.getVisibility() == View.VISIBLE;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (binder != null) mWindowManager.removeView(binder.getRoot());
    }
}
