package com.fmtech.materialdesign;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getDataFromServer(String urlStr){
        try {
            URL url = new URL(urlStr);//获取URL对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();//通过URL对象获得一个URLConnection对象；
            connection.setConnectTimeout(5000);//设置连接主机超时
            connection.setReadTimeout(5000);//设置从主机读取数据超时
            connection.setRequestMethod("GET");//设置请求的方法
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");//设置浏览器类型
            //2XX请求成功；3XX资源重定向；4XX资源找不到；5XX服务器错误
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {//获取服务器返回的状态码
                StringBuffer stringBuffer = new StringBuffer();
                //获取服务器返回的流
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuffer.append(str);
                }
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void doPost(String urlStr, String username, String password){
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");//设置请求方式为POST
            connection.setConnectTimeout(5000);//设置连接超时
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型
            String data ="username="+ URLEncoder.encode(username, "utf-8")+"&password="+URLEncoder.encode(password, "utf-8");
            connection.setRequestProperty("Content-Length", String.valueOf(data.length()));
            //post的请求是把数据以流的方式写给了服务器
            //指定请求的输出模式
            connection.setDoOutput(true);
            //运行当前的应用程序给服务器写数据
            connection.getOutputStream().write(data.getBytes());
            int code = connection.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK){
                InputStream is = connection.getInputStream();
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len = -1;
                while((len = is.read(buffer))!=-1){
                    baos.write(buffer, 0 , len);
                }
                is.close();
                System.out.println(baos.toString("utf-8"));
                baos.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void doHttpClientGet(String url){
        try {
            //打开浏览器
            HttpClient httpClient = new DefaultHttpClient();
            //设置数据
            HttpGet httpGet = new HttpGet(url);
            //发送请求
            HttpResponse httpResponse = httpClient.execute(httpGet);
            int code = httpResponse.getStatusLine().getStatusCode();
            if(code == HttpStatus.SC_OK){
                InputStream is = httpResponse.getEntity().getContent();
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len = -1;
                while((len = is.read(buffer))!=-1){
                    baos.write(buffer, 0 , len);
                }
                is.close();
                System.out.println(baos.toString("utf-8"));
                baos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doHttpClientPost(String url,String username, String password){
        try {
            //1、打开浏览器
            HttpClient httpClient = new DefaultHttpClient();
            //2、设置数据
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            //3、发送请求
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int code = httpResponse.getStatusLine().getStatusCode();
            if(code == HttpStatus.SC_OK){
                InputStream is = httpResponse.getEntity().getContent();
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len = -1;
                while((len = is.read(buffer))!=-1){
                    baos.write(buffer, 0 , len);
                }
                is.close();
                System.out.println(baos.toString("utf-8"));
                baos.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doAsyncHttpClient(String url,String username, String password){
        //1.打开浏览器；支持异步的浏览器，自动在后台开启线程，发送网络请求
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        //2.设置请求参数
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        //3.发送请求
        asyncHttpClient.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(MainActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
