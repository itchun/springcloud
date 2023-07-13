package com.itchun.word.freemarker;

import freemarker.cache.URLTemplateLoader;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteTemplateLoader extends URLTemplateLoader {

    private String urlPath;

    public RemoteTemplateLoader(String urlPath) {
        this.urlPath = urlPath;
    }

    @Override
    protected URL getURL(String path) {
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
