package com.itchun.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    public static void main(String[] args) throws Exception {
        Map<String,String> map = new HashMap<String,String>(16);
        map.put("access_token","xxxxxxxxxxxx");
        map.put("url","xxxxxxxx");
        map.put("baike_num","xx");
        System.out.println(post("https://aip.baidubce.com/rest/2.0/image-classify/v1/plant",map));
    }

    /**
     *  原生http请求
     * @param sendUrl 请求的Url
     * @param map 传入的参数
     * @return
     */
    public static String post(String sendUrl, Map<String,String> map){
        String result="";
        URL url;
        HttpURLConnection conn;
        try{
            url = new URL(sendUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            //连接主机的超时时间（单位：毫秒）
            conn.setConnectTimeout(10000);
            //从主机读取数据的超时时间（单位：毫秒）
            conn.setReadTimeout(10000);
            //设置请求方式
            conn.setRequestMethod("POST");
            //设置  网络文件的类型和网页的编码
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
            int responseCode = conn.getResponseCode();
            if (responseCode > 200 ){
                StringBuffer resultBuffer = new StringBuffer();
                String tempLine = null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
                while ((tempLine = bufferedReader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
                throw new Exception( "HTTP Request is not success, Response code is " + responseCode + ", And Response message is " + resultBuffer.toString());
            }
            //将数据写入流中
            out.write(mapToString(map));
            //刷新流中的数据
            out.flush();
            //关闭流(关闭之前要先刷新)
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            //读一行文字并返回该行字符
            while ((line = in.readLine()) != null) {
                result += line;
            }
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            url=null;
            conn=null;
        }
        return result;
    }

    /**
     * 将map转String
     * @param map
     * @return
     */
    private static String mapToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (String i:map.keySet()){
            index ++;
            if (map.size() == index){
                sb.append(i+"="+map.get(i));
            }else {
                sb.append(i+"="+map.get(i)+"&");
            }
        }
        return  sb.toString();
    }
}
