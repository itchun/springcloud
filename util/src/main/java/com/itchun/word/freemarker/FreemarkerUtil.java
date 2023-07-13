package com.itchun.word.freemarker;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

public class FreemarkerUtil {

    /**
     * 获取一个模板
     *
     * @param url 模板地址
     * @return
     */
    public static Template getTemplate(String url, boolean need_http) {
        try {
            // 通过Freemarker的Configuration读取相应的ftl
            Configuration configuration = new Configuration();
            // 处理空值
            configuration.setClassicCompatible(true);
            configuration.setDefaultEncoding("UTF-8");
            Template template;
            if (need_http) {
                RemoteTemplateLoader remoteTemplateLoader = new RemoteTemplateLoader(url);
                configuration.setTemplateLoader(remoteTemplateLoader);
                template = configuration.getTemplate(url);
            } else {
                configuration.setObjectWrapper(new DefaultObjectWrapper());
                // "/templates/" 是绝对路径
                // "templates/" 是相对于 FreemarkerUtil.class 的路径
                configuration.setClassForTemplateLoading(FreemarkerUtil.class,"/templates/");
                template = configuration.getTemplate(url);
            }
            return template;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 打印模板内容
     *
     * @param name 模板地址
     * @param root 参数
     */
    public void print(String name, Map<String, Object> root) {
        // 通过Template可以将模版文件输出到相应的文件流
        Template template = this.getTemplate(name, false);
        try {
            template.process(root, new PrintWriter(System.out)); // 在控制台输出内容
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出HTML文件
     *
     * @param name    模板地址
     * @param root    模板参数
     * @param outFile 输出地址
     */
    public void fprint(String name, Map<String, Object> root, String outFile) {
        FileWriter out = null;
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            File file = new File(outFile);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            out = new FileWriter(file);
            Template temp = this.getTemplate(name, false);
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 输出 word 文件
     *
     * @param modeName 模板地址
     * @param outFile  输出地址
     * @param params   模板参数
     */
    public static void createWorldByMode(String modeName, String outFile, Object params) {
        Configuration cfg = new Configuration();
        Writer out = null;
        try {
            // 设置模板路径
            cfg.setDirectoryForTemplateLoading(ResourceUtils.getFile("classpath:templates"));
            cfg.setDefaultEncoding("UTF-8");
            // 处理空值
            cfg.setClassicCompatible(true);
            File file = new File(outFile);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8"); // 设置编码 UTF-8
            Template template = cfg.getTemplate(modeName);
            template.process(params, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据模板创建word文档
     *
     * @param template 模板
     * @param outFile  生成的word文档字符串
     * @param params   模板填充需要的Map数据
     */
    public static void createWordByTemplate(Template template, String outFile, Object params) {
        Writer out = null;
        FileOutputStream fos = null;
        try {
            // 2、输出word
            File wordFile = new File(outFile);
            if (!wordFile.getParentFile().exists()) {
                wordFile.getParentFile().mkdirs();
            }
            if (!wordFile.exists()) {
                wordFile.createNewFile();
            }
            fos = new FileOutputStream(wordFile);
            out = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            template.process(params, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
