package com.roxy.second;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RateList extends ListActivity implements Runnable{
    String data[]={"wait..."};

   //
   // String[] list1 = {"One", "Tow", "Three", "Four"};
    private Object handler;
    private final String TAG="rate";

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_rate_list);
        List<String> list1 = new ArrayList<String>();
        for (int i = 1; i <= 100; i++) {
            list1.add("item" + i);
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
        setListAdapter(adapter);

        Thread t=new Thread(this);
        t.start();


        handler = new Handler() {

            public void handleMessage(Message msg) {
                if (msg.what == 7) {
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(RateList.this, android.R.layout.simple_list_item_1, list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void run() {
        List<String> retList=new ArrayList<String>();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG, "run:" + doc.title());

            doc.getElementsByTag("table");
            Elements tables = doc.getElementsByTag("table");
            int i = 1;
            Element table6  = tables.get(5);
            Log.i(TAG, "run: table6=" + table6);

            //获取TD中的数据
            Elements tds = table6.getElementsByTag("td");
            for(i=0;i<tds.size();i+=8){
                Element td1=tds.get(i);
                Element td2=tds.get(i+5);
                String str1=td1.text();
                String val=td2.text();
                Log.i(TAG, "run: text="+td1.text()+"==>"+td2.text());
                retList.add(str1+"==>"+val);

            }

            for (Element td : tds) {
                Log.i(TAG, "run: td=" + td);
                Log.i(TAG, "run: text="+td.text());
                Log.i(TAG, "run: text="+td.html());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
       /* //获取网络数据，放入list带回主线程
        Message msg = handler.obtainMessage(5);
        msg.obj=retList;
        handler.sendMessage(msg);*/
    }
}



