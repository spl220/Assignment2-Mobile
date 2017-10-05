package com.df.widget;
import com.df.dianping.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by Lenovo on 2017/10/4.
 */

public class DetailItem extends LinearLayout{

    PoiStar restaurant_stars;
    ImageView restaurant_Image;
    TextView average_cost;
    TextView taste_score;
    TextView environment_score;
    TextView service_score;
    TextView restaurant_address;
    TextView restaurant_phone;
    TextView restaurant_popularDishes;
    TextView restaurant_selfDescription;


    public DetailItem(Context context) {
        super(context);
    }

    public DetailItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRestaurantDetailData(Map<String, Object> map) {
        this.average_cost.setText(map.get("restaurant_average_cost").toString());
        this.environment_score.setText(map.get("restaurant_environment_score").toString());
        this.service_score.setText(map.get("restaurant_service_score").toString());
        ImageView restaurant_Image;
        TextView taste_score;

        TextView restaurant_address;
        TextView restaurant_phone;
        TextView restaurant_popularDishes;
        TextView restaurant_selfDescription;
    }
}
