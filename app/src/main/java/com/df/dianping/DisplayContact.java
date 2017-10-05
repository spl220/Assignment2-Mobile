package com.df.dianping;

/**
 * Created by shunshun on 05/10/2017.
 */


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayContact extends Activity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;

    TextView name ;
    TextView phone;
    TextView street;
    TextView latitude;
    TextView longitude;
    TextView rank;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        name = (TextView) findViewById(R.id.editTextName);
        phone = (TextView) findViewById(R.id.editTextPhone);
        street = (TextView) findViewById(R.id.editTextLongitude);
        latitude = (TextView) findViewById(R.id.editTextLatitude);
        longitude = (TextView) findViewById(R.id.editTextLongitude);
        rank = (TextView) findViewById(R.id.editTextRank);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_NAME));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_PHONE));
                String stree = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_STREET));
                String latitud = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_LATITUDE));
                String longitud = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_LONGITUDE));
                String ran = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_RANK));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                phone.setText((CharSequence)phon);
                phone.setFocusable(false);
                phone.setClickable(false);


                street.setText((CharSequence)stree);
                street.setFocusable(false);
                street.setClickable(false);

                latitude.setText((CharSequence)latitud);
                latitude.setFocusable(false);
                latitude.setClickable(false);

                longitude.setText((CharSequence)longitud);
                longitude.setFocusable(false);
                longitude.setClickable(false);

                rank.setText((CharSequence)ran);
                rank.setFocusable(false);
                rank.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_contact, menu);
            } else{
                getMenuInflater().inflate(R.menu.menu_main, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                phone.setEnabled(true);
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);

                street.setEnabled(true);
                street.setFocusableInTouchMode(true);
                street.setClickable(true);

                latitude.setEnabled(true);
                latitude.setFocusableInTouchMode(true);
                latitude.setClickable(true);

                longitude.setEnabled(true);
                longitude.setFocusableInTouchMode(true);
                longitude.setClickable(true);

                rank.setEnabled(true);
                rank.setFocusableInTouchMode(true);
                rank.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateContact(id_To_Update,name.getText().toString(),
                        phone.getText().toString(),
                        street.getText().toString(),
                        Double.parseDouble(longitude.getText().toString()),
                        Double.parseDouble(latitude.getText().toString()),
                        Double.parseDouble(rank.getText().toString())

                        )){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{
                if(mydb.insertContact(name.getText().toString(), phone.getText().toString(),
                        street.getText().toString(),
                        Double.parseDouble(longitude.getText().toString()),
                        Double.parseDouble(latitude.getText().toString()),
                        Double.parseDouble(rank.getText().toString()))){
                    Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }
}