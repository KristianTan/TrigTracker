package com.uk.trigtracker.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uk.trigtracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoBoxFragment extends Fragment {


    public InfoBoxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.info_box_fragment, container, false);
    }

}
