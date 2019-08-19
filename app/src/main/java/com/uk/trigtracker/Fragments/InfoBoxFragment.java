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

        View RootView = inflater.inflate(R.layout.info_box_fragment, container, false);

        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        TrigPoint data = (TrigPoint)bundle.get("data");

        TextView name = RootView.findViewById(R.id.info_name);
        name.setText(data.getName());

        TextView park = RootView.findViewById(R.id.national_park);
        park.setText("Located in the " + data.getPark() + " national park");

        return RootView;

    }


    public void setData(TrigPoint trigPoint) {
        TextView name = (TextView)getActivity().findViewById(R.id.info_name);
        name.setText(trigPoint.getName());
    }

}
