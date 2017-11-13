package org.geobricks.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Config instance = null;
    private final String configFilePath = "./conf.txt";

    public String mainDbDns;
    public String logDbDns;
    public String queueDns;

    protected Config() {
        readFile();
    }

    public static Config getInstance() {
        if(instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private void readFile() {

        FileOutputStream output;
        Properties prop = new Properties();

        try {

            FileInputStream stream = new FileInputStream(configFilePath);
            prop.load(stream);

            mainDbDns = prop.getProperty("main_db_dns");
            logDbDns = prop.getProperty("log_db_dns");
            queueDns = prop.getProperty("queue_dns");

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
