package top.oahnus.Bean;

import java.io.Serializable;

/**
 * Created by oahnus on 2016/7/20.
 */
public class Message implements Serializable{
    private static final long serialVersionUID = 1234L;

    private String code;
    private String targetID;
    private String sourceID;

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getTargetID() {
        return targetID;
    }

    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    private String content;
    private String extra;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
