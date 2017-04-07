package com.example.jordan.weatherapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class FragmentOne extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_one, container,
                false);

        Button clickButton = (Button) rootView.findViewById(R.id.FirstBtn);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Main2Activity.class);
                startActivity(intent);

            }
        });

        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        GetStories rss = new GetStories(getActivity(), lv);
        rss.execute("http://www.meteokav.gr/weather/plaintext-rss.php");

        return rootView;
    }

}
