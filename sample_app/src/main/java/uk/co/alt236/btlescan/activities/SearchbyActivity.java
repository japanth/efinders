package uk.co.alt236.btlescan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import uk.co.alt236.btlescan.R;

public class SearchbyActivity extends AppCompatActivity {

    ImageView searchobj,searchroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchby);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchobj = (ImageView) findViewById(R.id.searchby_obj);

        searchroom = (ImageView) findViewById(R.id.searchby_room);
        searchobj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchbyActivity.this,ShowlistbeaconActivity.class);
                startActivity(intent);
            }
        });

        searchroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchbyActivity.this,ShowlistroomActivity.class);
                startActivity(intent);
            }
        });

    }

}
