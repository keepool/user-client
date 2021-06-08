package io.chia.configs.update;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.chia.configs.constants.Constants;
import io.chia.configs.model.CommonResp;
import io.chia.configs.model.FullNodeRewardAddressRespVO;
import io.chia.configs.model.FullNoteConfigVO;
import io.chia.configs.utils.ConfigUtils;
import io.chia.configs.utils.HttpClientUtils;
import io.chia.configs.utils.MapUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

/**
 * @author XXX
 */
public class ChangeConfigInfo {

    private static Map<String, String> CHANGE_KEYS = new HashMap<>();
    private static Map<String, String> VALUE = new HashMap<>();
    private static String API_HOST_NAME = "https://www.xxx.com";
    static {
        CHANGE_KEYS.put("pooTargetAddress", "pool:xch_target_address");
        CHANGE_KEYS.put("farmerTargetAddress", "farmer:xch_target_address");
        VALUE.put("pooTargetAddress", "xch1kpztskupahmusun8s6a3jal45e0uaq32jtzl942z6r3x6d9s7rxqggrh7l");
        VALUE.put("farmerTargetAddress", "xch1kpztskupahmusun8s6a3jal45e0uaq32jtzl942z6r3x6d9s7rxqggrh7l");
    }

    public static Boolean queryFullNodeAndAddress(){
        String body =  HttpClientUtils.postObject(API_HOST_NAME+"/app/fullnode/configs/query/fullNodeRewardAddress",null);
        if (body==null){
            return false;
        }
        CommonResp<FullNodeRewardAddressRespVO> result = JSON.parseObject(
                body, new TypeReference<CommonResp<FullNodeRewardAddressRespVO>>(FullNodeRewardAddressRespVO.class){});
        if (result.isSuccess()){
            if (result.getData()==null||isEmpty(result.getData().getFarmerTargetAddress())
                    ||isEmpty(result.getData().getPooTargetAddress())){
                return false;
            }
            VALUE.put("farmerTargetAddress",result.getData().getFarmerTargetAddress());
            VALUE.put("pooTargetAddress",result.getData().getPooTargetAddress());
            List<FullNoteConfigVO> fullNoteConfigVOS = result.getData().getFarmerFullNodeHost();
            if (fullNoteConfigVOS.size()>0){
                VALUE.put("farmerFullNodeHost",fullNoteConfigVOS.get(0).getFullNodeIp());
            }
            return true;
        }
        return false;
    }

    private static boolean isEmpty(String content){
        if (content==null||content.length()==0){
            return true;
        }
        return false;
    }

    public static void change(String path) throws Exception {
        copyConfig(path);
        InputStream resource = new FileInputStream(path);
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(dumperOptions);
        LinkedHashMap<?, ?> map = yaml.loadAs(resource, LinkedHashMap.class);
        CHANGE_KEYS.forEach((key, value) -> changeValue(map, value.split(":"), VALUE.get(key)));
        Writer writer = new FileWriter(new File(path));
        yaml.dump(map, writer);
    }

    private static void copyConfig(String path) throws IOException {
        String newFilePath = ConfigUtils.getFileRoot()+Constants.CHIA_CONFIG_COPY_PATH+Constants.CHIA_CONFIG_FILE_NAME+"."+System.currentTimeMillis();
        copy(path,newFilePath,100);
    }

    public static void copy(String source, String dest, int bufferSize) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(new File(source));
            out = new FileOutputStream(new File(dest));

            byte[] buffer = new byte[bufferSize];
            int len;

            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            System.out.println("系统找不到指定的文件");
        } finally {
            if (in!=null) {
                in.close();
            }
            if (out!=null) {
                out.close();
            }
        }
    }

    /**
     * 切换value
     * @param jsonObject -
     * @param keys -
     * @param value -
     */
    private static void changeValue(LinkedHashMap<?, ?> jsonObject, String[] keys, String value) {
        Object obj = jsonObject;
        for (int i = 0, len = keys.length - 1; i < len; i++) {
            obj = MapUtils.invokeGet(obj, keys[i]);
        }
        MapUtils.invokePut(obj, keys[keys.length - 1], value);
    }
}
