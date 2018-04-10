package com.popo.szuspider;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by lgp on 2018/4/10.
 */

public class GetTask extends AsyncTask<Void,Void,Void>{
    StringBuffer stringBuffer=null;
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url=new URL("https://authserver.szu.edu.cn/authserver/login");
            HttpsURLConnection connection=(HttpsURLConnection)url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connection.setRequestProperty("Accept-Encoding","deflate, br");
            connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9,zh-TW;q=0.8");
            connection.setRequestProperty("Connection","keep-alive");
            connection.setRequestProperty("Host","authserver.szu.edu.cn");
            connection.setRequestProperty("Referer","https://authserver.szu.edu.cn/authserver/logout?service=/authserver/login");
            connection.setRequestProperty("Upgrade-Insecure-Requests","1");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");

            connection.connect();
            if(connection.getResponseCode()==200){
                Log.d("hhh","OK");
            }

            Map<String, List<String>> responseHeaderMap = connection.getHeaderFields();
            int size = responseHeaderMap.size();
            StringBuilder sbResponseHeader = new StringBuilder();
            for(int i = 0; i < size; i++){
                String responseHeaderKey = connection.getHeaderFieldKey(i);
                String responseHeaderValue = connection.getHeaderField(i);
                sbResponseHeader.append(responseHeaderKey);
                sbResponseHeader.append(":");
                sbResponseHeader.append(responseHeaderValue);
                sbResponseHeader.append("\n");
            }
            Log.d("hhh",sbResponseHeader.toString());

            InputStreamReader inputStream =new InputStreamReader(connection.getInputStream(),"UTF-8");
            BufferedReader reader=new BufferedReader(inputStream);
            String lines;
            stringBuffer=new StringBuffer("");
            while ((lines=reader.readLine())!=null){
                lines=new String(lines.getBytes(),"utf-8");
                //Log.d("hhh",lines);
                stringBuffer.append(lines);
            }
            reader.close();

            connection.disconnect();


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d("hhh",stringBuffer.toString());
    }
}
