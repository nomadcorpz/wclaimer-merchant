package com.cenmetrix.wclaimermerchant.MainActivityFragments;

import static com.cenmetrix.wclaimermerchant.global.GlobalMethods.auth;
import static com.cenmetrix.wclaimermerchant.global.GlobalVariables.db;
import static com.cenmetrix.wclaimermerchant.global.GlobalVariables.userDetails;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cenmetrix.wclaimermerchant.Models.UserModel;
import com.cenmetrix.wclaimermerchant.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import es.dmoral.toasty.Toasty;


public class WClaimerAccountFragment extends Fragment {
    TextView user_f_name, user_l_name, user_email;
    ImageView iv_output;

    public WClaimerAccountFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WClaimerAccountFragment newInstance(String param1, String param2) {
        WClaimerAccountFragment fragment = new WClaimerAccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {

        iv_output = view.findViewById(R.id.iv_output);
        user_f_name = view.findViewById(R.id.user_f_name);
        user_l_name = view.findViewById(R.id.user_l_name);
        user_email = view.findViewById(R.id.user_email);
        getUserDetails();

        if (userDetails != null) {
            user_f_name.setText(userDetails.getUser_FirstName());
            user_l_name.setText(userDetails.getUser_LastName());
            user_email.setText(userDetails.getUser_Email());
        }

        GenerateQR();
    }

    private void getUserDetails() {
        DocumentReference docRef = db.collection("merchant_users").document(auth.getUid());
        if (userDetails == null) {
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    userDetails = documentSnapshot.toObject(UserModel.class);
                    if (userDetails != null) {
                        if (!userDetails.getUser_Role().trim().equals("Merchant")) {
                            auth.signOut();
                            Log.e("TAG", "onSuccess: " + userDetails.getUser_Role());
                        } else {

                            user_f_name.setText(userDetails.getUser_FirstName());
                            user_l_name.setText(userDetails.getUser_LastName());
                            user_email.setText(userDetails.getUser_Email());
//                        Log.e("TAG", "initViews: "+userDetails.getUser_FirstName() );
//                        Log.e("TAG", "initViews: "+userDetails.getUser_LastName() );
//                        Log.e("TAG", "initViews: "+userDetails.getUser_Email() );
                        }
                    } else {
                        Toasty.warning(getContext(), "Authentication Failed !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void GenerateQR() {
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(auth.getUid(), BarcodeFormat.QR_CODE, 300, 300);

            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            iv_output.setImageBitmap(bitmap);

//            InputMethodManager manager = (InputMethodManager) getSystemService(
//                    Context.INPUT_METHOD_SERVICE
//            );

        } catch (WriterException e) {
            e.printStackTrace();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wclaimer_account, container, false);
    }
}