package com.uk.trigtracker.Fragments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Circle;
import com.uk.trigtracker.Models.TrigPoint;
import com.uk.trigtracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoBoxFragment extends Fragment {

    Circle circle;

    public InfoBoxFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.info_box_fragment, container, false);

        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        final TrigPoint data = (TrigPoint) bundle.get("data");

        TextView name = RootView.findViewById(R.id.info_name);
        name.setText(data.getName());

        TextView park = RootView.findViewById(R.id.national_park);
        park.setText("Located in the " + data.getPark() + " national park");

        CheckBox checkBox = RootView.findViewById(R.id.checkBox);

        if (circle.getFillColor() == Color.parseColor("#e63c60")) {
            checkBox.setChecked(true);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    circle.setFillColor(Color.parseColor("#e63c60"));
                    //TODO: For some reason this sets the circle fill colour to gray instead of actual colour of value
//                    circle.setFillColor(R.color.visited);
                } else {
                    circle.setFillColor(Color.TRANSPARENT);
                }
            }
        });

        TextView close = RootView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        return RootView;

    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
