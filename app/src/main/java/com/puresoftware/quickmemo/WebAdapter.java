package com.puresoftware.quickmemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WebAdapter extends BaseAdapter {

    Context context;
    ArrayList<MemberDO> datas = new ArrayList<>();

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        context = viewGroup.getContext();

        // view가 없을때만 만들기
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.web_item_xml, viewGroup, false);
        }

        TextView textview3 = view.findViewById(R.id.tvWebName);
        TextView textView4 = view.findViewById(R.id.tvWebAge);
        TextView textView5 = view.findViewById(R.id.tvWebGender);

        MemberDO data = datas.get(i);
        String name = data.getName();
        int age = data.getAge();
        String gender = data.getGender();

        System.out.println(data.toString());

        textview3.setText(name);
        textView4.setText(age+"");
        textView5.setText(gender);

        return view;
    }

    public void addItem(MemberDO data) {
        datas.add(data);
    }
}
