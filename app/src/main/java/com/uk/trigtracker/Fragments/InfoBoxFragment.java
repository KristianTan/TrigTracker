package com.uk.trigtracker.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.uk.trigtracker.Models.TrigPoint;
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


    public void setData(TrigPoint trigPoint) {
        TextView name = (TextView)getActivity().findViewById(R.id.info_name);
        name.setText(trigPoint.getName());
    }

}
