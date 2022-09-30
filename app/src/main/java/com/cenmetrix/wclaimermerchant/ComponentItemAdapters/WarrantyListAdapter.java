package com.cenmetrix.wclaimermerchant.ComponentItemAdapters;

import static com.cenmetrix.wclaimermerchant.MainActivityFragments.AllCreatedProductsFragment.getTotalOwnProducts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cenmetrix.wclaimermerchant.MainActivityFragments.DashboardFragment;
import com.cenmetrix.wclaimermerchant.ProductWarrantyContainerActivity;
import com.cenmetrix.wclaimermerchant.R;
import com.cenmetrix.wclaimermerchant.Models.WarrantyDetailModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class WarrantyListAdapter extends FirestoreRecyclerAdapter<WarrantyDetailModel, WarrantyListAdapter.ViewHolder> {
    String screen_tag;
    TextView product_availability;
    RecyclerView dash_created_warranties_rv;

    public WarrantyListAdapter(@NonNull FirestoreRecyclerOptions<WarrantyDetailModel> options) {
        super(options);
    }

    public WarrantyListAdapter(FirestoreRecyclerOptions<WarrantyDetailModel> options, String TAG) {
        super(options);
        this.screen_tag = TAG;
        getTotalOwnProducts();
        Constructor = 1;
    }

    public WarrantyListAdapter(FirestoreRecyclerOptions<WarrantyDetailModel> options, TextView no_any_own_products_tv, RecyclerView dash_created_warranties_rv) {
        super(options);
        this.product_availability = no_any_own_products_tv;
        product_availability.setVisibility(View.VISIBLE);
        this.dash_created_warranties_rv = dash_created_warranties_rv;
        Constructor = 2;
    }

    int Constructor = 0;


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull WarrantyDetailModel model) {
        if (Constructor == 2) {
            product_availability.setVisibility(View.GONE);
            dash_created_warranties_rv.setVisibility(View.VISIBLE);
            switch (position) {
                case 0:
                    dash_created_warranties_rv.setMinimumHeight(80);
                    break;
                case 1:
                    dash_created_warranties_rv.setMinimumHeight(160);
                    break;
                case 2:
                    dash_created_warranties_rv.setMinimumHeight(240);
                    break;
            }
        }

        Log.e("TAG", "onBindViewHolder: " + position);
        holder.SerialNoTV.setText(model.getProd_SerialNo());
        holder.ModelNameTV.setText(model.getProd_ModelName());
        holder.ModelNoTV.setText(model.getProd_ModelNo());
        holder.WarrantyPeriodTV.setText(model.getProd_WarrantyPeriod() + "");

        holder.item_layout_inspection_type_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ProductWarrantyContainerActivity.class);
                Bundle bundle = new Bundle();
                i.putExtra("warranty_details", model);
                i.putExtra("screen_name", "ProductWarrantyFormFragment_update");
                view.getContext().startActivity(i);
            }
        });
        if (screen_tag != null) {
            getTotalOwnProducts();
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView SerialNoTV, ModelNameTV, ModelNoTV, WarrantyPeriodTV;
        LinearLayout item_layout_inspection_type_list;

        public ViewHolder(View view) {
            super(view);
            SerialNoTV = view.findViewById(R.id.SerialNoTV);
            ModelNameTV = view.findViewById(R.id.ModelNameTV);
            ModelNoTV = view.findViewById(R.id.ModelNoTV);
            WarrantyPeriodTV = view.findViewById(R.id.WarrantyPeriodTV);

            item_layout_inspection_type_list = view.findViewById(R.id.item_layout_inspection_type_list);

        }
    }
}

