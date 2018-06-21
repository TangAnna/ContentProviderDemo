package com.example.tanganan.contentproviderdemo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tanganan.contentproviderdemo.adapter.ContactAdapter;
import com.example.tanganan.contentproviderdemo.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * ContentProvider在联系人中的使用
 */
public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ContactActivity";
    private ListView mListView;
    private TextView mTvName;
    private TextView mTvPhone;
    private static final int REQUEST_CODE = 101;
    /**
     * 手机联系人的Uri
     */
    private Uri mContactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private List<Contact> mData;
    private ContactAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();
        initData();
        setListener();
        setData();
    }

    private void setData() {
        mListView.setAdapter(mAdapter);
    }

    private void setListener() {
        findViewById(R.id.layout_contact_choose).setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initData() {
        mData = new ArrayList<>();
        mAdapter = new ContactAdapter(mData, this);
        getData();

    }

    /**
     * 获取手机联系人信息
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void getData() {
        //TODO 动态获取权限
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(mContactUri, new String[]{}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String display_name = cursor.getString(cursor.getColumnIndex("display_name"));
                String phone = cursor.getString(cursor.getColumnIndex("data1"));
                if (!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(phone)) {
                    Contact contact = new Contact();
                    contact.name = display_name;
                    contact.phone = phone;
                    mData.add(contact);
                }

            } while (cursor.moveToNext());
            mAdapter.notifyDataSetChanged();
        }

    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_contact_list);
        mTvName = (TextView) findViewById(R.id.tv_contact_name);
        mTvPhone = (TextView) findViewById(R.id.tv_contact_phone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_contact_choose:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.PICK");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setType("vnd.android.cursor.dir/phone_v2");
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE://选择联系人
                    Uri uri = data.getData();
                    if (uri != null) {
                        Log.d(TAG, "onActivityResult: " + uri.toString());
                        ContentResolver resolver = getContentResolver();
                        Cursor cursor = resolver.query(uri, new String[]{}, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                String display_name = cursor.getString(cursor.getColumnIndex("display_name"));
                                String phone = cursor.getString(cursor.getColumnIndex("data1"));
                                mTvName.setText(display_name);
                                mTvPhone.setText(phone);

                            } while (cursor.moveToNext());

                        }
                    }

                    break;
            }

        }
    }
}
