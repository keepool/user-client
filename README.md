## 功能说明

- 修改用户的收益地址指向矿池收益地址
- 将用户的pool_public_key, wallet_address等信息签名，用于矿池用户绑定

## 核心代码

> 入口类：io.chia.config.update.ModifyConfig

<br>

> 获取配置类：io.chia.configs.utils.ConfigUtils  
  
  代码片段:

```java
private static List<ChiaKey> parseUserChiaKeys(List<String> keys){
        List<ChiaKey> chiaKeys = new ArrayList<>();
        ChiaKey temp = null;
        for (String content:keys){
            // 获取指纹信息
            if (content.contains(Constants.FINGERPRINT_NAME)){
                temp = null;
                temp = new ChiaKey();
                temp.setFingerprint(getValue(content));
                chiaKeys.add(temp);
            }
            // 获取农民公钥
            if (content.contains(Constants.FARMER_PUBLIC_KEY_NAME)){
                temp.setFarmerPublicKey(getValue(content));
            }
            // 获取钱包地址
            if (content.contains(Constants.FIRST_WALLET_ADDRESS_NAME)){
                temp.setAddress(getValue(content));
            }
            // 获取pool_public_key
            if (content.contains(Constants.POOL_PUBLIC_KEY_NAME)){
                temp.setPoolPublicKey(getValue(content));
            }
        }

        return chiaKeys;
    }
```
