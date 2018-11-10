package ir.mbf5923.adineh.ganjadin.intropage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import agency.tango.materialintroscreen.SlideFragment;
import ir.mbf5923.adineh.ganjadin.R;


public class pageone extends SlideFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.intro_pageone, container, false);
    }

//    @Override
//    public void askForPermissions() {
//        super.askForPermissions();
//
//    }

    @Override
    public int backgroundColor() {
        return R.color.bluees;
    }

    @Override
    public int buttonsColor() {
        return R.color.bluees;
    }

//    @Override
//    public boolean canMoveFurther() {
//        return hasAnyPermissionsToGrant();
//    }
//    @Override
//    public boolean hasAnyPermissionsToGrant() {
//        return super.hasAnyPermissionsToGrant();
//    }
}
