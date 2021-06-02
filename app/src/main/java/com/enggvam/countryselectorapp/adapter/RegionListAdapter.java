package com.enggvam.countryselectorapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enggvam.countryselectorapp.databinding.LayoutItemRegionBinding;
import com.enggvam.countryselectorapp.helper.UrlHelper;
import com.enggvam.countryselectorapp.data.db.entity.Region;

import java.util.List;

/**
 * <h1>{@link RecyclerView.Adapter} class for the region recycle view</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class RegionListAdapter extends RecyclerView.Adapter<RegionListAdapter.ViewHolder> {

    public interface RegionListListener {
        /**
         * Method invoked when a different region gets selected from the list.
         *
         * @param selectedRegion the newly selected region
         * @param currentPos     position of the newly selected region in the {@link #regionList}
         * @param lastPos        position of the original selected region(when the list was first
         *                       displayed) in the {@link #regionList}
         */
        void onRegionSelected(@NonNull final Region selectedRegion, final int currentPos, final int lastPos);
    }

    private List<Region> regionList;
    private final RegionListListener listener;
    private int currentSelectedPosition;

    public RegionListAdapter(int currentSelectedPosition, @NonNull final RegionListListener listener) {
        this.listener = listener;
        this.currentSelectedPosition = currentSelectedPosition;
    }

    public void setData(List<Region> newRegionList) {
        if ((newRegionList == null || newRegionList.isEmpty()) && regionList != null)
            this.regionList.clear();
        else this.regionList = newRegionList;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ViewHolder holder = new ViewHolder(LayoutItemRegionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));

        holder.rootBinding.rbRegion.setOnClickListener(view -> {
            int lastSelectedPosition = currentSelectedPosition;
            currentSelectedPosition = holder.getAdapterPosition();

            //proceed only if the current selection is different than the original selection
            if (listener != null && currentSelectedPosition != lastSelectedPosition) {
                listener.onRegionSelected(regionList.get(holder.getAdapterPosition()),
                        currentSelectedPosition, lastSelectedPosition);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rootBinding.rbRegion.setText(regionList.get(position).getName());
        holder.rootBinding.rbRegion.setChecked(position == currentSelectedPosition);

        Glide.with(holder.itemView)
                .asBitmap()
                .load(UrlHelper.getFlagUrl(regionList.get(position).getCode()))
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.rootBinding.ivFlag);
    }

    @Override
    public int getItemCount() {
        return regionList != null ? regionList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final LayoutItemRegionBinding rootBinding;

        public ViewHolder(@NonNull LayoutItemRegionBinding rootBinding) {
            super(rootBinding.getRoot());
            this.rootBinding = rootBinding;
        }
    }
}
