package com.miyuki.firstapp.schedulebook;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

import io.realm.Realm;

public class InputActivity extends AppCompatActivity {

    private Realm mRealm;
    private Long mId;
    private TextView mDate;
    private EditText mTitle;
    private EditText mDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        mRealm = Realm.getDefaultInstance();
        mDate = (TextView) findViewById(R.id.date);
        mTitle = (EditText) findViewById(R.id.title);
        mDetail = (EditText) findViewById(R.id.detail);

        if (getIntent() != null){
            mId = getIntent().getLongExtra("ID",-1);
            Schedule schedule = mRealm.where(Schedule.class)
                    .equalTo("id",mId).findFirst();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formatDate = sdf.format(schedule.date);
            mDate.setText(formatDate);
            mTitle.setText(schedule.title);
            mDetail.setText(schedule.detail);
        }
        mTitle.addTextChangedListener(new TextWatcher() {
            //EditTextで編集を始めた時に呼ばれる。操作前のEditTextの状態を取得できる
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            //操作中のEditTextの状態を取得できる。一文字入力するたびに呼ばれる。
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            //操作後のEditTextの状態を取得できる。入力が終了し入力フィールドを離れた時に呼ばれる
            @Override
            public void afterTextChanged(final Editable editable) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Schedule diary = realm.where(Schedule.class).equalTo("id",mId).findFirst();
                        diary.title = editable.toString();
                    }
                });
            }
        });
        mDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Schedule diary = realm.where(Schedule.class).equalTo("id",mId).findFirst();
                        diary.title = editable.toString();
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}