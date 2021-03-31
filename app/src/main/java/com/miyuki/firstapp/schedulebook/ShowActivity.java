package com.miyuki.firstapp.schedulebook;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.palette.graphics.Palette;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;

import io.realm.Realm;

public class ShowActivity extends AppCompatActivity {

    private Realm mRealm;
    //三つの画像のIDを格納した配列を用意
    private int[] images = {R.drawable.image0, R.drawable.image1,R.drawable.image2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mRealm = Realm.getDefaultInstance();
        ImageView toolbarImage = (ImageView) findViewById(R.id.toolbar_image);
        TextView date = (TextView) findViewById(R.id.date);
        TextView detail = (TextView) findViewById(R.id.detail);
        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroll_view);

        if (getIntent() != null){
            long id = getIntent().getLongExtra("ID", -1);
            Schedule schedule = mRealm.where(Schedule.class).equalTo("id",id).findFirst();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formatDate = sdf.format(schedule.date);
            date.setText(formatDate);
            layout.setTitle(schedule.title);
            detail.setText(schedule.detail);
            toolbarImage.setImageResource(images[(int) id % 3]);

            Bitmap bitmap = ((BitmapDrawable)toolbarImage.getDrawable()).getBitmap();
            Palette palette = Palette.from(bitmap).generate();

            int titleColor = palette.getLightVibrantColor(Color.WHITE);
            int bodyColor = palette.getDarkMutedColor(Color.BLACK);
            int scrimColor = palette.getMutedColor(Color.DKGRAY);
            int iconColor = palette.getVibrantColor(Color.LTGRAY);

            layout.setExpandedTitleColor(titleColor);
            layout.setContentScrimColor(scrimColor);
            scrollView.setBackgroundColor(bodyColor);
            detail.setTextColor(titleColor);
            fab.setBackgroundTintList(ColorStateList.valueOf(iconColor));
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}