package io.chia.configs.utils;

import io.chia.configs.constants.Constants;
import io.chia.configs.model.ChiaKey;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class ConfigUtils {


    public static String getFileRootPath(){
        String userRootPath = "";
        Properties properties = System.getProperties();
        Enumeration<?> keys = properties.propertyNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement().toString();
            if (key.equals("user.home")){
                userRootPath = properties.getProperty(key);
                break;
            }
        }
        return userRootPath+ Constants.CHIA_CONFIG_PATH;
    }

    public static String getFileRoot(){
        String userRootPath = "";
        Properties properties = System.getProperties();
        Enumeration<?> keys = properties.propertyNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement().toString();
            if (key.equals("user.home")){
                userRootPath = properties.getProperty(key);
                break;
            }
        }
        return userRootPath;
    }

    public static List<ChiaKey> getUserChiaKeyFromCmd() throws Exception {
        List<String> keys = new ArrayList<>();
        String cmds = Constants.CHIA_GET_KEY_CMD;
        Process process = Runtime.getRuntime().exec(cmds);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            keys.add(line);
        }
        return parseUserChiaKeys(keys);
    }

    private static List<ChiaKey> parseUserChiaKeys(List<String> keys){
        List<ChiaKey> chiaKeys = new ArrayList<>();
        ChiaKey temp = null;
        for (String content:keys){
            if (content.contains(Constants.FINGERPRINT_NAME)){
                temp = null;
                temp = new ChiaKey();
                temp.setFingerprint(getValue(content));
                chiaKeys.add(temp);
            }
            if (content.contains(Constants.FARMER_PUBLIC_KEY_NAME)){
                temp.setFarmerPublicKey(getValue(content));
            }
            if (content.contains(Constants.FIRST_WALLET_ADDRESS_NAME)){
                temp.setAddress(getValue(content));
            }
            if (content.contains(Constants.POOL_PUBLIC_KEY_NAME)){
                temp.setPoolPublicKey(getValue(content));
            }
        }

        return chiaKeys;
    }

    private static String getValue(String content){
        String [] key = content.split(":");
        if (key!=null && key.length==2){
            return key[1];
        }
        return null;
    }

}
