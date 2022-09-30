package com.cenmetrix.wclaimermerchant.FragmentScreensComponents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cenmetrix.wclaimermerchant.R;

public class ProductUpdateFragment extends Fragment {
    public ProductUpdateFragment() {
        // Required empty public constructor
    }

    public static ProductUpdateFragment newInstance(String param1, String param2) {
        ProductUpdateFragment fragment = new ProductUpdateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_update, container, false);
    }
}