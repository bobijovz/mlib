package com.vng.app.mobilelegendsitembuilds.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentItemBuilderBinding;


/**
 * Created by Nico on 3/24/2017.
 */

public class ItemBuilderFragment extends Fragment {

    private FragmentItemBuilderBinding binder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_item_builder, container, false);

        return binder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
