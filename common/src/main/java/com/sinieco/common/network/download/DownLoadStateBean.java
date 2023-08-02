package com.sinieco.common.network.download;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 *
 * Created by LiuHe on 2018/9/23.
 */

public class DownLoadStateBean implements Parcelable, Serializable {
    /**
     *  文件总大小
     */
    private long total;
    /**
     *  已下载的文件大小
     */
    private long bytesLoaded;

    /**
     * 多文件下载tag
     */
    private String tag;

    public DownLoadStateBean(long total, long bytesLoaded) {
        this.total = total;
        this.bytesLoaded = bytesLoaded;
    }

    public DownLoadStateBean(long total, long bytesLoaded, String tag) {
        this.total = total;
        this.bytesLoaded = bytesLoaded;
        this.tag = tag;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getBytesLoaded() {
        return bytesLoaded;
    }

    public void setBytesLoaded(long bytesLoaded) {
        this.bytesLoaded = bytesLoaded;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    protected DownLoadStateBean(Parcel in) {
        total = in.readLong();
        bytesLoaded = in.readLong();
        tag = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(total);
        dest.writeLong(bytesLoaded);
        dest.writeString(tag);
    }

    public static final Creator<DownLoadStateBean> CREATOR = new Creator<DownLoadStateBean>() {
        @Override
        public DownLoadStateBean createFromParcel(Parcel in) {
            return new DownLoadStateBean(in);
        }

        @Override
        public DownLoadStateBean[] newArray(int size) {
            return new DownLoadStateBean[size];
        }
    };
}
