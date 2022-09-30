package com.cenmetrix.wclaimermerchant.MainActivityFragments;

import static com.cenmetrix.wclaimermerchant.global.GlobalMethods.FirebaseAuthRequest;
import static com.cenmetrix.wclaimermerchant.global.GlobalMethods.auth;
import static com.cenmetrix.wclaimermerchant.global.GlobalVariables.db;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cenmetrix.wclaimermerchant.ComponentItemAdapters.WarrantyListAdapter;
import com.cenmetrix.wclaimermerchant.MainActivity;
import com.cenmetrix.wclaimermerchant.Models.WarrantyDetailModel;
import com.cenmetrix.wclaimermerchant.ProductWarrantyContainerActivity;
import com.cenmetrix.wclaimermerchant.R;
import com.cenmetrix.wclaimermerchant.global.GlobalFragment;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class DashboardFragment extends GlobalFragment {

    private static final String TAG = "DashboardFragment";
    RecyclerView DASH_created_warranties_RV;
    ImageButton add_product_btn;
    TextView see_all_btn_tv, no_any_own_products_tv;

    public DashboardFragment() {
        // Required empty public constructor
        FirebaseAuthRequest(getActivity());
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initClicks();
        initClickEffects();
        getWarrantyDataFromFireStore();
    }

    WarrantyListAdapter adapter;

    private void getWarrantyDataFromFireStore() {
        Query query = db.collection("product_warranties")
                .whereEqualTo("prod_Merchant", auth.getUid())
                .whereEqualTo("prod_WarrantyIsStarted", false).orderBy("prod_WarrantyAddedDate",Query.Direction.DESCENDING).limit(3);
        FirestoreRecyclerOptions<WarrantyDetailModel> options = new FirestoreRecyclerOptions.Builder<WarrantyDetailModel>()
                .setQuery(query, WarrantyDetailModel.class).build();

        Log.e("TAG", "getWarrantyDataFromFireStore: ");
        adapter = new WarrantyListAdapter(options, no_any_own_products_tv, DASH_created_warranties_RV);
        DASH_created_warranties_RV.setLayoutManager(new LinearLayoutManager(getContext()));
        DASH_created_warranties_RV.setAdapter(adapter);
    }


    private void initViews(View view) {
        add_product_btn = view.findViewById(R.id.add_product_btn);
        DASH_created_warranties_RV = view.findViewById(R.id.DASH_created_warranties_RV);
        see_all_btn_tv = view.findViewById(R.id.see_all_btn_tv);
        no_any_own_products_tv = view.findViewById(R.id.no_any_own_products_tv);
    }

    private void initClicks() {
        add_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ProductWarrantyContainerActivity.class);
                i.putExtra("screen_name", "ProductWarrantyFormFragment");
                startActivity(i);
            }
        });

        see_all_btn_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.NavigationMainFragments("product_list_IB");
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initClickEffects() {

        add_product_btn.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    add_product_btn.setImageResource(R.drawable.add_product_press_down);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    add_product_btn.setImageResource(R.drawable.add_product_press_up);
                    break;
                }
            }
            return false;
        });
    }
}