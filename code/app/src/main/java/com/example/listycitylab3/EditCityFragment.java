package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.listycitylab3.City;

public class EditCityFragment extends DialogFragment {
    public static EditCityFragment newInstance(City selectedCity, int position) {
        EditCityFragment fragment = new EditCityFragment();
        // Lines 20 to 26 are adapted from ChatGPT, OpenAI, "How do I implement a bundle in Android
        // Studio", refer to README.txt, 2025-09-17
        Bundle args = new Bundle();
        args.putString("cityName", selectedCity.getName());
        args.putString("provinceName", selectedCity.getProvince());
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    interface EditCityDialogListener {
        void editCity(String cityName, String provinceName, int position);
    }
    private EditCityDialogListener listener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        String cityName = getArguments().getString("cityName");
        String provinceName = getArguments().getString("provinceName");
        int position = getArguments().getInt("position");

        editCityName.setText(cityName);
        editProvinceName.setText(provinceName);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Edit", (dialog, which) -> {
                    String newCityName = editCityName.getText().toString();
                    String newProvinceName = editProvinceName.getText().toString();

                    listener.editCity(newCityName, newProvinceName, position);
                })
                .create();
    }
}