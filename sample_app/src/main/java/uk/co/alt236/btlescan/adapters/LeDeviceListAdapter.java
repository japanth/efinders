package uk.co.alt236.btlescan.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconType;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconUtils;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.activities.DB;
import uk.co.alt236.btlescan.activities.DBHelper;
import uk.co.alt236.btlescan.util.Constants;
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;

// Adapter for holding devices found through scanning.
public class LeDeviceListAdapter extends SimpleCursorAdapter {
    private final LayoutInflater mInflator;
    private final Activity mActivity;
    static SQLiteDatabase mDb;
    static DBHelper mHelper;
    static Cursor mCursor;

    public LeDeviceListAdapter(final Activity activity, final EasyObjectCursor<BluetoothLeDevice> cursor) {
        super(activity, R.layout.list_item_device, cursor, new String[0], new int[0], 0);
        mInflator = activity.getLayoutInflater();
        mActivity = activity;




    }

    @SuppressWarnings("unchecked")
    @Override
    public EasyObjectCursor<BluetoothLeDevice> getCursor() {
        return ((EasyObjectCursor<BluetoothLeDevice>) super.getCursor());
    }

    @Override
    public BluetoothLeDevice getItem(final int i) {
        return getCursor().getItem(i);
    }

    @Override
    public long getItemId(final int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        // General ListView optimization code.




        if (view == null) {
            view = mInflator.inflate(R.layout.list_item_device, null);
            viewHolder = new ViewHolder();
                viewHolder.checkb = (TextView) view.findViewById(R.id.checkb);

           // viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
            viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
          //  viewHolder.deviceRssi = (TextView) view.findViewById(R.id.device_rssi);
            viewHolder.deviceIcon = (ImageView) view.findViewById(R.id.device_icon);
         //   viewHolder.deviceLastUpdated = (TextView) view.findViewById(R.id.device_last_update);
           /* viewHolder.ibeaconMajor = (TextView) view.findViewById(R.id.ibeacon_major);
            viewHolder.ibeaconMinor = (TextView) view.findViewById(R.id.ibeacon_minor);
            viewHolder.ibeaconDistance = (TextView) view.findViewById(R.id.ibeacon_distance);
            viewHolder.ibeaconUUID = (TextView) view.findViewById(R.id.ibeacon_uuid);*/
        /*    viewHolder.ibeaconTxPower = (TextView) view.findViewById(R.id.ibeacon_tx_power);
            viewHolder.ibeaconSection = view.findViewById(R.id.ibeacon_section);
            viewHolder.ibeaconDistanceDescriptor = (TextView) view.findViewById(R.id.ibeacon_distance_descriptor);*/
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final BluetoothLeDevice device = getCursor().getItem(i);
        final String deviceName = device.getName();
        final double rssi = device.getRssi();

        if (deviceName != null && deviceName.length() > 0) {
            viewHolder.deviceName.setText(deviceName);
        } else {
            viewHolder.deviceName.setText(R.string.unknown_device);
        }

        if (BeaconUtils.getBeaconType(device) == BeaconType.IBEACON) {
            final IBeaconDevice iBeacon = new IBeaconDevice(device);
            final String accuracy = Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(iBeacon.getAccuracy());

            viewHolder.deviceIcon.setImageResource(R.drawable.ic_device_ibeacon);

        //    viewHolder.ibeaconSection.setVisibility(View.VISIBLE);
      //      viewHolder.ibeaconMajor.setText(String.valueOf(iBeacon.getMajor()));
       //     viewHolder.ibeaconMinor.setText(String.valueOf(iBeacon.getMinor()));
       //     viewHolder.ibeaconTxPower.setText(String.valueOf(iBeacon.getCalibratedTxPower()));
//            viewHolder.ibeaconUUID.setText(iBeacon.getUUID());
            /*viewHolder.ibeaconDistance.setText(
                    mActivity.getString(R.string.formatter_meters, accuracy));
            viewHolder.ibeaconDistanceDescriptor.setText(iBeacon.getDistanceDescriptor().toString());*/
        } else {
            viewHolder.deviceIcon.setImageResource(R.drawable.ic_bluetooth);
           // viewHolder.ibeaconSection.setVisibility(View.GONE);
        }

        final String rssiString =
                mActivity.getString(R.string.formatter_db, String.valueOf(rssi));
        final String runningAverageRssiString =
                mActivity.getString(R.string.formatter_db, String.valueOf(device.getRunningAverageRssi()));

        String[] user = DB.selectuser(mActivity.getApplicationContext());
        int s=0;
        mHelper = new DBHelper(mActivity.getApplicationContext());
        mDb = mHelper.getWritableDatabase();
        mCursor = mDb.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME  ,null);
        mCursor.moveToFirst();

        while ( !mCursor.isAfterLast() ){
            if(mCursor.getString(mCursor.getColumnIndex(DBHelper.COL_ADDRESS)).equals(device.getAddress())) {
                viewHolder.checkb.setText("Installed");
                //viewHolder.checkn.setText(mCursor.getString(mCursor.getColumnIndex(DBHelper.COL_ITEM_NAME)));
            }
            mCursor.moveToNext();
            s++;

        }
        mHelper = new DBHelper(mActivity.getApplicationContext());
        mDb = mHelper.getWritableDatabase();
        mCursor = mDb.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAMEQ  ,null);
        mCursor.moveToFirst();

        while ( !mCursor.isAfterLast() ){
            if(mCursor.getString(mCursor.getColumnIndex(DBHelper.COL_ADDRESSQ)).equals(device.getAddress())) {
                viewHolder.checkb.setText("Installed");
                //viewHolder.checkn.setText(mCursor.getString(mCursor.getColumnIndex(DBHelper.COL_ITEM_NAME)));

            }
            mCursor.moveToNext();
            s++;

        }




        if (deviceName != null && deviceName.length() > 0) {
            viewHolder.deviceName.setText(deviceName);
        } else {
            viewHolder.deviceName.setText(R.string.unknown_device);
        }


        if("54:4A:16:6A:D7:FA".equals(device.getAddress())) {

            viewHolder.deviceName.setText("My Beacon");

        }else if("C2:7A:50:93:02:01".equals(device.getAddress())) {
            viewHolder.deviceName.setText("Blueberry Beacon");
        }else if("E9:6B:2A:B1:6E:28".equals(device.getAddress())) {
            viewHolder.deviceName.setText("Ice Beacon");
        }else if("C9:27:85:88:7E:7E".equals(device.getAddress())) {
            viewHolder.deviceName.setText("Mint Beacon");
        }else
        {

        }


   /*     viewHolder.deviceLastUpdated.setText(
                android.text.format.DateFormat.format(
                        Constants.TIME_FORMAT, new java.util.Date(device.getTimestamp())));
        viewHolder.deviceAddress.setText(device.getAddress());
        viewHolder.deviceRssi.setText(rssiString + " / " + runningAverageRssiString);*/
        return view;
    }


    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
        TextView deviceRssi;
        TextView ibeaconUUID;
        TextView ibeaconMajor;
        TextView ibeaconMinor;
        TextView ibeaconTxPower;
        TextView ibeaconDistance;
        TextView ibeaconDistanceDescriptor;
        TextView deviceLastUpdated;
        View ibeaconSection;
        TextView checkb;
        TextView checkn;
        TextView checkm;
        ImageView deviceIcon;
    }

}