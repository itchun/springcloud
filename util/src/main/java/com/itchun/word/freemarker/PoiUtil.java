package com.itchun.word.freemarker;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * Created by hzm on 2019/6/26
 */
@Slf4j
public final class PoiUtil {

    /**
     * 根据url取poi模板
     *
     * @param urlPath 模板url
     */
    public static XWPFTemplate getTemplate(String urlPath, Configure configure, boolean need_http) {
        if (StringUtils.isEmpty(urlPath)) {
            throw new RuntimeException(" url is empty ");
        }
        XWPFTemplate template = null;
        InputStream inputStream = null;
        try {
            inputStream = getInputStream(urlPath, need_http);
            if (null == configure) {
                template = XWPFTemplate.compile(inputStream);
            } else {
                template = XWPFTemplate.compile(inputStream, configure);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return template;
    }

    /**
     * 根据url从服务器获取一个输入流
     *
     * @param urlPath
     */
    private static InputStream getInputStream(String urlPath, boolean need_http) {
        InputStream inputStream = null;
        if (need_http) {
            HttpURLConnection httpURLConnection;
            try {
                URL url = new URL(urlPath);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(3000);//设置连接超时
                httpURLConnection.setDoInput(true);//设置应用程序要从网络连接读取数据
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    //接收服务器返回的流
                    inputStream = httpURLConnection.getInputStream();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                inputStream = new FileInputStream(urlPath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return inputStream;
    }

    /**
     * 根据doc模板和数据输出到文件流生成新文档
     *
     * @param template doc模板
     * @param outFile  输出地址
     * @param dataMap  模板参数
     */
    public static void writeByTemplate(XWPFTemplate template, String outFile, Map<String, Object> dataMap) {
        //输出流
        File wordFile = new File(outFile);
        if (!wordFile.getParentFile().exists()) {
            wordFile.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        try {
            if (!wordFile.exists()) {
                wordFile.createNewFile();
            }
            fos = new FileOutputStream(wordFile);
            //输出到文件流
            template.render(dataMap).write(fos);
            fos.flush();
        } catch (Exception e) {

        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (null != template) {
                    template.close();
                }
            } catch (IOException e) {
                log.info("报告生成异常:" + e.getStackTrace());
            }
        }
    }

    /**
     * 根据doc模板和数据输出到文件
     *
     * @param template doc模板
     * @param outFile  输出地址
     * @param dataMap  模板参数
     */
    public static void writeToFileByTemplate(XWPFTemplate template, String outFile, Map<String, Object> dataMap) {
        try {
            //输出到文件
            template.render(dataMap).writeToFile(outFile);
            template.close();
        } catch (Exception e) {
            log.info("报告生成异常:" + e.getStackTrace());
        }
    }
}