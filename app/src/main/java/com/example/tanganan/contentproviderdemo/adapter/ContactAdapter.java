package com.example.tanganan.contentproviderdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tanganan.contentproviderdemo.R;
import com.example.tanganan.contentproviderdemo.model.Contact;

import java.util.List;

/**
 * Created by TangAnna on 2018/6/20.
 */

public class ContactAdapter extends BaseAdapter {

    private List<Contact> mData;
    private Context mContext;

    public ContactAdapter(List<Contact> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactViewHolder holder = null;
        if (holder == null) {
            convertView = View.inflate(mContext, R.layout.contact_item, null);
            holder = new ContactViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ContactViewHolder) convertView.getTag();
        }
        Contact contact = mData.get(position);
        holder.mTvName.setText(contact.name);
        holder.mTvPhone.setText(contact.phone);
        return convertView;
    }

    class ContactViewHolder {
        TextView mTvName, mTvPhone;

        public ContactViewHolder(View view) {
            mTvName = view.findViewById(R.id.tv_contact_item_name);
            mTvPhone = view.findViewById(R.id.tv_contact_item_phone);

        }
    }
}
