package com.awifi.np.admin.entity;

public class NPTemplateWithBLOBs extends NPTemplate {
    private String src;

    private String content;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}