/*
package com.aimz.admob.libs.adslib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.aimz.admob.libs.R;
import com.aimz.admob.libs.admob.ENUMS;
import com.google.android.material.textview.MaterialTextView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class InHouseNativeAds {

    public static void showInHouseAds(Context context, Activity activity, String appName,
                                      String pkgName, int isSmallAd) {

        MaterialTextView adsTitle = activity.findViewById(R.id.adsTitle);
        MaterialTextView adsSubTitle = activity.findViewById(R.id.adsSubTitle);
        MaterialTextView adsTitleSmall = activity.findViewById(R.id.adsTitleSmall);
        MaterialTextView adsRating = activity.findViewById(R.id.adsRating);
        ImageView icon = activity.findViewById(R.id.iconAds);
        ImageView featureGraphic = activity.findViewById(R.id.blurImage);
        Button installBtn = activity.findViewById(R.id.installBtn);
      //  VideoView ad_mediaVideo = activity.findViewById(R.id.ad_mediaVideo);
        YouTubePlayerView youTubePlayerView = activity.findViewById(R.id.youtube_player_view);

        showAd(context, appName, pkgName, isSmallAd, adsTitle, adsSubTitle, adsTitleSmall, adsRating,
                icon, featureGraphic, installBtn, youTubePlayerView);
    }

    public static void showFragmentInHouseAds(Context context, View view, String appName,
                                              String pkgName, int isSmallAd) {

        MaterialTextView adsTitle = view.findViewById(R.id.adsTitle);
        MaterialTextView adsSubTitle = view.findViewById(R.id.adsSubTitle);
        MaterialTextView adsTitleSmall = view.findViewById(R.id.adsTitleSmall);
        MaterialTextView adsRating = view.findViewById(R.id.adsRating);
        ImageView icon = view.findViewById(R.id.iconAds);
        ImageView featureGraphic = view.findViewById(R.id.blurImage);
        Button installBtn = view.findViewById(R.id.installBtn);
        //VideoView ad_mediaVideo = view.findViewById(R.id.ad_mediaVideo);
        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);

        showAd(context, appName, pkgName, isSmallAd, adsTitle, adsSubTitle, adsTitleSmall, adsRating,
                icon, featureGraphic, installBtn, youTubePlayerView);
    }

    public static void showAd(Context context, String appName, String pkgName, int isSmallAd,
                              MaterialTextView adsTitle, MaterialTextView adsSubTitle,
                              MaterialTextView adsTitleSmall, MaterialTextView adsRating,
                              ImageView icon, ImageView featureGraphic, Button installBtn,
                              YouTubePlayerView youTubePlayerView) {

        adsSubTitle.setSelected(true);

        String packageName = "";
        try {
            Log.i("MyLog", "In try");

            String iconURL = InHouseAds.getModelAdsList().get(0).getAdsIcon();
            String featureGraphicURL = InHouseAds.getModelAdsList().get(0).getAdsImage();
            String videoURL = InHouseAds.getModelAdsList().get(0).getAdsVideo();
            packageName = InHouseAds.getModelAdsList().get(0).getAppPackage();
            boolean isVideoAvailable = InHouseAds.getModelAdsList().get(0).isVideo();

            adsTitle.setText(   InHouseAds.getModelAdsList().get(0).getAdsTitle()); // title
            adsSubTitle.setText(InHouseAds.getModelAdsList().get(0).getAdsSubText());
            adsTitleSmall.setText(InHouseAds.getModelAdsList().get(0).getAdsTitle());
            adsRating.setText(InHouseAds.getModelAdsList().get(0).getAdsRating());

            Glide.with(context)
                    .load(iconURL)
                    .into( icon );

            //JUST FOR TESTING - TO REMOVE
           // isVideoAvailable = true;

            if (isVideoAvailable) {
                //ad_mediaVideo.setVisibility(View.VISIBLE);
                */
/* Uri video = Uri.parse(videoURL);
                ad_mediaVideo.setVideoURI(video);
                ad_mediaVideo.start();
                ad_mediaVideo.setOnCompletionListener(mediaPlayer -> {
                    Log.d("TAG", "showInHouseAds: ");
                    ad_mediaVideo.start();
                });*//*


                featureGraphic.setVisibility(View.GONE);
                youTubePlayerView.setVisibility(View.VISIBLE);
                YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = videoURL;
                                //"g5qauZ8Vn2w";
                        youTubePlayer.loadVideo(videoId, 0f);
                    }
                };
                IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                        .controls(0)
                        .build();
                youTubePlayerView.initialize(listener, iFramePlayerOptions);

            } else {
                //ad_mediaVideo.setVisibility(View.GONE);

                youTubePlayerView.setVisibility(View.GONE);

                featureGraphic.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(featureGraphicURL)
                        .into( featureGraphic );
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("MyLog", "Exception: "+e.toString());
        }

        if(isSmallAd == ENUMS.SMALL_ADS){
            //ad_mediaVideo.setVisibility(View.GONE);

            youTubePlayerView.setVisibility(View.GONE);

            featureGraphic.setVisibility(View.GONE);
        }
        String finalPackageName = packageName;
        installBtn.setOnClickListener(view -> {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + finalPackageName)));
            } catch (Exception e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + finalPackageName)));
            }

            try {
                AdsClick.onAdClick(context, appName, pkgName,finalPackageName, Constants.NATIVE_AD );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
*/
