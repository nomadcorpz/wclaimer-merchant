package com.cenmetrix.wclaimermerchant.MainActivityFragments;

import static com.cenmetrix.wclaimermerchant.global.GlobalMethods.FirebaseAuthRequest;
import static com.cenmetrix.wclaimermerchant.global.GlobalMethods.auth;
import static com.cenmetrix.wclaimermerchant.global.GlobalVariables.db;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cenmetrix.wclaimermerchant.ComponentItemAdapters.WarrantyListAdapter;
import com.cenmetrix.wclaimermerchant.FragmentScreensComponents.Capture;
import com.cenmetrix.wclaimermerchant.Models.WarrantyDetailModel;
import com.cenmetrix.wclaimermerchant.R;
import com.cenmetrix.wclaimermerchant.global.GlobalFragment;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


public class AllCreatedProductsFragment extends GlobalFragment {

    private static final String TAG = "AllCreatedProductsFragment";
    ImageButton add_product_btn, scan_btn1;
    RecyclerView created_warranties_RV;
      WarrantyListAdapter adapter;
    EditText product_serial_no_ET;
    static TextView no_of_products_TV;

    public AllCreatedProductsFragment() {
        // Required empty public constructor

    }

    // TODO: Rename and change types and number of parameters
    public static AllCreatedProductsFragment newInstance(String param1, String param2) {
        AllCreatedProductsFragment fragment = new AllCreatedProductsFragment();
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
        return inflater.inflate(R.layout.fragment_all_created_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initClicks();
        getWarrantyDataFromFireStore("");
    }

    private void initClicks() {
        product_serial_no_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getWarrantyDataFromFireStore(product_serial_no_ET.getText().toString());
                adapter.startListening();
            }
        });

        scan_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanOptions scanOptions = new ScanOptions();
                scanOptions.setPrompt("For flash use volume up key");
                scanOptions.setBeepEnabled(true);
                scanOptions.setOrientationLocked(true);
                scanOptions.setCaptureActivity(Capture.class);
                barcodeLauncher.launch(scanOptions);
            }
        });

    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) {
            Toast.makeText(getActivity(), "No Barcode: " + result.getContents(), Toast.LENGTH_LONG).show();
        } else {
            product_serial_no_ET.setText(result.getContents());
        }
    });

    private void initViews(View view) {
        add_product_btn = view.findViewById(R.id.add_product_btn);
        created_warranties_RV = view.findViewById(R.id.created_warranties_RV);
        product_serial_no_ET = view.findViewById(R.id.product_serial_no_ET);
        scan_btn1 = view.findViewById(R.id.scan_btn1);
        no_of_products_TV = view.findViewById(R.id.no_of_products_TV);

    }

    public static void getTotalOwnProducts() {
        Query query = db.collection("product_warranties")
                .whereEqualTo("prod_Merchant", auth.getUid())
                .whereEqualTo("prod_WarrantyIsStarted", false);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.e("TAG", "getTotalOwnProducts: " + task.getResult().size());
                no_of_products_TV.setText(task.getResult().size() + "");
            } else {
                //  no_of_products_TV.setText("0");
            }
        });
    }



    private void getWarrantyDataFromFireStore(String like) {
        Query query = db.collection("product_warranties")
                .whereEqualTo("prod_Merchant", auth.getUid())
                .whereGreaterThanOrEqualTo("prod_SerialNo", like)
                .whereEqualTo("prod_WarrantyIsStarted", false);

        Log.e("TAG", "getWarrantyDataFromFireStore: " + like);

        FirestoreRecyclerOptions<WarrantyDetailModel> options = new FirestoreRecyclerOptions.Builder<WarrantyDetailModel>()
                .setQuery(query, WarrantyDetailModel.class).build();

        adapter = new WarrantyListAdapter(options ,  TAG);
        created_warranties_RV.setLayoutManager(new LinearLayoutManager(getContext()));
        created_warranties_RV.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening() ;
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening() ;
    }

}