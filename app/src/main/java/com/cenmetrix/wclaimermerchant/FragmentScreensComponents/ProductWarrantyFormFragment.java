package com.cenmetrix.wclaimermerchant.FragmentScreensComponents;

import static com.cenmetrix.wclaimermerchant.global.GlobalMethods.FirebaseAuthRequest;
import static com.cenmetrix.wclaimermerchant.global.GlobalMethods.auth;
import static com.cenmetrix.wclaimermerchant.global.GlobalMethods.getCurrentDate;
import static com.cenmetrix.wclaimermerchant.global.GlobalVariables.db;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cenmetrix.wclaimermerchant.R;
import com.cenmetrix.wclaimermerchant.Models.WarrantyDetailModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.w3c.dom.DocumentType;

import es.dmoral.toasty.Toasty;


public class ProductWarrantyFormFragment extends Fragment {
    private static final String TAG = "ProductWarrantyFormFragment";
    EditText CW_product_serial_no_ET, CW_product_model_no_ET, CW_product_model_name_ET, CW_no_of_months_ET, CW_warrantyConditionET, CW_prod_Remark_ET;
    ImageButton CW_create_warranty_IB, CW_delete_warranty_IB, scan_btn1, scan_btn2, scan_btn3;
    LinearLayout CW_product_serial_no_LL, CW_product_model_no_LL, CW_product_model_name_LL, CW_no_of_months_LL, CW_delete_warranty_LL, product_update_LL;
    ProgressDialog dialog;
    TextView CW_date_added_TV;
    Bundle bundle;

    public ProductWarrantyFormFragment() {
        // Required empty public constructor
    }

    public static ProductWarrantyFormFragment newInstance(String param1, String param2) {
        ProductWarrantyFormFragment fragment = new ProductWarrantyFormFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuthRequest(getActivity());
    }

    WarrantyDetailModel wm;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = this.getArguments();
        if (bundle != null) {
            wm = (WarrantyDetailModel) bundle.getSerializable("Warranty_Product");
            Log.e(TAG, "onViewCreated: " + wm.getProd_Merchant());
        }

