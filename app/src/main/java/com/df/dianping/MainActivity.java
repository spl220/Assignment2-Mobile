package com.df.dianping;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.df.widget.PoiListItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.support.*;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button btnSearch;
    public Location mLastLocation;
    private ArrayList<Map<String, Object>> mData;
    private ArrayList<Map<String, Object>> filterData;
    private View loadingView;
    private ListView listView;
    private FusedLocationProviderClient mFusedLocationClient;
    ListAdapter resultAdapter = null;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    public double currentLongitude;
    public double currentLatitude;
    private LocationProvider _locationProvider;
    private LocationManager _locationManager;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.searchresult);

        _locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        _locationProvider = _locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
        @SuppressWarnings("MissingPermission")
        Location location = _locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        /*mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();

        }*/
        DBHelper mydb = new DBHelper(this);
        //mydb.getWritableDatabase().execSQL("drop table  contacts");
        mydb.getWritableDatabase().execSQL("create table restaurants (id integer primary key, name text, phone text, street text, latitude real, longitude real, rank real)");
        mydb.getWritableDatabase().execSQL("INSERT INTO restaurants VALUES(0,'11','22','33',44,55,66)");
        mData=mydb.getAllContacts();
        //mData = PoiResultData.searchDatebase(location.getLatitude(),location.getLongitude());
        //mData = PoiResultData.searchDatebase(mLastLocation);
        //filterData = mData;

        listView = (ListView) findViewById(R.id.resultlist);
        //list.setOnItemClickListener(mOnClickListener);
        resultAdapter = new MainActivity.PoiResultAdapter(this);

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

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            System.out.println("Lastlocation current: latitude:+AAAA"+mLastLocation.getLatitude());
                            System.out.println("Lastlocation current: longitude:+AAAA"+mLastLocation.getLongitude());
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            //showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });
    }

    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {
        View container = findViewById(android.R.id.content);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }
    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }
    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
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
            intent.setClass(MainActivity.this, DetailActivity.class);
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
    public static Comparator<Map<String,Object>> PriceComparator = new Comparator <Map<String,Object>>( ) {

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