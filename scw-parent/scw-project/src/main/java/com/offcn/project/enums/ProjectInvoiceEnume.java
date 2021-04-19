package com.offcn.project.enums;


public enum ProjectInvoiceEnume {

    NO_FP(0, "不开发票"),
    HAVE_FP(1, "开发票");

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    private ProjectInvoiceEnume(Integer code, String type) {
        this.code = code;
        this.type = type;
    }


}
