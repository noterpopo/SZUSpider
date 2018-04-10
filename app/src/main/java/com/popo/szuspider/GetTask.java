package com.popo.szuspider;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
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
        Map<String,String> cookies=new HashMap<>();
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

            int start=stringBuffer.toString().indexOf("name=\"lt\" value=\"")+17;
            int end=stringBuffer.toString().indexOf("\"",start);
            String lt=stringBuffer.toString().substring(start,end);
            Log.d("hhh",lt);
            String dllt="userNamePasswordLogin";
            start=stringBuffer.indexOf("execution\" value=\"")+18;
            end=stringBuffer.indexOf("\"",start);
            String execution=stringBuffer.substring(start,end);
            String _eventId="submit";
            String rmShown="1";
            start=sbResponseHeader.indexOf("JSESSIONID_auth=")+16;
            end=sbResponseHeader.indexOf(";",start);
            String JSESSIONID_auth=sbResponseHeader.substring(start,end);
            Log.d("hhh",JSESSIONID_auth);
            start=sbResponseHeader.indexOf("route=")+6;
            end=sbResponseHeader.indexOf("\n",start);
            String route=sbResponseHeader.substring(start,end);
            Log.d("hhh",route);
            cookies.put("JSESSIONID_auth",JSESSIONID_auth);
            cookies.put("route",route);
            connection.disconnect();

            //第二次请求
            url=new URL("https://authserver.szu.edu.cn/authserver/login;JSESSIONID_auth="+cookies.get("JSESSIONID_auth"));
            connection=(HttpsURLConnection)url.openConnection();
            connection.setRequestProperty("Cookie","route="+cookies.get("route")+"; JSESSIONID_auth="+cookies.get("JSESSIONID_auth")+"; org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE=zh_CN");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Cache-Control","max-age=0");
            connection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connection.setRequestProperty("Accept-Encoding","deflate, br");
            connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9,zh-TW;q=0.8");
            connection.setRequestProperty("Connection","keep-alive");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Origin","https://authserver.szu.edu.cn");
            connection.setRequestProperty("Referer","https://authserver.szu.edu.cn/authserver/login");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write("username=180403&password=54336qaz55&lt="+lt+"&dllt=userNamePasswordLogin&execution="+execution+"&_eventId=submit&rmShown=1");
            out.flush();
            out.close();
            connection.connect();
            if(connection.getResponseCode()==302){
                Log.d("hhh","OK2");
            }
            responseHeaderMap = connection.getHeaderFields();
            size = responseHeaderMap.size();
            sbResponseHeader = new StringBuilder();
            for(int i = 0; i < size; i++){
                String responseHeaderKey = connection.getHeaderFieldKey(i);
                String responseHeaderValue = connection.getHeaderField(i);
                sbResponseHeader.append(responseHeaderKey);
                sbResponseHeader.append(":");
                sbResponseHeader.append(responseHeaderValue);
                sbResponseHeader.append("\n");
            }
            Log.d("hhh",sbResponseHeader.toString());

            start=sbResponseHeader.indexOf("CASTGC=")+7;
            end=sbResponseHeader.indexOf(";",start);
            String CASTGC=sbResponseHeader.substring(start,end);
            Log.d("hhh",CASTGC);
            start=sbResponseHeader.indexOf("iPlanetDirectoryPro=")+20;
            end=sbResponseHeader.indexOf(";",start);
            String iPlanetDirectoryPro=sbResponseHeader.substring(start,end);
            Log.d("hhh",iPlanetDirectoryPro);
            cookies.put("CASTGC",CASTGC);
            cookies.put("iPlanetDirectoryPro",iPlanetDirectoryPro);
            connection.disconnect();


            //第三次请求
            url=new URL("http://authserver.szu.edu.cn/authserver/index.do");
            HttpURLConnection connection1=(HttpURLConnection)url.openConnection();
            connection1.setRequestProperty("Cookie","CASTGC="+cookies.get("CASTGC")+"; route="+cookies.get("route")+"; JSESSIONID_auth="+cookies.get("JSESSIONID_auth")+"; org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE=zh_CN");
            connection1.setRequestMethod("GET");
            connection1.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connection1.setRequestProperty("Accept-Encoding","deflate");
            connection1.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9,zh-TW;q=0.8");
            connection1.setRequestProperty("Connection","keep-alive");
            connection1.setRequestProperty("Host","authserver.szu.edu.cn");
            connection1.setRequestProperty("Upgrade-Insecure-Requests","1");
            connection1.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            connection1.setRequestProperty("Cache-Control","max-age=0");
            connection1.connect();
            if(connection1.getResponseCode()==302){
                Log.d("hhh","OK3");
            }
            responseHeaderMap = connection1.getHeaderFields();
            size = responseHeaderMap.size();
            sbResponseHeader = new StringBuilder();
            for(int i = 0; i < size; i++){
                String responseHeaderKey = connection1.getHeaderFieldKey(i);
                String responseHeaderValue = connection1.getHeaderField(i);
                sbResponseHeader.append(responseHeaderKey);
                sbResponseHeader.append(":");
                sbResponseHeader.append(responseHeaderValue);
                sbResponseHeader.append("\n");
            }
            Log.d("hhh",sbResponseHeader.toString());
            start=sbResponseHeader.indexOf("insert_cookie=")+14;
            end=sbResponseHeader.indexOf(";",start);
            String insert_cookie=sbResponseHeader.substring(start,end);
            Log.d("hhh",insert_cookie);
            cookies.put("insert_cookie",insert_cookie);
            connection1.disconnect();

            //第四次请求
            url=new URL("https://authserver.szu.edu.cn/authserver/index.do");
            connection=(HttpsURLConnection)url.openConnection();
            connection.setRequestProperty("Cookie","CASTGC="+cookies.get("CASTGC")+"; route="+cookies.get("route")+"; JSESSIONID_auth="+cookies.get("JSESSIONID_auth")+"; org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE=zh_CN; insert_cookie="+cookies.get("insert_cookie"));
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connection.setRequestProperty("Accept-Encoding","deflate, br");
            connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9,zh-TW;q=0.8");
            connection.setRequestProperty("Connection","keep-alive");
            connection.setRequestProperty("Host","authserver.szu.edu.cn");
            connection.setRequestProperty("Upgrade-Insecure-Requests","1");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            connection.setRequestProperty("Cache-Control","max-age=0");
            connection.connect();
            if(connection.getResponseCode()==302){
                Log.d("hhh","OK4");
            }
            responseHeaderMap = connection.getHeaderFields();
            size = responseHeaderMap.size();
            sbResponseHeader = new StringBuilder();
            for(int i = 0; i < size; i++){
                String responseHeaderKey = connection.getHeaderFieldKey(i);
                String responseHeaderValue = connection.getHeaderField(i);
                sbResponseHeader.append(responseHeaderKey);
                sbResponseHeader.append(":");
                sbResponseHeader.append(responseHeaderValue);
                sbResponseHeader.append("\n");
            }
            Log.d("hhh",sbResponseHeader.toString());
            connection.disconnect();

            //第五次请求
            url=new URL("http://authserver.szu.edu.cn/authserver/index.do");
            connection1=(HttpURLConnection)url.openConnection();
            connection1.setRequestProperty("Cookie","CASTGC="+cookies.get("CASTGC")+"; route="+cookies.get("route")+"; JSESSIONID_auth="+cookies.get("JSESSIONID_auth")+"; org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE=zh_CN");
            connection1.setRequestMethod("GET");
            connection1.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connection1.setRequestProperty("Accept-Encoding","deflate");
            connection1.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9,zh-TW;q=0.8");
            connection1.setRequestProperty("Connection","keep-alive");
            connection1.setRequestProperty("Host","authserver.szu.edu.cn");
            connection1.setRequestProperty("Upgrade-Insecure-Requests","1");
            connection1.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            connection1.setRequestProperty("Cache-Control","max-age=0");
            connection1.connect();
            if(connection1.getResponseCode()==302){
                Log.d("hhh","OK5");
            }
            responseHeaderMap = connection1.getHeaderFields();
            size = responseHeaderMap.size();
            sbResponseHeader = new StringBuilder();
            for(int i = 0; i < size; i++){
                String responseHeaderKey = connection1.getHeaderFieldKey(i);
                String responseHeaderValue = connection1.getHeaderField(i);
                sbResponseHeader.append(responseHeaderKey);
                sbResponseHeader.append(":");
                sbResponseHeader.append(responseHeaderValue);
                sbResponseHeader.append("\n");
            }
            Log.d("hhh",sbResponseHeader.toString());
            connection1.disconnect();

            //第六次请求
            url=new URL("https://authserver.szu.edu.cn/authserver/index.do");
            connection=(HttpsURLConnection)url.openConnection();
            connection.setRequestProperty("Cookie","CASTGC="+cookies.get("CASTGC")+"; route="+cookies.get("route")+"; JSESSIONID_auth="+cookies.get("JSESSIONID_auth")+"; org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE=zh_CN; insert_cookie="+cookies.get("insert_cookie"));
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connection.setRequestProperty("Accept-Encoding","deflate, br");
            connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9,zh-TW;q=0.8");
            connection.setRequestProperty("Connection","keep-alive");
            connection.setRequestProperty("Host","authserver.szu.edu.cn");
            connection.setRequestProperty("Upgrade-Insecure-Requests","1");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            connection.setRequestProperty("Cache-Control","max-age=0");
            connection.connect();
            if(connection.getResponseCode()==200){
                Log.d("hhh","OK6");
            }
            responseHeaderMap = connection.getHeaderFields();
            size = responseHeaderMap.size();
            sbResponseHeader = new StringBuilder();
            for(int i = 0; i < size; i++){
                String responseHeaderKey = connection.getHeaderFieldKey(i);
                String responseHeaderValue = connection.getHeaderField(i);
                sbResponseHeader.append(responseHeaderKey);
                sbResponseHeader.append(":");
                sbResponseHeader.append(responseHeaderValue);
                sbResponseHeader.append("\n");
            }
            Log.d("hhh",sbResponseHeader.toString());
            connection.disconnect();

            inputStream =new InputStreamReader(connection.getInputStream(),"UTF-8");
            reader=new BufferedReader(inputStream);
            stringBuffer=new StringBuffer("");
            while ((lines=reader.readLine())!=null){
                lines=new String(lines.getBytes(),"utf-8");
                Log.d("hhh",lines);
                stringBuffer.append(lines);
            }
            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }
}
