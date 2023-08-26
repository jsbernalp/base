package com.jonathanbernal.libbase.list.factories;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.jonathanbernal.libbase.list.items.GenericItemView;

public interface GenericAdapterFactory {

    int TYPE_CATEGORY = 1004;

    @NonNull
    GenericItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

}