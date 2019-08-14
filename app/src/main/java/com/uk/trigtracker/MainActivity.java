package com.uk.trigtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        readData();
    }

    private void readData() {
        InputStream is = getResources().openRawResource(R.raw.peaks_trigs);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                is,
                Charset.forName("UTF-8")));

        ArrayList<String[]> lines = new ArrayList<>();
        String line;
        try {

            // Step over headers
            reader.readLine();

            while((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                lines.add(tokens);
            }
            System.out.println(lines);
        } catch (IOException e) {
            Log.wtf("MainActivity", "Error reading file at line 31: " + e);
            e.printStackTrace();
        }
    }
}
