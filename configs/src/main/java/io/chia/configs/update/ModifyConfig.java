package io.chia.configs.update;


import io.chia.configs.model.ChiaKey;
import io.chia.configs.utils.ConfigUtils;
import io.chia.configs.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ModifyConfig {

    public static void main(String[] args) throws Exception {
        System.out.println("初始化开始...");
        String configPath =  ConfigUtils.getFileRootPath();
        System.out.println("...........................................................................................................");
        System.out.println("...........................................初始化中..........................................................");
        System.out.println("...........................................................................................................");
        try {
            Boolean load = ChangeConfigInfo.queryFullNodeAndAddress();
            if (!load){
                System.out.println("网络加载失败,请重试!");
                ModifyConfig.pause();
                return;
            }
            ChangeConfigInfo.change(configPath);
            List<ChiaKey> keys = ConfigUtils.getUserChiaKeyFromCmd();
            FileUtils.writeFileFromKeyInfo(keys);
            System.out.println("指纹Key生成结束！");
            System.out.println("说明:\n1.请登录网站后完成绑定，指纹Key请到桌面FingerPrintKey.txt文件查看");
            System.out.println("2.绑定成功后，启动CHIA官方客户端即可参与矿池挖矿");
            System.out.println("\n\n\n");
            System.out.println("请输入任意字符并回车退出");
            ModifyConfig.pause();
        } catch (Exception e) {
            FileUtils.writeFileFromKeyInfo(null);
            e.printStackTrace();
        }
    }

    public static void pause() throws IOException {
        InputStream inputStream = System.in;
        while (true){
            if (inputStream.read()>=0){
                break;
            }
        }
    }

}



