package com.vng.app.mobilelegendsitembuilds.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.adapter.ImageAdapter;
import com.vng.app.mobilelegendsitembuilds.databinding.DialogItemBinding;
import com.vng.app.mobilelegendsitembuilds.model.Item;

import java.util.ArrayList;

/**
 * Created by Nico on 3/28/2017.
 */

public class ItemDialog extends DialogFragment {

    private DialogItemBinding binder;
    private ArrayList<Item> items = new ArrayList<>();
    private ImageAdapter adapter;
    private ImageAdapter.ImageAdapterListener listener;
    private RecyclerView recyclerView;
    private View view;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_item, container, false);
        setDialogWindow();

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            items = getArguments().getParcelableArrayList("items");
            adapter = new ImageAdapter(getActivity().getApplicationContext());
            adapter.setItems(items);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_item_list);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 6));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setListener(listener);
        }
    }

    private void setDialogWindow() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = View.MeasureSpec.UNSPECIFIED;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_corner);
        getDialog().getWindow().setDimAmount(0.8f);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimationFromBottom;
    }

    public void setAdapterListener(ImageAdapter.ImageAdapterListener listener) {
        this.listener = listener;
    }
}
