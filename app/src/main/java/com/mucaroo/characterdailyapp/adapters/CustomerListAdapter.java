package com.mucaroo.characterdailyapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.listeners.ItemTouchHelperAdapter;
import com.mucaroo.characterdailyapp.listeners.ItemTouchHelperViewHolder;
import com.mucaroo.characterdailyapp.listeners.OnCustomerListChangedListener;
import com.mucaroo.characterdailyapp.listeners.OnStartDragListener;
import com.mucaroo.characterdailyapp.models.Pillars;


//import com.okason.draganddrop.listeners.OnCustomerListChangedListener;
//import com.okason.draganddrop.listeners.OnStartDragListener;
//import com.okason.draganddrop.utilities.ItemTouchHelperAdapter;
//import com.okason.draganddrop.utilities.ItemTouchHelperViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Valentine on 10/18/2015.
 */
public class CustomerListAdapter extends
        RecyclerView.Adapter<CustomerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private List<Pillars> mCustomers;
    private Context mContext;
    private OnStartDragListener mDragStartListener;
    private OnCustomerListChangedListener mListChangedListener;

    public CustomerListAdapter(List<Pillars> customers, Context context,
                               OnStartDragListener dragLlistener,
                               OnCustomerListChangedListener listChangedListener) {
        mCustomers = customers;
        mContext = context;
        mDragStartListener = dragLlistener;
        mListChangedListener = listChangedListener;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_customer_list, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        final Pillars selectedCustomer = mCustomers.get(position);

        holder.customerName.setText(selectedCustomer.getValue());
        holder.customerEmail.setText(selectedCustomer.getPillarName());
//        Picasso.with(mContext)
//                .load(selectedCustomer.getImagePath())
//                .placeholder(R.drawable.profile_icon)
//                .into(holder.profileImage);


        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return mCustomers.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mCustomers, fromPosition, toPosition);
        mListChangedListener.onNoteListChanged(mCustomers);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView customerName, customerEmail;
        public final ImageView handleView;


        public ItemViewHolder(View itemView) {
            super(itemView);
            customerName = (TextView) itemView.findViewById(R.id.text_view_customer_name);
            customerEmail = (TextView) itemView.findViewById(R.id.text_view_customer_email);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
//            profileImage = (ImageView)itemView.findViewById(R.id.image_view_customer_head_shot);


        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
