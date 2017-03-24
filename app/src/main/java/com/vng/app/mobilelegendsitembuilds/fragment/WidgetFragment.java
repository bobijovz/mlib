package com.vng.app.mobilelegendsitembuilds.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        binder.buttonOpenWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getActivity(), FloatingViewService.class));
            }
        });

        binder.buttonOpenMoba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.mobile.legends");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                    getActivity().startService(new Intent(getActivity(), FloatingViewService.class));
                }
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
