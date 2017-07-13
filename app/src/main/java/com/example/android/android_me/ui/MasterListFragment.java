package com.example.android.android_me.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

// This fragment displays all the Android-Me images in a grid view
public class MasterListFragment extends Fragment {
    OnImageClickListener mCallback;

    // An interface for calling a method in the host activity
    public interface OnImageClickListener {
        void onImageSelected(int position);
    }

    public MasterListFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Make sure that the host activity has implemented the callback interface
        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnImageClickListener!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.images_grid_view);

        // Create the adaptor that takes in the context and list of all images to display
        MasterListAdapter adaptor = new MasterListAdapter(getContext(), AndroidImageAssets.getAll());
        gridView.setAdapter(adaptor);

        // Set a click listener on the gridView and trigger the callback when an item is clicked
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCallback.onImageSelected(position);
            }
        });

        return rootView;
    }
}