        initViews(view);
        initClicks();
        initClickEffects();
    }

    private void initViews(View view) {
        CW_product_serial_no_ET = view.findViewById(R.id.CW_product_serial_no_ET);
        CW_product_model_no_ET = view.findViewById(R.id.CW_product_model_no_ET);
        CW_product_model_name_ET = view.findViewById(R.id.CW_product_model_name_ET);
        CW_no_of_months_ET = view.findViewById(R.id.CW_no_of_months_ET);
        CW_warrantyConditionET = view.findViewById(R.id.CW_warrantyConditionET);
        CW_prod_Remark_ET = view.findViewById(R.id.CW_prod_Remark_ET);

        CW_date_added_TV = view.findViewById(R.id.CW_date_added_TV);

        CW_create_warranty_IB = view.findViewById(R.id.CW_create_warranty_IB);
        CW_delete_warranty_IB = view.findViewById(R.id.CW_delete_warranty_IB);
        CW_delete_warranty_LL = view.findViewById(R.id.CW_delete_warranty_LL);
        product_update_LL = view.findViewById(R.id.product_update_LL);


        scan_btn1 = view.findViewById(R.id.scan_btn1);
        scan_btn2 = view.findViewById(R.id.scan_btn2);
        scan_btn3 = view.findViewById(R.id.scan_btn3);

        CW_product_serial_no_LL = view.findViewById(R.id.CW_product_serial_no_LL);
        CW_product_model_no_LL = view.findViewById(R.id.CW_product_model_no_LL);
        CW_product_model_name_LL = view.findViewById(R.id.CW_product_model_name_LL);
        CW_no_of_months_LL = view.findViewById(R.id.CW_no_of_months_LL);

        if (bundle != null) {
            CW_date_added_TV.setText(wm.getProd_WarrantyAddedDate().toString());
            CW_product_serial_no_ET.setText(wm.getProd_SerialNo());
            CW_product_model_no_ET.setText(wm.getProd_ModelNo());
            CW_product_model_name_ET.setText(wm.getProd_ModelName());
            CW_no_of_months_ET.setText(wm.getProd_WarrantyPeriod() + "");
            CW_warrantyConditionET.setText(wm.getProd_WarrantyConditions());
            CW_prod_Remark_ET.setText(wm.getProd_Remark());

            CW_product_serial_no_ET.setTextColor(requireContext().getColor(R.color.colorFontGrey));
            CW_product_model_no_ET.setTextColor(requireContext().getColor(R.color.colorFontGrey));
            CW_product_model_name_ET.setTextColor(requireContext().getColor(R.color.colorFontGrey));
            CW_no_of_months_ET.setTextColor(requireContext().getColor(R.color.colorFontGrey));


            CW_product_serial_no_LL.setBackgroundResource(R.drawable.bg_edit_text_disable);
            CW_product_model_no_LL.setBackgroundResource(R.drawable.bg_edit_text_disable);
            CW_product_model_name_LL.setBackgroundResource(R.drawable.bg_edit_text_disable);
            CW_no_of_months_LL.setBackgroundResource(R.drawable.bg_edit_text_disable);


            CW_product_serial_no_ET.setEnabled(false);
            CW_product_model_no_ET.setEnabled(false);
            CW_product_model_name_ET.setEnabled(false);
            CW_no_of_months_ET.setEnabled(false);

            CW_create_warranty_IB.setBackgroundResource(R.drawable.update_product_up_btn);
        } else {
            product_update_LL.setVisibility(View.GONE);
            CW_delete_warranty_LL.setVisibility(View.GONE);
        }

    }

    private void initClicks() {
        CW_create_warranty_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bundle == null) {
                    productCreate("add");
                } else {
                    productCreate("update");
                }
            }
        });

        scan_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanQRorBarCode("scan_btn1");
            }
        });
        scan_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanQRorBarCode("scan_btn2");
            }
        });
    }

    String scan_button;

    private void ScanQRorBarCode(String button_name) {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("For flash use volume up key");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(Capture.class);

        barcodeLauncher.launch(scanOptions);
        scan_button = button_name;

    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) {
            Toast.makeText(getActivity(), "No Barcode: " + result.getContents(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Barcode: " + result.getContents(), Toast.LENGTH_LONG).show();
            switch (scan_button) {
                case "scan_btn1":
                    CW_product_serial_no_ET.setText(result.getContents());
                    break;
                case "scan_btn2":
                    CW_product_model_no_ET.setText(result.getContents());
                    break;

                default:
                    Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    break;
            }

        }
    });


    private void productCreate(String add_or_update) {

        String product_serial_no = CW_product_serial_no_ET.getText().toString();
        String product_model_no = CW_product_model_no_ET.getText().toString();
        String product_model_name = CW_product_model_name_ET.getText().toString();
        int no_of_months = Integer.parseInt(CW_no_of_months_ET.getText().toString());
        String prod_Remark = CW_prod_Remark_ET.getText().toString();
        String prod_WarrantyConditions = CW_warrantyConditionET.getText().toString();


        Log.e(TAG, "initClicks: " + auth.getUid());

        WarrantyDetailModel wdm = new WarrantyDetailModel();
        wdm.setProd_SerialNo(product_serial_no);
        wdm.setProd_ModelNo(product_model_no);
        wdm.setProd_ModelName(product_model_name);
        wdm.setProd_WarrantyPeriod(no_of_months);
        wdm.setProd_WarrantyAddedDate(getCurrentDate());
        wdm.setProd_Remark(prod_Remark);
        wdm.setProd_WarrantyConditions(prod_WarrantyConditions);
        wdm.setProd_Merchant(auth.getUid());
        wdm.setProd_Owner(auth.getUid());
        dialog = ProgressDialog.show(getActivity(), "Processing...", "Please wait....", true);
        Query query = db.collection("product_warranties").whereEqualTo("prod_SerialNo", wdm.getProd_SerialNo());
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                switch (add_or_update) {
                    case "add":
                        if (value.isEmpty()) {
                            Log.e(TAG, "onEvent: " + value.getDocuments().size());
                            AddOrReplaceDocument(wdm);
                        } else {
                            //   Toasty.warning(getActivity(), "Already added in database", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        break;
                    case "update":
                        AddOrReplaceDocument(wdm);
                        break;
                    default:
                        Toasty.warning(getActivity(), "please check again", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;

                }

            }
        });


    }

    private void AddOrReplaceDocument(WarrantyDetailModel wdm) {
        db.collection("product_warranties").document(wdm.getProd_SerialNo()).set(wdm).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toasty.success(getActivity(), "Success !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getActivity().finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.warning(getActivity(), "Registration Fail !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initClickEffects() {
        if (bundle != null) {
            CW_create_warranty_IB.setOnTouchListener((view, motionEvent) -> {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        CW_create_warranty_IB.setImageResource(R.drawable.update_product_down_btn);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        CW_create_warranty_IB.setImageResource(R.drawable.update_product_up_btn);
                        break;
                    }
                }
                return false;
            });
        } else {
            CW_create_warranty_IB.setOnTouchListener((view, motionEvent) -> {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        CW_create_warranty_IB.setImageResource(R.drawable.create_warranty_down_btn);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        CW_create_warranty_IB.setImageResource(R.drawable.create_warranty_up_btn);
                        break;
                    }
                }
                return false;
            });
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_product_warranty_form, container, false);
    }

}