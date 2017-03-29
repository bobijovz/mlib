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
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentWidgetBinding;
import com.vng.app.mobilelegendsitembuilds.service.FloatingViewService;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Nico on 3/24/2017.
 */

public class WidgetFragment extends Fragment {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private FragmentWidgetBinding binder;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String BuildOne = "buildOneKey";
    public static final String BuildTwo = "buildTwoKey";
    public static final String BuildThree = "buildThreeKey";

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
        sharedpreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        binder.textReservedBuild1.setText(sharedpreferences.getString(BuildOne, "Reserved Build One"));
        binder.textReservedBuild2.setText(sharedpreferences.getString(BuildTwo, "Reserved Build Two"));
        binder.textReservedBuild3.setText(sharedpreferences.getString(BuildThree, "Reserved Build Three"));
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
                        editor.putString(BuildOne, binder.textReservedBuild1.getText().toString());
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
                        editor.putString(BuildTwo, binder.textReservedBuild2.getText().toString());
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
                        editor.putString(BuildThree, binder.textReservedBuild3.getText().toString());
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
