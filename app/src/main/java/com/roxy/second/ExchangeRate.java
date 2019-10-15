package com.roxy.second;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExchangeRate extends AppCompatActivity implements Runnable {

    private final String TAG = "Rate";
    private float dollarRate = 0.1f;
    private float euroRate = 0.2f;
    private float wonRate = 0.3f;
    private String updateDate="";


    EditText input;
    TextView show;
    Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate);

        input = (EditText) findViewById(R.id.input);
        show = (TextView) findViewById(R.id.change_rate);

        //获取SP里保存的数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        //SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);
        dollarRate = sharedPreferences.getFloat("dollar_rate", 0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate", 0.0f);
        wonRate = sharedPreferences.getFloat("won_rate", 0.0f);
        updateDate=sharedPreferences.getString("update_date","");

        //获取当前系统时间
        Date today= Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr=sdf.format(today);
        
        Log.i(TAG, "onCreate:sp dollarRate=" + dollarRate);
        Log.i(TAG, "onCreate:sp euroRate=" + euroRate);
        Log.i(TAG, "onCreate:sp wonRate=" + wonRate);

        Log.i(TAG, "onCreate:sp updateDate="+updateDate);
        Log.i(TAG, "onCreate:sp todatStr="+todayStr);


        //判断时间
        if(todayStr. equals(updateDate)){
            //开启子线程
            Thread t = new Thread(this);
            t.start();

        }else{
            Log.i(TAG, "onCreate: 不需要更新");
        }
       

       
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 5) {

                    Bundle bdl=(Bundle)msg.obj;
                    dollarRate=bdl.getFloat("dollar-rate");
                    euroRate=bdl.getFloat("euro-rate");
                    wonRate=bdl.getFloat("won-rate");

                    Log.i(TAG, "handleMessage: dollarRate"+dollarRate);
                    Log.i(TAG, "handleMessage: euroRate"+euroRate);
                    Log.i(TAG, "handleMessage: wonRate"+wonRate);


                    //保存更新的日期
                    SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("update_date",todayStr);
                    editor.putFloat("dollar_rate", dollarRate);
                    editor.putFloat("euro_rate", euroRate);
                    editor.putFloat("won_rate", wonRate);
                    editor.apply();

                    Toast.makeText(ExchangeRate.this, "汇率已更新", Toast.LENGTH_SHORT).show();

                    /*String str = (String) msg.obj;
                    Log.i(TAG, "handlerMessage:getMessage msg=" + str);
                    show.setText((str));*/
                }
                super.handleMessage(msg);
            }
        };


    }

    public void onClick(View view) {

        String str = input.getText().toString();
        float r = 0;
        if (str.length() > 0) {
            r = Float.parseFloat(str);
        } else {
            //提示用户输入
            Toast.makeText(this, "Please input a number!", Toast.LENGTH_SHORT).show();
        }

        //计算
        float va1 = 0;
        if (view.getId() == R.id.dollar) {
            //va1 = r * (1 / 7.1f);
            va1 = r * (1 / dollarRate);
        } else if (view.getId() == R.id.euro) {
            // va1 = r * (1 / 7.8f);
            va1 = r * (1 / euroRate);
        } else if (view.getId() == R.id.won) {
            // va1 = r * 168;
            va1 = r * wonRate;
        }

        show.setText((String.valueOf(va1)));
        // show.setText(va1+"");
    }

    public void openOne(View btn) {
        Log.i("open", "openOne");
        openConfig();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ratemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_set) {
            openConfig();
        }else if(item.getItemId()==R.id.open_list){

            //打开列表
            Intent list = new Intent(this, MyList2.class);
            startActivity(list);
            //startActivityForResult(config, 1);
        }
        return super.onOptionsItemSelected(item);
    }




    private void openConfig() {
        Intent config = new Intent(this, ConfigActivity.class);
        config.putExtra("dollar_rate_key", dollarRate);
        config.putExtra("euro_rate_key", euroRate);
        config.putExtra("won_rate_key", wonRate);

        Log.i(TAG, "openOne:dollarRate" + dollarRate);
        Log.i(TAG, "openOne:euroRate" + euroRate);
        Log.i(TAG, "openOne:wonRate" + wonRate);


        //startActivity(config);
        startActivityForResult(config, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 2) {

            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar", 0.1f);
            euroRate = bundle.getFloat("key_euro", 0.1f);
            wonRate = bundle.getFloat("key_won", 0.1f);

            Log.i(TAG, "onActivityResult:dollarRate=" + dollarRate);
            Log.i(TAG, "onActivityResult:euroRate=" + euroRate);
            Log.i(TAG, "onActivityResult:wonRate=" + wonRate);

            //把新的汇率写入sp里

            SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("dollar_rate", dollarRate);
            editor.putFloat("euro_rate", euroRate);
            editor.putFloat("won_rate", wonRate);
            editor.commit();

            Log.i(TAG, "onActivity:Result:数据已经保存到sharedPreferences");

        }

        super.onActivityResult(requestCode, requestCode, data);
    }

    @Override
    public void run() {
        Log.i(TAG, "run:run()……");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

         //用于保存获取的汇率
        Bundle bundle;

        //获取网络数据
     /*

        URL url = null;
        try {
            url = new URL("http://www.usd-cny.com/icbc.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
           // String in=http.getInputStream();

            String html=inputStream2String(in);
            Log.i(TAG, "run: html="+html);

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        //bundle中保存获取的汇率
        bundle=getFromBOC();

        //获取Msg对象，用于返回主线程
        Message msg = handler.obtainMessage(5);
        //msg.what=5;
       // msg.obj = "Hello from RUN!";
        msg.obj=bundle;
        handler.sendMessage(msg);
    }


    /**
     *
     * 从bankofchina获取数据
     * @return
     */
    private Bundle getFromBOC() {
        Bundle bundle=new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/icbc.htm").get();
            //doc=Jsoup.parse(html);
            Log.i(TAG, "run:" + doc.title());


            doc.getElementsByTag("table");
            Elements tables = doc.getElementsByTag("table");
            int i = 1;

           /* for(Element table:tables){
                Log.i(TAG, "run: table["+i+"]"+table);
                i++;
            }*/

            Element table6  = tables.get(5);
            Log.i(TAG, "run: table6=" + table6);


            //获取TD中的数据
            Elements tds = table6.getElementsByTag("td");
            for(i=0;i<tds.size();i+=8){
                Element td1=tds.get(i);
                Element td2=tds.get(i+5);
                Log.i(TAG, "run: text="+td1.text()+"==>"+td2.text());

                String str1=td1.text();
                String val=td2.text();


                if("美元".equals((str1))){
                    bundle.putFloat("dollar-rate",100f/Float.parseFloat(val));
                }else if("欧元".equals((str1))){
                    bundle.putFloat("euro-rate",100f/Float.parseFloat(val));
                }else if("韩国元".equals((str1))){
                    bundle.putFloat("won-rate",100f/Float.parseFloat(val));
                }
            }

            for (Element td : tds) {
                Log.i(TAG, "run: td=" + td);
                Log.i(TAG, "run: text="+td.text());
                Log.i(TAG, "run: text="+td.html());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bundle;
    }

    private Bundle getFromUsdCny() {
        Bundle bundle=new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.boc.cn/sourcedb/whpj/").get();
            //doc=Jsoup.parse(html);
            Log.i(TAG, "run:" + doc.title());


            doc.getElementsByTag("table");
            Elements tables = doc.getElementsByTag("table");
            int i = 1;

           /* for(Element table:tables){
                Log.i(TAG, "run: table["+i+"]"+table);
                i++;
            }*/

            Element table6  = tables.get(5);
            Log.i(TAG, "run: table6=" + table6);


            //获取TD中的数据
            Elements tds = table6.getElementsByTag("td");
            for(i=0;i<tds.size();i+=8){
                Element td1=tds.get(i);
                Element td2=tds.get(i+5);
                Log.i(TAG, "run: text="+td1.text()+"==>"+td2.text());

                String str1=td1.text();
                String val=td2.text();


                if("美元".equals((str1))){
                    bundle.putFloat("dollar-rate",100f/Float.parseFloat(val));
                }else if("欧元".equals((str1))){
                    bundle.putFloat("euro-rate",100f/Float.parseFloat(val));
                }else if("韩国元".equals((str1))){
                    bundle.putFloat("won-rate",100f/Float.parseFloat(val));
                }
            }

            for (Element td : tds) {
                Log.i(TAG, "run: td=" + td);
                Log.i(TAG, "run: text="+td.text());
                Log.i(TAG, "run: text="+td.html());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bundle;
    }


    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuilder out=new StringBuilder();
        Reader in=new InputStreamReader(inputStream,"gb2312");
        for(;;){
            int rsz =in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);

        }
        return out.toString();
    }



}


