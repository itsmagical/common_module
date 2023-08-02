package com.sinieco.common.network.entity;

import java.io.Serializable;

/**
 * 附件DTO
 * Created by LiuHe on 2018/9/25.
 */

public class AAttachmentDto implements Serializable {

    private Long dataId;

    private String fileName;

    private String storePath;

    /**
     *  附件路径
     *  上传成功响应和接口查询路径一致
     */
    private String globalPath;

    private int type;

    private String strValue;

    private byte[] fileByte;

    private String suffix;

    private Double size;

    private String unit;

    private Long attachmentPacketId;

    private String fixedPath;

    private Integer mediaDuration;

    /**
     *  文件大小+单位
     */
    private String fileSize;

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String filename) {
        this.fileName = filename;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getGlobalPath() {
        return globalPath;
    }

    public void setGlobalPath(String globalPath) {
        this.globalPath = globalPath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public byte[] getFileByte() {
        return fileByte;
    }

    public void setFileByte(byte[] fileByte) {
        this.fileByte = fileByte;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getAttachmentPacketId() {
        return attachmentPacketId;
    }

    public void setAttachmentPacketId(Long attachmentPacketId) {
        this.attachmentPacketId = attachmentPacketId;
    }

    public String getFixedPath() {
        return fixedPath;
    }

    public void setFixedPath(String fixedPath) {
        this.fixedPath = fixedPath;
    }

    public Integer getMediaDuration() {
        return mediaDuration;
    }

    public void setMediaDuration(Integer mediaDuration) {
        this.mediaDuration = mediaDuration;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
