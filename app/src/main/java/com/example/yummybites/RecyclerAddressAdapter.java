package com.example.yummybites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummybites.database.DatabaseHelper;
import com.example.yummybites.databinding.AddressRecyclerViewBinding;
import com.example.yummybites.utils.AppUtil;

import java.util.List;

public class RecyclerAddressAdapter extends RecyclerView.Adapter<RecyclerAddressAdapter.yViewHolder> {
    Context context;
    AddressRecyclerViewBinding binding;
    RecyclerAddressAdapter addressAdapter;
    List<String> addressList;
    LayoutInflater layoutInflater;
    DatabaseHelper databaseHelper;

    public RecyclerAddressAdapter(Context context, List<String> address) {
        this.context = context;
        this.addressList = address;
    }

    @NonNull
    @Override
    public RecyclerAddressAdapter.yViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = AddressRecyclerViewBinding.inflate(layoutInflater, parent, false);
        databaseHelper = new DatabaseHelper(context);
        return new RecyclerAddressAdapter.yViewHolder(binding.getRoot());

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAddressAdapter.yViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        binding.addressText.setText(addressList.get(position));
        addressAdapter = new RecyclerAddressAdapter(context, addressList);
        holder.itemView.findViewById(R.id.remove_address).setOnClickListener(view -> {

            AppUtil.getInstance().showToast(context, "Address  removed at position " + position);
            databaseHelper.deleteAddress(addressList.remove((position)));
            notifyItemRemoved(position);

        });
        holder.itemView.findViewById(R.id.edit_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final Dialog dialog = new Dialog(context);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.bottom_address_fragment);
//                dialog.show();
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.getWindow().getAttributes().windowAnimations = R.style.DialAnimation;
//                dialog.getWindow().setGravity(Gravity.BOTTOM);
//                bottomAddressFragmentBinding.addressLocation.setText(addressList.get(position));
                AppUtil.getInstance().showToast(context, "Editing done");
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class yViewHolder extends RecyclerView.ViewHolder {

        public yViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
