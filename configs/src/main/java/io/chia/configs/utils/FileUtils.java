package io.chia.configs.utils;

import io.chia.configs.model.ChiaKey;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.List;

public class FileUtils {

    public static void writeFileFromKeyInfo(List<ChiaKey> keys) throws Exception {
        String signKeys = SignUtils.signKeys(keys);
        String filePath = getJarDir()+"\\FingerPrintKey.txt";
        StringBuilder sb = new StringBuilder();
        if (keys!=null&&keys.size()>0) {
            sb.append("您的指纹Key:");
            sb.append(signKeys);
            sb.append("\n\n");
            sb.append("指纹Key使用以下信息生成:\n\n");
            for (ChiaKey chiaKey : keys) {
                sb.append("Fingerprint:" + chiaKey.getFingerprint() + "\n");
                sb.append("First wallet address:" + chiaKey.getAddress() + "\n");
                sb.append("Farmer public key:" + chiaKey.getFarmerPublicKey() + "\n");
                sb.append("Pool public key:" + chiaKey.getPoolPublicKey() + "\n");
                sb.append("\n\n");
            }
        } else {
            sb.append("未查询到指纹信息，请确定已经安装CHIA官方程序，并且完成创建或导入钱包");
        }
        BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(filePath,false),"utf-8"));
        writer.write(sb.toString());
        writer.close();
        writer.close();
    }

    private static String getJarDir(){
        File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        return desktopPath;
    }

}
