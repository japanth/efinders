package uk.co.alt236.btlescan.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uk.co.alt236.btlescan.R;

public class ShowbeaconAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Integer> cstatus;
   ArrayList<String> strName;

    public ShowbeaconAdapter (Context context, ArrayList<String> strName ,ArrayList<Integer> status){
        this.mContext= context;
        this.strName = strName;
        this.cstatus = status;

    }

    public int getCount() {
        return strName.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.list_item_view_showbeacon, parent, false);



        TextView textView = (TextView)view.findViewById(R.id.textView1);
        final String name = strName.get(position);
        textView.setText(name);

        ImageView status = (ImageView)view.findViewById(R.id.btn_delete);
        int numstatus = cstatus.get(position);
        if(numstatus==0){
            status.setVisibility(View.VISIBLE);
        }
        else if(numstatus==8) {
            status.setVisibility(View.GONE);
        }

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.deletebeacon(mContext,name);
                ShowallinfoActivity.checkupdate();
            }
        });

        return view;
    }
}
