package com.enggvam.countryselectorapp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.enggvam.countryselectorapp.R;
import com.enggvam.countryselectorapp.activity.viewModel.PreferenceSharedViewModel;
import com.enggvam.countryselectorapp.data.db.entity.Region;
import com.enggvam.countryselectorapp.databinding.FragmentMainBinding;
import com.enggvam.countryselectorapp.fragment.viewModel.MainFragmentViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * <h1>Main {@link Fragment}</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class MainFragment extends Fragment {

    private FragmentMainBinding rootBinding;
    private MainFragmentViewModel viewModel;
    private PreferenceSharedViewModel preferenceViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootBinding = FragmentMainBinding.inflate(inflater, container, false);
        initConfigs();
        initListeners();
        initViewModelObservers();
        return rootBinding.getRoot();
    }

    private void initConfigs() {
        viewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        preferenceViewModel = new ViewModelProvider(requireActivity()).get(PreferenceSharedViewModel.class);
    }

    private void initListeners() {
        rootBinding.includedSelectedRegion.getRoot().setOnClickListener(v ->
                Navigation.findNavController(v).navigate(
                        MainFragmentDirections.actionMainFragmentToRegionBottomSheet()));
    }

    private void initViewModelObservers() {
        viewModel.getLatestRegionList().observe(getViewLifecycleOwner(), new Observer<List<Region>>() {
            @Override
            public void onChanged(List<Region> regions) {
                if (preferenceViewModel.getRegionHash() == 0 ||
                        preferenceViewModel.getRegionHash() != Objects.hash(regions))
                    viewModel.saveRegions(regions);

                preferenceViewModel.setRegionHash(Objects.hash(regions));
            }
        });

        viewModel.getRegionBackendError().observe(getViewLifecycleOwner(), backendError -> {
            Toast.makeText(requireContext(),
                    getString(R.string.toast_error_unable_to_fetch__latest_data), Toast.LENGTH_SHORT).show();
        });

        preferenceViewModel.getSelectedRegionName().observe(getViewLifecycleOwner(), s ->
                rootBinding.includedSelectedRegion.tvRegion.setText(
                        TextUtils.isEmpty(s) ? getString(R.string.tv_unknown) : s));

        preferenceViewModel.getSelectedRegionCode().observe(getViewLifecycleOwner(), s -> {
            if (!TextUtils.isEmpty(s))
                Glide.with(requireContext())
                        .asBitmap()
                        .load(viewModel.getSelectedRegionFlagUri(s))
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                        .error(android.R.drawable.stat_notify_error)
                        .into(rootBinding.includedSelectedRegion.ivFlag);
        });
    }
}
