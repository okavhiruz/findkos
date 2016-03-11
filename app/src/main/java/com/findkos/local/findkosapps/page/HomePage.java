package com.findkos.local.findkosapps.page;

/**
 * Created by Sulalah Rugaya on 3/4/2016.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findkos.local.findkosapps.R;


public class HomePage extends Fragment {

    public HomePage(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        return rootView;
    }
}