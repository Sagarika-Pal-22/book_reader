package com.example.bookreader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.bookreader.Model.Banner_Model;
import com.example.bookreader.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    List<Banner_Model> banner_modelList;
    private LayoutInflater layoutInflater;

    public ViewPagerAdapter(Context context, List<Banner_Model> banner_modelList) {
        this.context = context;
        this.banner_modelList = banner_modelList;
    }

    @Override
    public int getCount() {
        return banner_modelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout,null);

        ImageView imageView = view.findViewById(R.id.imageview);
        Glide.with(context)
                .load(banner_modelList.get(position).getImage())
                .placeholder(R.drawable.no_image)
                .into(imageView);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
