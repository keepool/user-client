package io.chia.configs.utils;

import com.alibaba.fastjson.JSON;
import io.chia.configs.model.ChiaKey;
import io.chia.configs.model.ChiaKeyResp;

import java.util.List;

public class SignUtils {

    public static String signKeys(List<ChiaKey> keys) throws Exception {
        ChiaKeyResp chiaKeyResp = new ChiaKeyResp();
        chiaKeyResp.setData(keys);
        String jsonStr = JSON.toJSONString(chiaKeyResp);
        return AesPeculiarUtil.encode(jsonStr);
    }

}
