package com.roxy.second;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MyList2 extends ListActivity {

    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;//存放文字和图片信息
    private SimpleAdapter listItemAdapter;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.list_item);

        initListView();
        this.setListAdapter(listItemAdapter);

        MyAdapter myAdapter=new MyAdapter(this,R.layout.list_item,listItems);
        this.setListAdapter(myAdapter);
    }


    private void initListView(){
        listItems=new ArrayList<HashMap<String, String>>();
        for(int i=0;i<10;i++){
            HashMap<String,String> map=new HashMap<String, String>();
            map.put("ItemTitle","Rate:"+i);//标题文字
            map.put("ItemDetail","detail"+i);//详情描述
            listItems.add(map);
        }

        //生成适配器Item和动态数组对应的元素
        listItemAdapter=new SimpleAdapter(this,listItems,//listItems数据源
        R.layout.list_item,//ListItem的XML布局实现
        new String[]{"ItemTitle","ItemDetail"},
        new int[]{R.id.itemTitle,R.id.itemDetail}
        );
    }
}


