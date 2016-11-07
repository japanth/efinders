package uk.co.alt236.btlescan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.commonsware.cwac.merge.MergeAdapter;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.BluetoothService;
import uk.co.alt236.bluetoothlelib.device.adrecord.AdRecord;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconType;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconUtils;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconManufacturerData;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.util.Constants;
import uk.co.alt236.btlescan.util.TimeFormatter;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;



public class AddBeaconDetailsMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RadioButton objects, room;

    String uuid;
    String major;
    String dis;
    String address;
    EditText name;
    Button btn_add;
    String status,image ;
    private RadioGroup radioSexGroup,radioImageGroup;
    private RadioButton radioSexButton,radioImageButton;
    private Button btnDisplay;
    public static final String EXTRA_DEVICE = "extra_device";
    @Bind(android.R.id.list)
    protected ListView mList;
    @Nullable
    @Bind(android.R.id.empty)
    protected View mEmpty;
    private BluetoothLeDevice mDevice;

    private void appendAdRecordView(final MergeAdapter adapter, final String title, final AdRecord record) {
    }

    private void appendDeviceInfo(final BluetoothLeDevice device) {

        address = device.getAddress();
        final String supportedServices;
        if(device.getBluetoothDeviceKnownSupportedServices().isEmpty()){
            supportedServices = getString(R.string.no_known_services);
        } else {
            final StringBuilder sb = new StringBuilder();

            for(final BluetoothService service : device.getBluetoothDeviceKnownSupportedServices()){
                if(sb.length() > 0){
                    sb.append(", ");
                }

                sb.append(service);
            }
            supportedServices = sb.toString();
        }

    }

    private void appendHeader(final MergeAdapter adapter, final String title) {
        final LinearLayout lt = (LinearLayout) getLayoutInflater().inflate(R.layout.list_item_view_header, null);
        final TextView tvTitle = (TextView) lt.findViewById(R.id.title);
        tvTitle.setText(title);

        adapter.addView(lt);
    }

    private void appendIBeaconInfo(final IBeaconManufacturerData iBeaconData) {

        uuid = iBeaconData.getUUID();
        major =""+iBeaconData.getMajor();
    }

    private void appendRssiInfo(final MergeAdapter adapter, final BluetoothLeDevice device) {
    }

    private void appendSimpleText(final MergeAdapter adapter, final byte[] data) {

    }

    private void appendSimpleText(final MergeAdapter adapter, final String data) {
        final LinearLayout lt = (LinearLayout) getLayoutInflater().inflate(R.layout.list_item_view_textview, null);
        final TextView tvData = (TextView) lt.findViewById(R.id.data);

        tvData.setText(data);

        adapter.addView(lt);
    }


    private String formatRssi(final double rssi) {
        return getString(R.string.formatter_db, String.valueOf(rssi));
    }

    private String formatRssi(final int rssi) {
        return getString(R.string.formatter_db, String.valueOf(rssi));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beacon_details_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ButterKnife.bind(this);
        mList.setEmptyView(mEmpty);

        objects = (RadioButton) findViewById(R.id.objects);
        room = (RadioButton) findViewById(R.id.room);


        name = (EditText) findViewById(R.id.beacon_name);
        //btn_add = (Button) findViewById(R.id.btn_add_beacon);

        addListenerOnButton();
/*
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DB.insert(getApplicationContext(),address,""+name.getText(),dis,status,major);

                Intent tt = new Intent(DeviceDetailsActivity.this,ShowlistbeaconActivity.class);
                startActivity(tt);
            }
        });*/


        mDevice = getIntent().getParcelableExtra(EXTRA_DEVICE);

        pupulateDetails(mDevice);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void addListenerOnButton() {

        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        btnDisplay = (Button) findViewById(R.id.btn_add_beacon);

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioSexButton = (RadioButton) findViewById(selectedId);


                status = "" + radioSexButton.getText();


                if(status.equals("Objects")){
                    DB.insert(getApplicationContext(), address, "" + name.getText(), dis, status, major);
                    Intent intent = new Intent(AddBeaconDetailsMainActivity.this, TestImages.class);
                    intent.putExtra("name", ""+name.getText());
                    startActivity(intent);

                }else if (status.equals("Room")){
                    DB.insertq(getApplicationContext(), "" + name.getText(), address, dis, major);
                    Intent intent = new Intent(AddBeaconDetailsMainActivity.this, ShowlistbeaconActivity.class);
                    startActivity(intent);

                }


            }

        });


    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_beacon_details_main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent home = new Intent(AddBeaconDetailsMainActivity.this,CategoryActivity.class);
            startActivity(home);


        } else if (id == R.id.nav_about) {
            Intent info = new Intent(AddBeaconDetailsMainActivity.this,ShowInfoActivity.class);
            startActivity(info);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:

                final Intent intent = new Intent(this, DeviceControlActivity.class);
                intent.putExtra(DeviceControlActivity.EXTRA_DEVICE, mDevice);

                startActivity(intent);

                break;
        }
        return true;
    }

    private void pupulateDetails(final BluetoothLeDevice device) {

        final IBeaconDevice iBeacon = new IBeaconDevice(device);
        final String accuracy = Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(iBeacon.getAccuracy());
        dis=accuracy;



        final boolean isIBeacon = BeaconUtils.getBeaconType(device) == BeaconType.IBEACON;
        if (isIBeacon) {
            final IBeaconManufacturerData iBeaconData = new IBeaconManufacturerData(device);

            appendIBeaconInfo(iBeaconData);
            appendDeviceInfo(device);

        }

    }

    private static String formatTime(final long time) {
        return TimeFormatter.getIsoDateTime(time);
    }

    private static String hexEncode(final int integer) {
        return "0x" + Integer.toHexString(integer).toUpperCase(Locale.US);
    }
}
