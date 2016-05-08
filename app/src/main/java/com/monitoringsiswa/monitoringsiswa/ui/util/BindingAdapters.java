package com.monitoringsiswa.monitoringsiswa.ui.util;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class BindingAdapters {

    @BindingAdapter({"visibility"})
    public static void setVisibility(ImageView imageView, boolean visibility) {
        if (visibility == true){
            imageView.setVisibility(View.VISIBLE);
        } else if (visibility == false){
            imageView.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"visibility"})
    public static void setVisibility(TextView textView, boolean visibility){
        if (visibility == true){
            textView.setVisibility(View.VISIBLE);
        } else if (visibility == false){
            textView.setVisibility(View.GONE);
        }
    }
}
