package com.example.arvin.versions.entity;

/**
 * Created by acer1 on 2016/5/23.
 */
public class VersionInfo {
    public String versioncode;
    public String versioninfo;
    public String versionurl;

    public VersionInfo() {
    }

    public String getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode;
    }

    public String getVersioninfo() {
        return versioninfo;
    }

    public void setVersioninfo(String versioninfo) {
        this.versioninfo = versioninfo;
    }

    public String getVersionurl() {
        return versionurl;
    }

    public void setVersionurl(String versionurl) {
        this.versionurl = versionurl;
    }

    public VersionInfo(String versioncode, String versionurl, String versioninfo) {
        this.versioncode = versioncode;
        this.versionurl = versionurl;
        this.versioninfo = versioninfo;
    }

    @Override
    public String toString() {
        return "VersionInfo{" +
                "versioncode='" + versioncode + '\'' +
                ", versioninfo='" + versioninfo + '\'' +
                ", versionurl='" + versionurl + '\'' +
                '}';
    }
}
