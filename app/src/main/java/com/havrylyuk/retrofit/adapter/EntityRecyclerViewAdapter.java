package com.havrylyuk.retrofit.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.havrylyuk.retrofit.R;
import com.havrylyuk.retrofit.model.Entity;

import java.util.List;

/**
 * Created by Igor Havrylyuk on 08.03.2017.
 */

public class EntityRecyclerViewAdapter extends RecyclerView.Adapter<EntityRecyclerViewAdapter.ItemHolder> {

    public interface ItemClickListener {
        void onItemClick(Entity country);
    }

    private ItemClickListener listener;
    private List<Entity> countryList;

    public EntityRecyclerViewAdapter(ItemClickListener listener, List<Entity> countryList) {
        this.listener = listener;
        this.countryList = countryList;
        notifyDataSetChanged();
    }

    public void setCountryList(List<Entity> countryList) {
        this.countryList = countryList;
        notifyDataSetChanged();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(countryList.get(holder.getAdapterPosition()));
                }
            }
        });
        String country = countryList.get(position).getCountryName();
        holder.country.setText(country);
        String capital = countryList.get(position).getCapital();
        holder.capital.setText(capital);
        String flagUrl = "http://www.geonames.org/flags/m/"+
                countryList.get(position).getCountryCode().toLowerCase()+".png";
        holder.flag.setImageURI(Uri.parse(flagUrl));
    }

    @Override
    public int getItemCount() {
        return countryList != null ? countryList.size() : 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        private  View view;
        private  SimpleDraweeView flag;
        private  TextView country;
        private  TextView capital;

        public ItemHolder(View view) {
            super(view);
            this.view = view;
            this.flag = (SimpleDraweeView) view.findViewById(R.id.list_item_icon);
            this.country = (TextView) view.findViewById(R.id.list_item_name);
            this.capital = (TextView) view.findViewById(R.id.list_item_sub_name);
        }
    }
}
