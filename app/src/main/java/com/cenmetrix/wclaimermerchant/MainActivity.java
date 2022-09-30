package com.cenmetrix.wclaimermerchant;

import static com.cenmetrix.wclaimermerchant.global.GlobalMethods.FirebaseAuthRequest;
import static com.cenmetrix.wclaimermerchant.global.GlobalMethods.auth;
import static com.cenmetrix.wclaimermerchant.global.GlobalVariables.db;
import static com.cenmetrix.wclaimermerchant.global.GlobalVariables.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cenmetrix.wclaimermerchant.MainActivityFragments.AllCreatedProductsFragment;
import com.cenmetrix.wclaimermerchant.MainActivityFragments.AllDistributedWarrantyProductsFragment;
import com.cenmetrix.wclaimermerchant.MainActivityFragments.DashboardFragment;
import com.cenmetrix.wclaimermerchant.MainActivityFragments.WClaimerAccountFragment;
import com.cenmetrix.wclaimermerchant.global.LoadingProgressFragment;

public class MainActivity extends AppCompatActivity {
    ImageButton logout_btn;
    static ImageButton home_IB;
    static ImageButton product_list_IB;
    static ImageButton product_list_on_warranty_IB;
    static ImageButton w_claimer_news_IB;
    static FragmentManager fragmentManager;
    static TextView navbar_home_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        db.setFirestoreSettings(settings);
        // Initialize Firebase Auth
        FirebaseAuthRequest(MainActivity.this);

        initViews();
        initClicks();
        initClickEffects();



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    private static void getCurrentFragment() {
        Fragment f = fragmentManager.findFragmentById(R.id.navHostMainFragment);
        //  Log.e("TAG", "getCurrentFragment: "+f.getParentFragment().getTag() );
        Log.e("TAG", "5: " + fragmentManager.findFragmentByTag("DashboardFragment"));


//        product_list_IB
//                product_list_on_warranty_IB
//        w_claimer_news_IB

    }


    private void initViews() {
        logout_btn = findViewById(R.id.logout_btn);
        home_IB = findViewById(R.id.home_IB);
        product_list_IB = findViewById(R.id.product_list_IB);
        product_list_on_warranty_IB = findViewById(R.id.product_list_on_warranty_IB);
        w_claimer_news_IB = findViewById(R.id.w_claimer_news_IB);
        navbar_home_TV = findViewById(R.id.navbar_home_TV);
    }

    private void initClicks() {
        logout_btn.setOnClickListener(view -> {
            auth.signOut();
            FirebaseAuthRequest(MainActivity.this);
        });
        home_IB.setOnClickListener(view -> {
            NavigationMainFragments("home_IB");

        });
        product_list_IB.setOnClickListener(view -> {
            NavigationMainFragments("product_list_IB");

        });
        product_list_on_warranty_IB.setOnClickListener(view -> {
            NavigationMainFragments("product_list_on_warranty_IB");

        });
        w_claimer_news_IB.setOnClickListener(view -> {
            NavigationMainFragments("w_claimer_news_IB");

        });
    }

    private static void navIconRefresh() {
        home_IB.setImageResource(R.drawable.ic_home);
        product_list_IB.setImageResource(R.drawable.ic_product_list);
        product_list_on_warranty_IB.setImageResource(R.drawable.ic_product_list_on_warranty);
        w_claimer_news_IB.setImageResource(R.drawable.ic_logo_merchant);
    }

    public static void NavigationMainFragments(String switch_navigation_button) {
        switch (switch_navigation_button) {
            case "home_IB":
                navIconRefresh();
                home_IB.setImageResource(R.drawable.ic_home_pressed);
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostMainFragment, DashboardFragment.class, null, "DashboardFragment")
                        .setReorderingAllowed(true)
                        .addToBackStack("replacement")
                        .commit();
                navbar_home_TV.setText("Wclaimer Merchant");
                break;

            case "product_list_IB":
                navIconRefresh();
                product_list_IB.setImageResource(R.drawable.ic_product_list_pressed);
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostMainFragment, AllCreatedProductsFragment.class, null, "AllCreatedProductsFragment")
                        .setReorderingAllowed(true)
                        .addToBackStack("replacement")
                        .commit();
                navbar_home_TV.setText("Own products");
                break;

            case "product_list_on_warranty_IB":
                navIconRefresh();
                product_list_on_warranty_IB.setImageResource(R.drawable.ic_product_list_on_warranty_pressed);

                fragmentManager.beginTransaction()
                        .replace(R.id.navHostMainFragment, AllDistributedWarrantyProductsFragment.class, null, "AllDistributedWarrantyProductsFragment")
                        .setReorderingAllowed(true)
                        .addToBackStack("replacement")
                        .commit();
                navbar_home_TV.setText("Distributed product warranties");
                break;
            case "w_claimer_news_IB":
                navIconRefresh();
                w_claimer_news_IB.setImageResource(R.drawable.ic_logo_merchant_pressed);

                fragmentManager.beginTransaction()
                        .replace(R.id.navHostMainFragment, WClaimerAccountFragment.class, null, "WClaimerAccountFragment")
                        .setReorderingAllowed(true)
                        .addToBackStack("replacement")
                        .commit();
                navbar_home_TV.setText("News");
                break;

            default:
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostMainFragment, LoadingProgressFragment.class, null, "DashboardFragment")
                        .setReorderingAllowed(true)
                        .addToBackStack("replacement")
                        .commit();
                navbar_home_TV.setText("Loading");
                break;


        }
        getCurrentFragment();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initClickEffects() {
        logout_btn.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    logout_btn.setImageResource(R.drawable.logout_press_down);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    logout_btn.setImageResource(R.drawable.logout_press);
                    break;
                }
            }
            return false;
        });

    }


}