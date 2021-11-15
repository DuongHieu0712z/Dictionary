package com.dictionary;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SplastFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViews();
        return inflater.inflate(R.layout.activity_splast, container, false);
    }
    private void initViews() {
        new Handler().postDelayed(this::gotoM001Screen, 2000);
    }
    private void gotoM001Screen() {
        ((MainActivity) getActivity()).gotoM001Screen();
    }
}
