package com.uk.trigtracker.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uk.trigtracker.R;
import com.uk.trigtracker.RecyclerViewAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class VisitedMenuFragment extends Fragment {


    public VisitedMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.visited_menu_fragment, container, false);

        ArrayList<String> titles = new ArrayList<>();
        titles.add("test");
        titles.add("test1");
        titles.add("test2");
        titles.add("test3");
        titles.add("test4");

        RecyclerView recyclerView = rootView.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.getContext(), titles);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
