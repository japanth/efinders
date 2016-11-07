package uk.co.alt236.btlescan.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.alt236.btlescan.R;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconManufacturerData;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.adapters.LeDeviceListAdapter;
import uk.co.alt236.btlescan.containers.BluetoothLeDeviceStore;
import uk.co.alt236.btlescan.util.BluetoothLeScanner;
import uk.co.alt236.btlescan.util.BluetoothUtils;
import uk.co.alt236.btlescan.util.Constants;
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;


public class DisplayImage extends Activity {
    private MyImage image;
    private ImageView imageView;
    private TextView description;
    private String jstring;

    static SQLiteDatabase mDb;
    static DBHelper mHelper;
    static Cursor mCursor;
    TextView showname,show_roomdis;
    TextView showdistance,showroom,showunit,show_unit2,show_room_dis,show_room_address;
    Button scan;


    boolean isScan = false;

    private BluetoothUtils mBluetoothUtils;
    private BluetoothLeScanner mScanner;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothLeDeviceStore mDeviceStore;
    IBeaconManufacturerData iBeaconData;
    IBeaconDevice iBeacon;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        imageView = (ImageView) findViewById(R.id.display_image_view);
   //      description = (TextView) findViewById(R.id.text_view_description);
        Bundle extras = getIntent().getExtras();
         name = extras.getString("name");


        mBluetoothUtils = new BluetoothUtils(this);
        mScanner = new BluetoothLeScanner(mLeScanCallback, mBluetoothUtils);
        final boolean mIsBluetoothOn = mBluetoothUtils.isBluetoothOn();
        final boolean mIsBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();

        mDeviceStore = new BluetoothLeDeviceStore();

        scan = (Button) findViewById(R.id.scan);
        showname = (TextView) findViewById(R.id.show_name_beaconn);
        showroom = (TextView) findViewById(R.id.show_room);
        showunit = (TextView) findViewById(R.id.show_unit);
        show_unit2 = (TextView) findViewById(R.id.show_unit2);
        showdistance = (TextView) findViewById(R.id.show_distance_beacon);
//       show_room_dis = (TextView) findViewById(R.id.show_room_dis);
        show_room_address = (TextView) findViewById(R.id.show_roomadd);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScan();
            }
        });


        if (extras != null) {
            jstring = extras.getString("IMAGE");
        }
        image = getMyImage(jstring);
//      description.setText(image.toString());
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        imageView.setImageBitmap(ImageResizer
                .decodeSampledBitmapFromFile(image.getPath(), width, height));
    }




    final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {

            final BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());
            mDeviceStore.addDevice(deviceLe);
            final EasyObjectCursor<BluetoothLeDevice> c = mDeviceStore.getDeviceCursor();
            try{
                iBeaconData = new IBeaconManufacturerData(deviceLe);
                iBeacon = new IBeaconDevice(deviceLe);
            }catch (Exception e){iBeaconData= null;}
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(iBeaconData != null) {


                        ImageView signal = (ImageView) findViewById(R.id.image_signal);
                        show_roomdis = (TextView) findViewById(R.id.show_roomdis);
                        showroom = (TextView) findViewById(R.id.show_room);


                        mHelper = new DBHelper(getApplicationContext());
                        mDb = mHelper.getWritableDatabase();
                        mCursor = mDb.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAMEQ  ,null);
                        mCursor.moveToFirst();
                        int count =0;

                        ArrayList nameroom = new ArrayList();
                        ArrayList adressroom = new ArrayList();




                        while ( !mCursor.isAfterLast() ){

                            nameroom.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.COL_ITEM_NAMEQ)));
                            adressroom.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.COL_ADDRESSQ)));
                            mCursor.moveToNext();
                            count++;
                        }



                        for (int i=0;i<=nameroom.size()-1;i++){
                            if(adressroom.get(i).equals(iBeacon.getAddress())&&Double.parseDouble(Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(iBeacon.getAccuracy()))<=10){
                                showroom.setText(""+nameroom.get(i));
                                show_room_address.setText(""+System.currentTimeMillis());
                                show_roomdis.setText(Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(iBeacon.getAccuracy()));

                            }
                            else {
                                showroom.setText("   ");
                            }
                            Log.i("adressroomsss", "" + adressroom.get(i));
                   /*         showroom.setText(""+nameroom.get(i));
                            //show_room_address.setText(iBeacon.getAddress());
                            show_roomdis.setText(Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(iBeacon.getAccuracy()));*/

                        }



                        String[] res = DB.selectitem(getApplicationContext(), name);
                        if(res[0]!=null) {
                            showname.setText(res[0]);
                            showunit.setText(Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(iBeacon.getAccuracy()));
                            show_unit2.setText(" Meter");
                            if(iBeacon.getAccuracy()<2){
                                showdistance.setText("Near");
                                signal.setImageResource(R.drawable.ic_signal_4);

                            }else if (iBeacon.getAccuracy()>2&&iBeacon.getAccuracy()<8){
                                signal.setImageResource(R.drawable.ic_signal_3);
                                showdistance.setText("Medium");
                            }
                            else if (iBeacon.getAccuracy()>8&&iBeacon.getAccuracy()<15){
                                signal.setImageResource(R.drawable.ic_signal_2);
                                showdistance.setText("Far");
                            }
                            else if (iBeacon.getAccuracy()>15&&iBeacon.getAccuracy()<20){
                                signal.setImageResource(R.drawable.ic_signal_1);
                                showdistance.setText("Very Far");
                            }
                            else if (iBeacon.getAccuracy()>20){
                                signal.setImageResource(R.drawable.ic_signal_0);
                                showdistance.setText("Out Of Range");
                            }
                            Log.i("RES", "" + res[0]);
                            Log.i("UUID", device.getAddress());
                        }



                    }
                }
            });
        }
    };




    private void startScan() {
        final boolean mIsBluetoothOn = mBluetoothUtils.isBluetoothOn();
        final boolean mIsBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();
        mDeviceStore.clear();

      /*  mLeDeviceListAdapter = new LeDeviceListAdapter(this, mDeviceStore.getDeviceCursor());
        mList.setAdapter(mLeDeviceListAdapter);*/
        mDeviceStore.getDeviceCursor();

        mBluetoothUtils.askUserToEnableBluetoothIfNeeded();
        if (mIsBluetoothOn && mIsBluetoothLePresent) {
            mScanner.scanLeDevice(-1, true);
        }
    }




    private MyImage getMyImage(String image) {
        try {
            JSONObject job = new JSONObject(image);
            return (new MyImage(job.getString("title"),
                    job.getString("description"), job.getString("path"),
                    job.getLong("datetimeLong")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * go back to main activity
     *
     * @param v
     */
    public void btnBackOnClick(View v) {
        startActivity(new Intent(this, ShowlistbeaconActivity.class));
        finish();
    }

    /**
     * delete the current item;
     *
     * @param v
     */
    public void btnDeleteOnClick(View v) {
        DAOdb db = new DAOdb(this);
        db.deleteImage(image);
        db.close();
        startActivity(new Intent(this, ShowlistbeaconActivity.class));
        finish();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        // Save the user's current game state
        if (jstring != null) {
            outState.putString("jstring", jstring);
        }
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        if (savedInstanceState.containsKey("jstring")) {
            jstring = savedInstanceState.getString("jstring");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
