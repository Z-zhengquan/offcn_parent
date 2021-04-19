package com.offcn.project.enums;

public enum ProjectImageTypeEnume {
    HEADER( 0, "头图"),
    DETAILS( 1, "详细图");
    private Integer code;
    private String type;

    private ProjectImageTypeEnume(Integer code, String type) {
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
