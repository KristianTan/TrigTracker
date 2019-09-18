package com.uk.trigtracker.Fragments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.Circle;
import com.google.gson.Gson;
import com.uk.trigtracker.Models.TrigPoint;
import com.uk.trigtracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoBoxFragment extends Fragment {

    Circle circle;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    TrigPoint trigPoint;

    public InfoBoxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.info_box_fragment, container, false);

        prefs = RootView.getContext().getSharedPreferences("MyPref", 0);
        editor = prefs.edit();

        circle.setStrokeColor(Color.WHITE);
        circle.setStrokeWidth(12);

        TextView name = RootView.findViewById(R.id.info_name);
        name.setText(trigPoint.getName());

        TextView park = RootView.findViewById(R.id.national_park);
        park.setText("Located in the " + trigPoint.getPark() + " national park");

        CheckBox checkBox = RootView.findViewById(R.id.checkBox);

        if (circle.getFillColor() == getResources().getColor(R.color.visited)) {
            checkBox.setChecked(true);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    circle.setFillColor(getResources().getColor(R.color.visited));

                    Gson gson = new Gson();
                    String json = gson.toJson(trigPoint);

                    editor.putString(trigPoint.getName(), json);

                    editor.commit();
                } else {
                    circle.setFillColor(Color.TRANSPARENT);
                    editor.remove(trigPoint.getName());
                    editor.commit();
                }
            }
        });

        final TextView close = RootView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close.setTextColor(getResources().getColor(R.color.white));
                close.setBackgroundColor(getResources().getColor(R.color.visited));

                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        return RootView;

    }

    @Override
    public void onStop() {
        super.onStop();
        circle.setStrokeColor(Color.BLACK);
        circle.setStrokeWidth(5);
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void setTrigPoint(TrigPoint trigPoint) { this.trigPoint = trigPoint; };

}
