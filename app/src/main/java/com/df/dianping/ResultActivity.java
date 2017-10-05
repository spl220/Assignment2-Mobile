package com.df.dianping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import com.df.widget.PoiListItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class ResultActivity extends Activity implements OnClickListener {
    private ArrayList<Map<String, Object>> mData;
    private ArrayList<Map<String, Object>> filterData;
    private View loadingView;
    private ListView listView;

    ListAdapter resultAdapter = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.searchresult);

        mData = PoiResultData.createData();
        filterData = mData;

        listView = (ListView) findViewById(R.id.resultlist);
        //list.setOnItemClickListener(mOnClickListener);
        resultAdapter = new PoiResultAdapter(this);

        View btnPrice = findViewById(R.id.id_sort_price);
        btnPrice.setOnClickListener(this);

        View btnBusyness = findViewById(R.id.id_sort_busyness);
        btnBusyness.setOnClickListener(this);

        loadingView = LayoutInflater.from(this).inflate(R.layout.listfooter, null);

        listView.addFooterView(loadingView);
        //loadingView.setVisibility(View.GONE);

        listView.setAdapter(resultAdapter);
        listView.setOnItemClickListener(mOnClickListener);
    }

    private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            Intent intent = new Intent();
            PoiListItem tempPol = (PoiListItem) v;
            String name = tempPol.getName().getText().toString();
            System.out.println("get name from poilistitem:@@@@" + name);
            Map map = filterData.get(position);
            System.out.println("get name from filterDatan:@@@@" + map.get("name").toString());
            System.out.println("choose id:@@@@" + id);
            System.out.println("choose view@@@@" + v.toString());
            intent.setClass(ResultActivity.this, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivity(intent);
            //ResultActivity.this.finish();
        }
    };


    public class PoiResultAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public PoiResultAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return filterData.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return filterData.get(position).get("name").toString();
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                Log.v("is NULL", "::" + position);
            }
            convertView = mInflater.inflate(R.layout.resultitem, null);

            PoiListItem item = (PoiListItem) convertView;

            Map map = filterData.get(position);

            item.setPoiData(
                    map.get("name").toString(),
                    ((Integer) map.get("price")).intValue(),
                    map.get("addr").toString(),
                    ((Integer) map.get("star")).intValue(),
                    ((Integer) map.get("busy")).intValue(),
                    ((Boolean) map.get("card")).booleanValue(),
                    ((Boolean) map.get("promo")).booleanValue(),
                    ((Boolean) map.get("checkin")).booleanValue()
            );

            item.setDistanceText(map.get("distance").toString());
            if (position == filterData.size() - 1) {
                listView.removeFooterView(loadingView);
            }
            return convertView;
        }

    }
    // Comparator for Ascending Order
    public static Comparator <Map<String,Object>> PriceComparator = new Comparator <Map<String,Object>>( ) {

        @Override
        public int compare(Map<String, Object> t1, Map<String, Object> t2) {
            return compareInt(((Integer) t1.get("price")).intValue(), ((Integer) t2.get("price")).intValue());
        }
    };
    // Comparator for Ascending Order
    public static Comparator <Map<String,Object>> BusynessComparator = new Comparator <Map<String,Object>>( ) {

        @Override
        public int compare(Map<String, Object> t1, Map<String, Object> t2) {
            return compareInt(((Integer) t1.get("busy")).intValue(), ((Integer) t2.get("busy")).intValue());
        }
    };
    private static int compareInt(int value1, int value2) {
        if(value1 > value2){
            return 1;
        }else if(value1 < value2){
            return -1;
        }else{
            return 0;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_sort_price: {
                Collections.sort(filterData, PriceComparator);
                ((PoiResultAdapter) resultAdapter).notifyDataSetChanged();
                break;
            }
            case R.id.id_sort_busyness: {
                Collections.sort(filterData, BusynessComparator);
                ((PoiResultAdapter) resultAdapter).notifyDataSetChanged();
                break;
            }
        }
    }
}