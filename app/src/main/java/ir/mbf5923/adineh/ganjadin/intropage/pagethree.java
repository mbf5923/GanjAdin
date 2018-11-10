package ir.mbf5923.adineh.ganjadin.intropage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import agency.tango.materialintroscreen.SlideFragment;
import ir.mbf5923.adineh.ganjadin.R;

public class pagethree extends SlideFragment {
    private TextView textView3, textView4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.intro_pagethree, container, false);
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            view.findViewById(R.id.textView3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.textView4).setVisibility(View.VISIBLE);
            view.findViewById(R.id.textView4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestpermision();
                }
            });
        }
        return view;
    }

//    @Override
//    public void askForPermissions() {
//        super.askForPermissions();
//
//    }


    private void requestpermision() {
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.getActivity(), new String[]{android.Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public int backgroundColor() {
        return R.color.bluees;
    }

    //
    @Override
    public int buttonsColor() {
        return R.color.bluees;
    }

    @Override
    public boolean canMoveFurther() {
        return ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean hasAnyPermissionsToGrant() {
        return super.hasAnyPermissionsToGrant();
    }
}
