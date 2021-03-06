package uk.co.alt236.btlescan.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import uk.co.alt236.btlescan.R;

public class ShowlistroomActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static SQLiteDatabase mDb;
    static DBHelper mHelper;
    static Cursor mCursor;

    static final ArrayList<String> dirArray = new ArrayList<String>();
    static final ArrayList<Integer> status = new ArrayList<Integer>();
    static ShowbeaconAdapter adapterDir ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlistroom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(ShowlistroomActivity.this,AddbeaconActivity.class);
                startActivity(add);
            }
        });




        ListView listView = (ListView) findViewById(R.id.listView1);
        mHelper = new DBHelper(this);
        mDb = mHelper.getWritableDatabase();
        mCursor = mDb.rawQuery("SELECT "+DBHelper.COL_ITEM_NAMEQ +" FROM " + DBHelper.TABLE_NAMEQ  ,null);

        mCursor.moveToFirst();
        dirArray.clear();
        status.clear();
        while ( !mCursor.isAfterLast() ){
            dirArray.add( mCursor.getString(mCursor.getColumnIndex(DBHelper.COL_ITEM_NAMEQ)));
            status.add(View.GONE);
            mCursor.moveToNext();
        }

        adapterDir = new ShowbeaconAdapter (getApplicationContext(),dirArray,status);
        listView.setAdapter(adapterDir);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ShowlistroomActivity.this, ScanBeaconActivity.class);
                intent.putExtra("name", dirArray.get(position));
                startActivity(intent);
            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
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
        getMenuInflater().inflate(R.menu.showlistbeacon, menu);
        return true;
    }

    Boolean currentstatus=false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            for(int i=0;i<status.size();i++){
                if(currentstatus==false){
                    status.set(i,View.VISIBLE);
                }

                else {
                    status.set(i,View.GONE);
                }
            }

            // set if false then true
            currentstatus = !currentstatus;

            adapterDir.notifyDataSetChanged();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void checkupdate (){
        mCursor  = mDb.rawQuery("SELECT "+DBHelper.COL_ITEM_NAME +" FROM " + DBHelper.TABLE_NAME,null );
        dirArray.clear();
        status.clear();
        mCursor.moveToFirst();
        while ( !mCursor.isAfterLast() ){
            dirArray.add( mCursor.getString(mCursor.getColumnIndex(DBHelper.COL_ITEM_NAME)));
            status.add(View.VISIBLE);
            mCursor.moveToNext();
        }
        adapterDir.notifyDataSetChanged();

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent home = new Intent(ShowlistroomActivity.this,CategoryActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_about) {

            Intent info = new Intent(ShowlistroomActivity.this,ShowInfoActivity.class);
            startActivity(info);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onPause() {
        super.onPause();
        mHelper.close();
        mDb.close();
    }
}
