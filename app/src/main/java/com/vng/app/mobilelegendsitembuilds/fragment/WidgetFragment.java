package com.vng.app.mobilelegendsitembuilds.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vng.app.mobilelegendsitembuilds.Constant;
import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentWidgetBinding;
import com.vng.app.mobilelegendsitembuilds.service.FloatingViewService;

import java.io.File;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Nico on 3/24/2017.
 */

public class WidgetFragment extends Fragment {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private FragmentWidgetBinding binder;


    SharedPreferences sharedpreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_widget, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getContext())) {


            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + this.getActivity().getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            initializeView();
        }
        return binder.getRoot();
    }

    private void initializeView() {
        sharedpreferences = getContext().getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);
        binder.textReservedBuild1.setText(sharedpreferences.getString(Constant.BuildOneTitle, "Reserved Build One"));
        binder.textReservedBuild2.setText(sharedpreferences.getString(Constant.BuildTwoTitle, "Reserved Build Two"));
        binder.textReservedBuild3.setText(sharedpreferences.getString(Constant.BuildThreeTitle, "Reserved Build Three"));
        String[] build1 = sharedpreferences.getString(Constant.BuildOneValues, ",,,,,").split(",");
        //TODO add adapter for builds collection
        Picasso.with(getContext())
                .load(new File(getContext().getFilesDir(), build1[0].concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageFirstReservedItem1);
        Picasso.with(getContext())
                .load(new File(getContext().getFilesDir(), build1[1].concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageFirstReservedItem2);
        Picasso.with(getContext())
                .load(new File(getContext().getFilesDir(), build1[2].concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageFirstReservedItem3);
        Picasso.with(getContext())
                .load(new File(getContext().getFilesDir(), build1[3].concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageFirstReservedItem4);
        Picasso.with(getContext())
                .load(new File(getContext().getFilesDir(), build1[4].concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageFirstReservedItem5);
        Picasso.with(getContext())
                .load(new File(getContext().getFilesDir(), build1[5].concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageFirstReservedItem6);


        binder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Item Widget Initialized", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getActivity().stopService(new Intent(getActivity(), FloatingViewService.class));
                            }
                        }).show();
                getActivity().startService(new Intent(getActivity(), FloatingViewService.class));

            }
        });

        binder.fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.mobile.legends");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                    getActivity().startService(new Intent(getActivity(), FloatingViewService.class));
                }
                return true;
            }
        });

        binder.textReservedBuild1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Input Name of Build");

                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binder.textReservedBuild1.setText(input.getText().toString());
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Constant.BuildOneTitle, binder.textReservedBuild1.getText().toString());
                        editor.apply();
                        Toast.makeText(getContext(), "Name changed", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        binder.textReservedBuild2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Input Name of Build");

                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binder.textReservedBuild2.setText(input.getText().toString());
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Constant.BuildTwoTitle, binder.textReservedBuild2.getText().toString());
                        editor.apply();
                        Toast.makeText(getContext(), "Name changed", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        binder.textReservedBuild3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Input Name of Build");

                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binder.textReservedBuild3.setText(input.getText().toString());
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Constant.BuildThreeTitle, binder.textReservedBuild3.getText().toString());
                        editor.apply();
                        Toast.makeText(getContext(), "Name changed", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializeView();
            } else { //Permission is not available
                Toast.makeText(getContext(),
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
