package com.findkos.local.findkosapps.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findkos.local.findkosapps.R;

/**
 * Created by Sulalah Rugaya on 3/4/2016.
 */
public class MenuFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.menu_page, null);
        return view;
    }

}