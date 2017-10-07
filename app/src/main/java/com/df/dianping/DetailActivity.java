package com.df.dianping;
import com.df.widget.QuickAction;
import com.df.widget.QuickActionBar;
import com.df.widget.QuickActionWidget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.df.dianping.R.id.maps;


public class DetailActivity extends Activity implements OnClickListener
{

	Handler handler = new Handler()
	{
		public void handleMessage(Message paramMessage)
		{
			if(paramMessage.what == 1)
			{
                restaurantDetail.findViewById(R.id.loadingbar).setVisibility(View.GONE);
                restaurantDetail.findViewById(R.id.serverdata).setVisibility(View.VISIBLE);
			}
		}
	};
	private String restuarantName;
	private  LinearLayout restaurantDetail;
	private Animation enterAnim;
	private Animation exitAnim;
	private QuickActionWidget mBar;
    private Map<String, Object> mData;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        Intent intentExtras = getIntent();
		Bundle bundle = intentExtras.getExtras();
        if(bundle != null){
              restuarantName = bundle.getString("name");
            System.out.println("AAAA："+ restuarantName);
        }
        mData = PoiResultData.getData(restuarantName);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.poidetail);

        LayoutInflater inflater = LayoutInflater.from(this);
        restaurantDetail = (LinearLayout)inflater.inflate(R.layout.poiinfo, null);


        LinearLayout scroll = (LinearLayout)findViewById(R.id.lite_list);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        scroll.addView(restaurantDetail, layoutParams);

        new Thread()
        {
        	public void run()
        	{
        		try
        		{
					Thread.sleep(1500);
				}
        		catch (InterruptedException e)
        		{
					e.printStackTrace();
				}

        		Message msg = new Message();
        		msg.what = 1;
        		handler.sendMessage(msg);
        	}
        }.start();

        prepareQuickActionBar();
        enterAnim = AnimationUtils.loadAnimation(this, R.anim.anim_enter);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.anim_exit);
        //DetailItem item = (DetailItem) restaurantDetail;
        //item.setRestaurantDetailData(mData);
        InitialResultDetails(mData);
        //TextView textview = (TextView) findViewById(R.id.text2_shop_score1);
        //textview.setText("AAABB");
        View btnMap = findViewById( maps);
        btnMap.setOnClickListener(this);

        View btnMore = findViewById(R.id.more);
        btnMore.setOnClickListener(this);

        View btnRequest = findViewById(R.id.requestroute);
        btnRequest.setOnClickListener(this);


	}

	private void InitialResultDetails(Map<String, Object> map) {
        System.out.println("begin inserting details: ");
        TextView textview;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            switch(entry.getKey()){
                case "restaurant_name":
                    textview = (TextView)findViewById(R.id.restaurantName);
                    textview.setText(map.get("restaurant_name").toString());
                    break;
                case "restaurant_average_cost":
                    textview = (TextView)findViewById(R.id.average_cost);
                    textview.setText(map.get("restaurant_average_cost").toString());
                    break;
                case "restaurant_environment_score":
                    textview = (TextView)findViewById(R.id.environment_score);
                    textview.setText(map.get("restaurant_environment_score").toString());
                    break;
                case "restaurant_service_score":
                    textview = (TextView) findViewById(R.id.service_score);
                    textview.setText(map.get("restaurant_service_score").toString());
                    break;
                case "restaurant_address":
                    textview = (TextView) findViewById(R.id.restaurant_address);
                    textview.setText(map.get("restaurant_address").toString());
                    break;
                case "restaurant_stars":
                    setRestaurantStars(((Integer)map.get("restaurant_stars")).intValue());
                    break;
            }
            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
        }
    }

    private void setRestaurantStars(int restaurant_stars) {
        ImageView imageview = (ImageView) findViewById(R.id.restaurant_stars);
        Drawable myDrawable;
        switch(restaurant_stars){
            case 0:
                myDrawable = getResources().getDrawable(R.drawable.star00);
                imageview.setImageDrawable(myDrawable);
                break;
            case 5:
                myDrawable = getResources().getDrawable(R.drawable.star05);
                imageview.setImageDrawable(myDrawable);
                break;
            case 10:
                myDrawable = getResources().getDrawable(R.drawable.star10);
                imageview.setImageDrawable(myDrawable);
                break;
            case 15:
                myDrawable = getResources().getDrawable(R.drawable.star15);
                imageview.setImageDrawable(myDrawable);
                break;
            case 20:
                myDrawable = getResources().getDrawable(R.drawable.star20);
                imageview.setImageDrawable(myDrawable);
                break;
            case 25:
                myDrawable = getResources().getDrawable(R.drawable.star25);
                imageview.setImageDrawable(myDrawable);
                break;
            case 30:
                myDrawable = getResources().getDrawable(R.drawable.star30);
                imageview.setImageDrawable(myDrawable);
                break;
            case 35:
                myDrawable = getResources().getDrawable(R.drawable.star35);
                imageview.setImageDrawable(myDrawable);
                break;
            case 40:
                myDrawable = getResources().getDrawable(R.drawable.star40);
                imageview.setImageDrawable(myDrawable);
                break;
            case 45:
                myDrawable = getResources().getDrawable(R.drawable.star45);
                imageview.setImageDrawable(myDrawable);
                break;
            case 50:
                myDrawable = getResources().getDrawable(R.drawable.star50);
                imageview.setImageDrawable(myDrawable);
                break;
        }
    }



    public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.more:
			{
				View view = findViewById(R.id.popup_more);

				if(view.getVisibility() == View.VISIBLE)
				{
					view.startAnimation(exitAnim);
					view.setVisibility(View.GONE);
				}
				else
				{
					view.startAnimation(enterAnim);
					view.setVisibility(View.VISIBLE);
				}
				break;
			}

			case R.id.requestroute:
			{
				mBar.show(v);
				break;
			}
            case R.id.maps:
                TextView textview = (TextView) findViewById(R.id.restaurant_address);
                String direction = "google.navigation:q=" + textview.getText().toString();
                Uri gmmIntentUri = Uri.parse(direction);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;
		}

	}


	private void prepareQuickActionBar() {
		this.mBar = new QuickActionBar(this);
		this.mBar.addQuickAction(new MyQuickAction(this, R.drawable.icon_car,
				"�Լ�"));
		this.mBar.addQuickAction(new MyQuickAction(this, R.drawable.icon_bus,
				"������ͨ"));
		this.mBar.addQuickAction(new MyQuickAction(this, R.drawable.icon_walk,
				"����"));
		// this.mBar.setOnQuickActionClickListener(this.mActionListener);
	}

	private static class MyQuickAction extends QuickAction {
		private static final ColorFilter BLACK_CF = new LightingColorFilter(
				-16777216, -16777216);

		// public MyQuickAction(Context paramContext, int paramInt1, int
		// paramInt2)
		// {
		// super(buildDrawable(paramContext, paramInt1),
		// String.valueOf(paramInt2));
		// }

		public MyQuickAction(Context paramContext, int paramInt,
				CharSequence paramCharSequence) {
			super(paramContext, paramInt, paramCharSequence);
		}

		// private static Drawable buildDrawable(Context paramContext, int
		// paramInt)
		// {
		// Drawable localDrawable =
		// paramContext.getResources().getDrawable(paramInt);
		// localDrawable.setColorFilter(BLACK_CF);
		// return localDrawable;
		// }
	}
}