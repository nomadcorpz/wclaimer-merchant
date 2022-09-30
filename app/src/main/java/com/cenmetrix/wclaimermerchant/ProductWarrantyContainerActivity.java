package com.cenmetrix.wclaimermerchant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cenmetrix.wclaimermerchant.FragmentScreensComponents.ProductWarrantyFormFragment;
import com.cenmetrix.wclaimermerchant.FragmentScreensComponents.ProductUpdateFragment;
import com.cenmetrix.wclaimermerchant.global.LoadingProgressFragment;
import com.cenmetrix.wclaimermerchant.Models.WarrantyDetailModel;

public class ProductWarrantyContainerActivity extends AppCompatActivity {
    private static final String TAG = "ProductWarrantyContainerActivity";
    FragmentManager fragmentManager;
    TextView navbar_TV;
    Bundle bundle;
    ImageButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add_container);
        fragmentManager = getSupportFragmentManager();
        navbar_TV = findViewById(R.id.navbar_TV);
        back_btn = findViewById(R.id.back_btn);
        Intent i = getIntent();
        bundle = new Bundle();

        WarrantyDetailModel wm = (WarrantyDetailModel) i.getSerializableExtra("warranty_details");
        String screen_name = i.getStringExtra("screen_name");
        //WarrantyDetailModel wm = (WarrantyDetailModel) bundle.getSerializable("warranty_details");
        if (wm != null) {
            bundle.putSerializable("Warranty_Product", wm);
        }


        SetFragmentScreen(wm, screen_name);
        initClicks();
    }

    private void initClicks() {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backPress();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPress();
    }

    private void backPress() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @SuppressLint("SetTextI18n")
    private void SetFragmentScreen(WarrantyDetailModel wm, String screen_name) {

        switch (screen_name) {
            case "ProductWarrantyFormFragment":
                fragmentManager.beginTransaction()
                        .replace(R.id.productWarrantyContainer, ProductWarrantyFormFragment.class, null, "ProductWarrantyFormFragment")
                        .setReorderingAllowed(true)
                        .addToBackStack("replacement")
                        .commit();
                navbar_TV.setText("Create a New Warranty");
                break;
            case "ProductWarrantyFormFragment_update":
                fragmentManager.beginTransaction()
                        .replace(R.id.productWarrantyContainer, ProductWarrantyFormFragment.class, bundle, "ProductWarrantyFormFragment_update")
                        .setReorderingAllowed(true)
                        .addToBackStack("replacement")
                        .commit();
                navbar_TV.setText("Product Update");
                break;
            default:
                fragmentManager.beginTransaction()
                        .replace(R.id.productWarrantyContainer, LoadingProgressFragment.class, null, "LoadingProgressFragment")
                        .setReorderingAllowed(true)
                        .addToBackStack("replacement")
                        .commit();
                break;
        }


    }

}