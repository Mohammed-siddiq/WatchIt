package com.watchit.watchsellers.configs;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigReader {
    static Config config = ConfigFactory.load("app.conf");

    public static final String UPLOAD_PATH = config.getString("UPLOAD_PATH");
    public static final String CREDENTIAL_PATH = config.getString("CREDENTIAL_PATH");


}
