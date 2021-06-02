package com.enggvam.countryselectorapp.bottomSheet;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.enggvam.countryselectorapp.activity.viewModel.PreferenceSharedViewModel;
import com.enggvam.countryselectorapp.adapter.RegionListAdapter;
import com.enggvam.countryselectorapp.bottomSheet.viewModel.RegionBottomSheetViewModel;
import com.enggvam.countryselectorapp.databinding.SheetSelectRegionBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

/**
 * <h1>{@link BottomSheetDialogFragment} to show the available regions for selection</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class RegionBottomSheet extends BottomSheetDialogFragment {

    private SheetSelectRegionBinding rootBinding;
    private RegionBottomSheetViewModel viewModel;
    private PreferenceSharedViewModel preferenceViewModel;
    private RegionListAdapter regionListAdapter;
    protected LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootBinding = SheetSelectRegionBinding.inflate(inflater, container, false);
        initConfigs();
        initViews();
        initListeners();
        initViewModelObservers();
        return rootBinding.getRoot();
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //this is needed to make the bottom sheet open as expanded by default
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(dialog -> {
            BottomSheetDialog sheetDialog = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = sheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

            if (bottomSheet != null) {
                BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

        });
        return bottomSheetDialog;
    }

    private void initConfigs() {
        viewModel = new ViewModelProvider(this).get(RegionBottomSheetViewModel.class);
        preferenceViewModel = new ViewModelProvider(requireActivity()).get(PreferenceSharedViewModel.class);

        regionListAdapter = new RegionListAdapter(preferenceViewModel.getLastRegionPosition(),
                (selectedRegion, currentPos, lastPos) -> {
                    regionListAdapter.notifyItemChanged(currentPos); //update(set checked) the newly selected region
                    regionListAdapter.notifyItemChanged(lastPos); //update(set unchecked) the previously selected region
                    //update the preferences accordingly
                    preferenceViewModel.setSelectedRegionPosition(currentPos);
                    preferenceViewModel.setSelectedRegionName(selectedRegion.getName());
                    preferenceViewModel.setSelectedRegionCode(selectedRegion.getCode());

                    //to delay the auto dismissal of bottom sheet on region selection, so the user the see the new selection
                    new Handler().postDelayed(this::dismiss, 300);
                });
    }

    private void initViews() {
        setupRegionList();
    }

    private void setupRegionList() {
        layoutManager = new LinearLayoutManager(requireContext());
        rootBinding.rvRegions.setLayoutManager(layoutManager);
        rootBinding.rvRegions.setItemAnimator(new DefaultItemAnimator());
        rootBinding.rvRegions.setAdapter(regionListAdapter);
    }

    private void initListeners() {
        rootBinding.btReload.setOnClickListener(v -> viewModel.reloadRegions());
    }

    private void initViewModelObservers() {
        viewModel.getRegionList().observe(getViewLifecycleOwner(), regions -> {
            regionListAdapter.setData(regions);
            checkForNoItems();
            scrollSelectedItemToCenter();
        });
    }

    private void checkForNoItems() {
        if (regionListAdapter.getItemCount() > 0) {
            rootBinding.tvNoItem.setVisibility(View.GONE);
            rootBinding.btReload.setVisibility(View.GONE);
        } else {
            rootBinding.tvNoItem.setVisibility(View.VISIBLE);
            rootBinding.btReload.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method to scroll to the previously selected item and centering it
     */
    private void scrollSelectedItemToCenter() {
        if (regionListAdapter.getItemCount() >= preferenceViewModel.getLastRegionPosition()) {
            rootBinding.rvRegions.scrollToPosition(preferenceViewModel.getLastRegionPosition());

            rootBinding.rvRegions.post(() -> {
                View view = layoutManager.findViewByPosition(preferenceViewModel.getLastRegionPosition());

                if (view != null) {
                    int itemToScroll = rootBinding.rvRegions.getChildAdapterPosition(view);
                    int centerOfScreen = rootBinding.rvRegions.getHeight() / 2 - view.getHeight() / 2;
                    layoutManager.scrollToPositionWithOffset(itemToScroll, centerOfScreen);
                }
            });
        }
    }
}
