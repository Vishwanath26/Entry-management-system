package com.innovaccer.entryManager.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Service
public class SendSmsService implements EnvironmentAware {

    private Logger logger = LoggerFactory.getLogger(SendSmsService.class.getName());
    private static String apiKey ;
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.apiKey = environment.getProperty("smsApiKey");
    }

    public String sendSms(String recipientNumber, String Message) {
        try {
            // Construct data
            String envApiKey = "apikey=" + apiKey;
            String message = "&message=" + Message;
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + recipientNumber;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = envApiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuilder.append(line);
            }
            rd.close();
            logger.info((stringBuilder.toString()));
            return (stringBuilder.toString());
        } catch (Exception e) {
            logger.error("Error SMS " , e);
            return ("Error " + e);
        }
    }
}
