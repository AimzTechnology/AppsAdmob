package com.aimz.admob.libs.adslib;

public class InHouseModel {
    private final String adsTitle;
    private final String adsSubText;
    private final String adsRating;
    private final String adsIcon;
    private final String adsImage;
    private final String adsVideo;
    private final boolean isVideo;
    private final String appPackage;

    public InHouseModel(String adsTitle, String adsSubText, String adsRating, String adsIcon, String adsImage, String adsVideo, boolean isVideo, String appPackage) {
        this.adsTitle = adsTitle;
        this.adsSubText = adsSubText;
        this.adsRating = adsRating;
        this.adsIcon = adsIcon;
        this.adsImage = adsImage;
        this.adsVideo = adsVideo;
        this.isVideo = isVideo;
        this.appPackage = appPackage;
    }

    public String getAdsTitle() {
        return adsTitle;
    }

    public String getAdsSubText() {
        return adsSubText;
    }

    public String getAdsRating() {
        return adsRating;
    }

    public String getAdsIcon() {
        return adsIcon;
    }

    public String getAdsImage() {
        return adsImage;
    }

    public String getAdsVideo() {
        return adsVideo;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public String getAppPackage() {
        return appPackage;
    }
}
