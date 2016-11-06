package uk.co.alt236.btlescan.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.alt236.btlescan.R;

public class CategoryActivity extends AppCompatActivity {

ImageView addbeacon,addbeaconq,findobj,informa,setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        addbeacon = (ImageView) findViewById(R.id.categor_add);

        addbeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,AddbeaconActivity.class);
                startActivity(intent);

            }
        });


        findobj = (ImageView) findViewById(R.id.category_search);

        findobj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,SearchbyActivity.class);
                startActivity(intent);
            }
        });

        informa = (ImageView) findViewById(R.id.categor_showinfo);

        informa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,ShowInfoActivity.class);
                startActivity(intent);
            }
        });

        setting= (ImageView) findViewById(R.id.category_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,TestImages.class);
                startActivity(intent);
            }
        });
    }
}
