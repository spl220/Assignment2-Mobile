package com.df.widget;

import com.df.dianping.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PoiListItem extends LinearLayout
{

	private static int CARD_PADDING = 0;
	private static int CHECKIN_PADDING = 0;
	public static float DISTANCE_FACTOR = 1.0F;
	private static int PROMO_PADDING = 0;
	private static int BUSY_PADDING = 0;

	View card;
	View checkin;
	PoiStar star;
	View promo;
	TextView name;
	TextView price;
	TextView addr;
	TextView distance;
    ImageView busy = (ImageView) findViewById(R.id.busy);

	public PoiListItem(Context context) {
		super(context);
	}

	public PoiListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public TextView getName(){
		return this.name;
	}
	public void setPoiData(String name, int price, String addr, int stars,
			int isBusy, boolean isCard, boolean isPromo, boolean isCheckIn) {
		
		int m = 0;
		int i = 0;
		int j = 0;
		int k = 0;

		this.addr.setText(addr);
		this.price.setText("Price: "+price);
		this.star.setStar(stars);
		this.name.setText(name);

        switch(isBusy){
			case 0:
			    ImageView imageviewGreen = (ImageView) findViewById(R.id.busy);
                Drawable myDrawableGreen = getResources().getDrawable(R.drawable.ic_green);
                imageviewGreen.setImageDrawable(myDrawableGreen);
				System.out.println("BBBB:green");
                break;
          case 1:
              ImageView imageviewYellow = (ImageView) findViewById(R.id.busy);
              Drawable myDrawableYellow = getResources().getDrawable(R.drawable.ic_yellow);
              imageviewYellow.setImageDrawable(myDrawableYellow);
				System.out.println("BBBB:yellow");
                break;
            case 2:
                ImageView imageviewRed = (ImageView) findViewById(R.id.busy);
                Drawable myDrawableRed = getResources().getDrawable(R.drawable.ic_red);
                imageviewRed.setImageDrawable(myDrawableRed);
				System.out.println("BBBB:red");
                break;
		}
        BUSY_PADDING = BitmapFactory.decodeResource(getResources(), R.drawable.ic_red).getWidth();
        BUSY_PADDING += (int)(6.0F * getResources().getDisplayMetrics().density);
        i += BUSY_PADDING;

		if(isPromo)
		{
			if(PROMO_PADDING == 0)
			{
				//PROMO_PADDING = BitmapFactory.decodeResource(getResources(), R.drawable.ic_promo_small).getWidth();
				PROMO_PADDING += (int)(6.0F * getResources().getDisplayMetrics().density);
			}
			m += PROMO_PADDING;
			i += PROMO_PADDING;
			//this.promo.setVisibility(View.VISIBLE);
		}
		else
		{
			//this.promo.setVisibility(View.INVISIBLE);
		}
		
		if(isCheckIn)
		{
			if(CHECKIN_PADDING == 0)
			{
				//CHECKIN_PADDING = BitmapFactory.decodeResource(getResources(), R.drawable.ic_checkin_small).getWidth();
				CHECKIN_PADDING += (int)(6.0F * getResources().getDisplayMetrics().density);
			}
			m += CHECKIN_PADDING;
			i += CHECKIN_PADDING;
			j += CHECKIN_PADDING;
			//this.checkin.setVisibility(View.VISIBLE);
		}
		else
		{
			//this.checkin.setVisibility(View.INVISIBLE);
		}
		
		if(isCard)
		{
			if(CARD_PADDING == 0)
			{
				//CARD_PADDING = BitmapFactory.decodeResource(getResources(), R.drawable.ic_card_small).getWidth();
				CARD_PADDING += (int)(6.0F * getResources().getDisplayMetrics().density);
			}
			m += CARD_PADDING;
			i += CARD_PADDING;
			j += CARD_PADDING;
			k += CARD_PADDING;
			//this.card.setVisibility(View.VISIBLE);
		}
		else
		{
			//this.card.setVisibility(View.INVISIBLE);
		}
		
		//this.busy.setPadding(this.busy.getPaddingLeft(), this.busy.getPaddingTop(), i, this.busy.getPaddingBottom());
		//this.promo.setPadding(this.promo.getPaddingLeft(), this.promo.getPaddingTop(), j, this.promo.getPaddingBottom());
		//this.checkin.setPadding(this.checkin.getPaddingLeft(), this.checkin.getPaddingTop(), k, this.checkin.getPaddingBottom());
		//this.name.setPadding(this.name.getPaddingLeft(), this.name.getPaddingTop(), m, this.name.getPaddingBottom());
	}
	
	protected void onFinishInflate() {
		super.onFinishInflate();
		this.star = ((PoiStar) findViewById(R.id.poistar));
		//this.busy = findViewById(R.id.busy);
		//this.promo = findViewById(R.id.promo);
		//this.checkin = findViewById(R.id.checkin);
		//this.card = findViewById(R.id.card);
		this.name = ((TextView) findViewById(R.id.name));
		this.price = ((TextView) findViewById(R.id.price));
		this.addr = ((TextView) findViewById(R.id.addr));
		this.distance = ((TextView) findViewById(R.id.distance));
	}
	
	public void setDistanceText(String dis)
	{
		this.distance.setText(dis);
	}
	
}