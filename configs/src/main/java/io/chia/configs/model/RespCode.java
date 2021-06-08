package io.chia.configs.model;


/**
 *
 */
public enum RespCode {
    // 成功：1开头
    SUCCESS("000000", "success"),
    LOGIN_TIMEOUT("100000", "user logout"),
    // 通用错误：2开头
    SYS_ERROR("999999", "system exception"),
    ILLEGAL_ARGUMENT("100006", "arguments exception");

    RespCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static RespCode getByCode(String code) {
        for (RespCode item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public String getMessage() {
        return getClass().getName() + '.' + name();
    }
}