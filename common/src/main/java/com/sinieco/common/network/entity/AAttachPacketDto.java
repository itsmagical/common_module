package com.sinieco.common.network.entity;

import java.util.List;

/**
 * 附件包DTO
 * Created by LiuHe on 2018/9/25.
 */

public class AAttachPacketDto {

    private String fileType;

    private Long objectId;

    private Long attachpacketId;

    private List<Long> attachIds;

    private List<AAttachmentDto> attachments;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getAttachpacketId() {
        return attachpacketId;
    }

    public void setAttachpacketId(Long attachpacketId) {
        this.attachpacketId = attachpacketId;
    }

    public List<Long> getAttachIds() {
        return attachIds;
    }

    public void setAttachIds(List<Long> attachIds) {
        this.attachIds = attachIds;
    }

    public List<AAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AAttachmentDto> attachments) {
        this.attachments = attachments;
    }
}
