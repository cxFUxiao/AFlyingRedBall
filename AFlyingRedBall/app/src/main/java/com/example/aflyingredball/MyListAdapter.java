package com.example.aflyingredball;
//liseview的Adapter

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static com.example.aflyingredball.DataBaseActivity.databaseList;


public class MyListAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    public int AddOne;//  listview上 显示第几次游戏




    public MyListAdapter(Context context){
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return databaseList.size();
    }

    @Override
    public Score getItem(int position ) {return null; }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        public TextView tvTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Score score = databaseList.get(position);
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.layout_list_item,null);//自定义listview布局
            holder = new ViewHolder();

            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_fraction);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //给控件赋值
        AddOne=position+1;
        holder.tvTitle.setText("第"+AddOne+"次："+score.toString()+"个柱子");
       return convertView;
    }


}