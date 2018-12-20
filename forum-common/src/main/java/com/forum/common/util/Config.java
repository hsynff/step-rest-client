package com.forum.common.util;

import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(Config.class.getClassLoader().getResourceAsStream("conf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Config(){

    }

    private static final String IMAGE_UPLOAD_PATH = "image.upload.path";
    private static final String FORUM_SERVICE_HOST = "forum.service.host";


    public static String getImageUploadPath(){
        return properties.getProperty(IMAGE_UPLOAD_PATH);
    }

    public static String getForumServiceHost(){
        return properties.getProperty(FORUM_SERVICE_HOST);
    }




}
