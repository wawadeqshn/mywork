package com.zetyun.mywork.common;

public enum ResultCode {

    SUCCESS(0, "成功"),

    // rest 请求错误码
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_QUERY_TYPE_WRONG(10002, "请求类型错误！"),

    //系统错误码
    SENSEDEAL_QUESTIONED(80001, "系统开了个小差，请稍后重试！");

    private Integer code;
    private String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        ResultCode[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ResultCode item = var1[var3];
            if (item.name().equals(name)) {
                return item.message;
            }
        }

        return name;
    }

    public static Integer getCode(String name) {
        ResultCode[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ResultCode item = var1[var3];
            if (item.name().equals(name)) {
                return item.code;
            }
        }

        return null;
    }

    public String toString() {
        return this.name();
    }
}
