package com.itchun.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommonUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String getSysTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

}
