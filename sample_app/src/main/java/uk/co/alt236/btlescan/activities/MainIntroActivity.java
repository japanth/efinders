package uk.co.alt236.btlescan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;

import uk.co.alt236.btlescan.R;

/**
 * Created by LiboPz on 9/26/2016.
 */

public class MainIntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SampleSlide.newInstance(R.layout.intro));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));
        addSlide(SampleSlide.newInstance(R.layout.intro4));
        addSlide(new InputDemoSlide());
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);

    }

    public void getStarted(View v) {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }
}
