package com.example.feedthecatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public int[] slideImages = {R.id.img, R.id.button_slide, R.id.slideText};
    public String[] slidetexts = {"This is the Cat. The Cat is hungry all the time, so you have to feed him every day, otherwise the cat will be very upset.",
    "Press the button to feed the Cat.For each click you get 1 satiety point.Every 15 points of satiety,the Cat will dance with happiness.",
            "Every day your counter will be reset to zero. Try to keep your Cat fed."};


    public SlideAdapter(Context context){
        this.context = context;
    }



    @Override
    public int getCount() {
        return slideImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide1,container,false);

        TextView textView = view.findViewById(R.id.slideText);

        textView.setText(slidetexts[position]);
       //imageView.setLayoutParams(slideImages[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout) object);
    }
}
