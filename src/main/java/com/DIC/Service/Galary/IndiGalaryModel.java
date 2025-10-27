package com.DIC.Service.Galary;

import org.primefaces.model.StreamedContent;

import java.io.InputStream;
import java.util.Date;

public class IndiGalaryModel {

    private int layoutGalaryId;
    private StreamedContent streamedContent;
    private Date createDate;
    private int is_active;
    private int userId;
    private int propId;
    private int propType;
    private InputStream inputStream;

    public int getLayoutGalaryId() {
        return layoutGalaryId;
    }

    public void setLayoutGalaryId(int layoutGalaryId) {
        this.layoutGalaryId = layoutGalaryId;
    }

    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public int getPropType() {
        return propType;
    }

    public void setPropType(int propType) {
        this.propType = propType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


}
