package com.innovaccer.entryManager.Service.MessageService;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class SmsService implements EnvironmentAware {

    private Environment environment;

    private static String smsApiKey;

    private static String smsSecretKey;

    private static String url;

    private static String senderId = "vishwanath.sharma724@gmail.com";

    private static Logger logger = LoggerFactory.getLogger(SmsService.class.getName());

    public static String getSmsApiKey() {
        return smsApiKey;
    }

    public static String getSmsSecretKey() {
        return smsSecretKey;
    }

    public static String getUrl() {
        return url;
    }

    @Override
    public void setEnvironment(Environment environment)
    {
        this.environment = environment;
        this.smsApiKey = environment.getProperty("smsApiKey");
        this.smsSecretKey = environment.getProperty("smsSecretKey");
        this.url = environment.getProperty("smsUrl");
    }

    /**
     * send sms as account configured using way2sms
     * @param phoneNumber
     * @param message
     * @return
     */
    public String sendSms(String phoneNumber , String message)
    {
        try {
            JSONObject urlParameters = new JSONObject();
            urlParameters.put("apikey", getSmsApiKey());
            urlParameters.put("secret", getSmsSecretKey());
            urlParameters.put("usetype", "prod");
            urlParameters.put("phone", phoneNumber);
            urlParameters.put("message", URLEncoder.encode(message, "UTF-8"));
            urlParameters.put("senderid", senderId);
            URL obj = new URL(url + "/api/v1/sendCampaign");

            // send data
            HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());

            wr.write(urlParameters.toString().getBytes());
            // get the response
            BufferedReader bufferedReader = null;
            if (httpConnection.getResponseCode() == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
            return content.toString();
        }
        catch (Exception ex)
        {
            logger.error("error in sms sending " , ex);
            return "error in sms sending";
        }
    }
}
