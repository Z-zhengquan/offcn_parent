package com.offcn.project.enums;

public enum ProjectReturnEnume {

    VIRTUAL(0, "虚拟物品"),
    REAL(1, "实物回报");

    private Integer code;
    private String type;

    private ProjectReturnEnume(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

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

}
