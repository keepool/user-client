package io.chia.configs.model;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
@Data
public class CommonResp<T> extends BaseResp {

    private static final String SUCCESS_RESP_CODE = "000000";
    private static final String SUCCESS_MSG = "success";

    private String code;

    private String msg;

    private T data;


    private boolean success;

    private String traceId;

    public CommonResp() {
    }

    private CommonResp(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private CommonResp(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static CommonResp<Void> of(RespCode code) {
        return new CommonResp<>(code.getCode(), code.getMsg());
    }


    public static <C> CommonResp<C> of(RespCode code, C data) {
        return new CommonResp<>(code.getCode(), code.getMsg(), data);
    }

    public static <C> CommonResp<C> of(RespCode code, String extMsg, C data) {
        return new CommonResp<>(code.getCode(), buildMsg(code, extMsg), data);
    }

    public static <C> CommonResp<C> of(String code, String msg, String extMsg, C data) {
        return new CommonResp<>(code, buildMsg(msg, extMsg), data);
    }

    public static <C> CommonResp<C> of(String code, String msg, C data) {
        return new CommonResp<>(code, msg, data);
    }

    public static <C> CommonResp<C> success(C data) {
        CommonResp<C> resp = new CommonResp<>(SUCCESS_RESP_CODE, SUCCESS_MSG);
        resp.setData(data);
        return resp;
    }

    public static CommonResp success() {
        CommonResp resp = new CommonResp<>(SUCCESS_RESP_CODE, SUCCESS_MSG);
        return resp;
    }

    private static String buildMsg(RespCode code, String extMsg) {
        return code.getMsg() + ". " + StringUtils.defaultString(extMsg);
    }

    private static String buildMsg(String msg, String extMsg) {
        return msg + ". " + StringUtils.defaultString(extMsg);
    }

    public boolean isSuccess() {
        return SUCCESS_RESP_CODE.equals(this.code);
    }

    public static Object badGateway() {
        return null;
    }
}
