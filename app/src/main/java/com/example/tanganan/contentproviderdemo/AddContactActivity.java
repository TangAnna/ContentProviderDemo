package com.example.tanganan.contentproviderdemo;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 添加联系人
 */
public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddContactActivity";
    private EditText mEtName, mEtPhone;
    private Uri mUri = Uri.parse("content://com.android.contacts/data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        findViewById(R.id.tv_add).setOnClickListener(this);
        mEtName = (EditText) findViewById(R.id.tv_add_contact_name);
        mEtPhone = (EditText) findViewById(R.id.tv_add_contact_phone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                String name = mEtName.getText().toString().trim();
                String phone = mEtPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)) {
                    testInsert(name, phone);
                } else {
                    Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 添加联系人的第一种方法：
     * 首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
     * 这时后面插入data表的依据，只有执行空值插入，才能使插入的联系人在通讯录里面可见
     */
    public void testInsert(String name, String phone) {
        ContentValues values = new ContentValues();
        //首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        Log.d(TAG, "==rawContactUri: " + rawContactUri.toString());
        //获取id
        long rawContactId = ContentUris.parseId(rawContactUri);
        //往data表入姓名数据
        values.clear();
        values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId); //添加id
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);//添加内容类型（MIMETYPE）
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);//添加名字，添加到first name位置
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        Log.d(TAG, "==rawContactUri姓名: " + ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME.toString());
        Log.d(TAG, "==rawContactUri姓名: " + values.toString());

        //往data表入电话数据
        values.clear();
        values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);//raw_contact_id字段是必要参数
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        //往data表入Email数据
//        values.clear();
//        values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
//        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
//        values.put(ContactsContract.CommonDataKinds.Email.DATA, "kesenhoo@gmail.com");
//        values.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
//        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        Toast.makeText(this, "添加成功！", Toast.LENGTH_SHORT).show();
    }

//    /**添加联系人的第二种方法：
//     * 批量添加联系人
//     * @throws Throwable
//     */
//    public void testSave() throws Throwable
//    {
//        //官方文档位置：reference\android\provider\ContactsContract.RawContacts.html
//        //建立一个ArrayList存放批量的参数
//        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//        int rawContactInsertIndex = ops.size();
//        ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
//                .withValue(RawContacts.ACCOUNT_TYPE, null)
//                .withValue(RawContacts.ACCOUNT_NAME, null)
//                .build());
//        //官方文档位置：reference\android\provider\ContactsContract.Data.html
//        //withValueBackReference后退引用前面联系人的id
//        ops.add(ContentProviderOperation.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
//                .withValue(StructuredName.GIVEN_NAME, "小明")
//                .build());
//        ops.add(ContentProviderOperation.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
//                .withValue(Phone.NUMBER, "13671323809")
//                .withValue(Phone.TYPE, Phone.TYPE_MOBILE)
//                .withValue(Phone.LABEL, "手机号")
//                .build());
//        ops.add(ContentProviderOperation.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                .withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
//                .withValue(Email.DATA, "kesen@gmail.com")
//                .withValue(Email.TYPE, Email.TYPE_WORK)
//                .build());
//        ContentProviderResult[] results = this.getContext().getContentResolver()
//                .applyBatch(ContactsContract.AUTHORITY, ops);
//        for(ContentProviderResult result : results)
//        {
//            Log.i(TAG, result.uri.toString());
//        }
//    }

}
