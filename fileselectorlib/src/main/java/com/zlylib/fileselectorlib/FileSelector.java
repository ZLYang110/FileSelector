package com.zlylib.fileselectorlib;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.zlylib.fileselectorlib.ui.FileSelectorActivity;

import java.lang.ref.WeakReference;

public class FileSelector {

    public static final int BY_NAME_ASC = 0;
    public static final int BY_NAME_DESC = 1;
    public static final int BY_TIME_ASC = 2;
    public static final int BY_TIME_DESC = 3;
    public static final int BY_SIZE_ASC = 4;
    public static final int BY_SIZE_DESC = 5;
    public static final int BY_EXTENSION_ASC = 6;

    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;

    private FileSelector(Activity activity) {
        this(activity,null);
    }

    private FileSelector(Fragment fragment){
        this(fragment.getActivity(),fragment);
    }

    private FileSelector(Activity mContext, Fragment mFragment) {
        this.mContext = new WeakReference<>(mContext);
        this.mFragment = new WeakReference<>(mFragment);
    }

    public static SelectCreator from(Activity activity){
        return new FileSelector(activity).initFile();
    }

    public static SelectCreator from(Fragment fragment){
        return new FileSelector(fragment).initFile();
    }

    private SelectCreator initFile(){
        return new SelectCreator(this);
    }




    public Activity getActivity() {
        return mContext.get();
    }

    public Fragment getFragment() {
        return mFragment != null ? mFragment.get() : null;
    }



}
