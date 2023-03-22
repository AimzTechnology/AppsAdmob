package com.aimz.admob.libs.admob;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.aimz.admob.libs.AdsCounter;
import com.aimz.admob.libs.R;
import com.aimz.admob.libs.adslib.Constants;
import com.aimz.admob.libs.adslib.SharedPrefUtils;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.card.MaterialCardView;

public class AdmobAds {

    public static InterstitialAd mInterstitial = null;
    public static NativeAd nativeAd1;

    public static void showActivityBanner(Context context, Activity activity) {
        MaterialCardView adArea = activity.findViewById(R.id.ad_area);
        LinearLayout adFrame = activity.findViewById(R.id.ad_layout);

        showBanner(context, adArea, adFrame);
    }

    public static void showFragmentBanner(Context context, View view) {

        MaterialCardView adArea = view.findViewById(R.id.ad_area);
        LinearLayout adFrame = view.findViewById(R.id.ad_layout);

        showBanner(context, adArea, adFrame);
    }

    public static void showBanner(Context context,
                                  MaterialCardView adArea, LinearLayout adFrame) {
        String bannerID = "no";
        try {
            bannerID = SharedPrefUtils.getStringData(context, Constants.BANNER);
            if (bannerID == null) {
                bannerID = "no";
            }
        } catch (Exception e) {
            e.printStackTrace();
            bannerID = "no";
        }

        AdView mAdView = new AdView(context);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(bannerID);
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        Log.i("MyLog", "Load ad");
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                Log.i("MyLog", "Ad failed: "+adError);
                adArea.setVisibility(View.VISIBLE);
                adFrame.setVisibility(View.GONE);
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("MyLog", "Ad loaded");
                adArea.setVisibility(View.GONE);
                adFrame.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });

        if(mAdView.getAdSize() != null || mAdView.getAdUnitId() != null)
            mAdView.loadAd(adRequest);
        adFrame.addView(mAdView);
    }

    public static void loadAdmobInters(Context context) {
        if (mInterstitial != null ) {
            return;
        }
         if(!AdsCounter.isABleToLoadInterstitial(context)){
            return;
        }
        try {
            String interID = "no";
            try {
                interID = SharedPrefUtils.getStringData(context, Constants.INTERSTIAL);
                if (interID == null) {
                    interID = "no";
                }
            } catch (Exception e) {
                e.printStackTrace();
                interID = "no";
            }
            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(context, interID, adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            mInterstitial = interstitialAd;
                            Log.d("TAG", "onAdLoaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.d("TAG", loadAdError.getMessage());
                            mInterstitial = null;
                            loadAdmobInters(context);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void adOnBack(Context context, Activity activity, String appName,
                                String packageName){
        if (AdsCounter.isShowAd(context)) {

            //JUST FOR TESTING PURPOSE
           // mInterstitial = null;

            if (mInterstitial != null) {
                mInterstitial.show(activity);
                mInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.d("TAG", "The ad was dismissed.");
                        activity.finish();
                        mInterstitial = null;
                        loadAdmobInters(context);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        // Called when fullscreen content failed to show.
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitial = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });
            } else {
                activity.finish();
//                InHouseInterAds.Companion.onBackInHouseInterAd(context, activity,
//                        appName, packageName);
            }
        }else {
            activity.finish();
        }
    }

    public static void RedirectActivity(Context context, Activity fromActivity, String appName,
                                        String packageName, Intent intent, boolean isFinish) {
        if (AdsCounter.isShowAd(fromActivity)) {
            Log.i("MyLog", "Show ad");

            //FOR TESTING ONLY - TO REMOVE
           // mInterstitial = null;

            if (mInterstitial != null) {
                mInterstitial.show(fromActivity);
                mInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.d("TAG", "The ad was dismissed.");
                        fromActivity.startActivity(intent);
                        if(isFinish) {
                            fromActivity.finish();
                        }
                        mInterstitial = null;
                        loadAdmobInters(fromActivity);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        mInterstitial = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });

            } else {
                fromActivity.startActivity(intent);
                if(isFinish) {
                    fromActivity.finish();
                }

                /*InHouseInterAds.Companion.redirectActivityInHouseInterAd(context, fromActivity,
                        appName, packageName, intent, isFinish);*/
            }
        } else {
            Log.i("MyLog", "Do not Show ad");
            fromActivity.startActivity(intent);
            if(isFinish) {
                fromActivity.finish();
            }
        }
    }

    public static void showInter(Context context, Activity fromActivity, String appName,
                                 String packageName) {
        if (AdsCounter.isShowAd(fromActivity)) {
            if (mInterstitial != null) {
                mInterstitial.show(fromActivity);
                mInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.d("TAG", "The ad was dismissed.");
                        mInterstitial = null;
                        loadAdmobInters(fromActivity);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        mInterstitial = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });
            }
            else {
//                InHouseInterAds.Companion.emptyInHouseInterAd(context, fromActivity,
//                        appName, packageName);
            }
        }
    }

    //interstitial ads
    public static void redirectFragmentWithNavController(Context context, Activity activtiy, String appName,
                                                         String packageName, int fragmentId, View view,
                                                         Bundle bundle, boolean backStack){
        if (AdsCounter.isShowAd(context)) {
            if (mInterstitial != null) {
                mInterstitial.show(activtiy);
                mInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        Navigation.findNavController(view).popBackStack(fragmentId, backStack);
                        Navigation.findNavController(view).navigate(fragmentId, bundle);
                        mInterstitial = null;
                        loadAdmobInters(context);
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }
                });
            } else {
                Navigation.findNavController(view).popBackStack(fragmentId, backStack);
                Navigation.findNavController(view).navigate(fragmentId, bundle);

              /*  InHouseInterAds.Companion.navFragmentInHouseInterAd(context, activtiy, appName, packageName,
                        view, fragmentId, bundle, backStack);*/
            }
        } else {
            Navigation.findNavController(view).popBackStack(fragmentId, backStack);
            Navigation.findNavController(view).navigate(fragmentId, bundle);
        }
    }

    public static void redirectFragmentWithCommit(Context context, Activity activtiy, String appName, String packageName,
                                                  FragmentTransaction fragmentTransaction){
        if (AdsCounter.isShowAd(context)) {
            if (mInterstitial != null) {
                mInterstitial.show(activtiy);
                mInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();

                        // Commit the transaction
                        fragmentTransaction.commit();
                        mInterstitial = null;
                        loadAdmobInters(context);
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }
                });
            } else {
                // Commit the transaction
                fragmentTransaction.commit();
              /*  InHouseInterAds.Companion.commitFragmentInHouseInterAd(context, activtiy, appName,
                        packageName, fragmentTransaction);*/
            }
        } else {
            // Commit the transaction
            fragmentTransaction.commit();
        }
    }

    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     */
    @SuppressLint("MissingPermission")
    public static void refreshAd(Context context, Activity activity, String appName,
                                 String pkgName, int isSmallAd, int nativeThemeColor) {
        FrameLayout nativeAds = activity.findViewById(R.id.fl_adplaceholder);
       LinearLayout AdsAreaEmpty = activity.findViewById(R.id.ads_area_empty);
        /*  LinearLayoutCompat inHouseAdArea =  activity.findViewById(R.id.inHouseAd);*/

        String nativeAdId = SharedPrefUtils
                .getStringData(context, Constants.NATIVE_AD);
        if(nativeAdId==null){
            nativeAdId="no";
        }
        AdLoader.Builder builder = new AdLoader.Builder(context, nativeAdId);

        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    // OnLoadedListener implementation.
                    @Override
                    public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.
                        try {
                            boolean isDestroyed = false;

                            isDestroyed = activity.isDestroyed();
                            if (isDestroyed || activity.isFinishing() || activity.isChangingConfigurations()) {
                                nativeAd1.destroy();
                                return;
                            }
                            // You must call destroy on old ads when you are done with them,
                            // otherwise you will have a memory leak.
                            if (nativeAd1 != null) {
                                nativeAd1.destroy();
                            }
                            nativeAd1 = nativeAd;
                            int layout = getNativeLayout(isSmallAd, nativeThemeColor);

                            NativeAdView adView =
                                    (NativeAdView) activity.getLayoutInflater().inflate(layout, null);
                            populateNativeAdView(nativeAd, adView,isSmallAd);
                            nativeAds.removeAllViews();
                            nativeAds.addView(adView);

                            //correct
                            nativeAds.setVisibility(View.VISIBLE);
                            AdsAreaEmpty.setVisibility(View.GONE);
                          /*  inHouseAdArea.setVisibility(View.GONE);
                           */

                            //Testing
                            /*InHouseNativeAds.showInHouseAds(context, activity, appName, pkgName, isSmallAd);
                            nativeAds.setVisibility(View.GONE);
                            AdsAreaEmpty.setVisibility(View.GONE);
                            inHouseAdArea.setVisibility(View.VISIBLE);*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        VideoOptions videoOptions =
                new VideoOptions.Builder().setStartMuted(false).build();

        NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                                       /* if (InHouseAds.getModelAdsList().size() > 0) {
                                            InHouseNativeAds.showInHouseAds(context, activity, appName, pkgName, isSmallAd);
                                            nativeAds.setVisibility(View.GONE);
                                            AdsAreaEmpty.setVisibility(View.GONE);
                                            inHouseAdArea.setVisibility(View.VISIBLE);
                                        } else {*/
                                            nativeAds.setVisibility(View.GONE);
                                        AdsAreaEmpty.setVisibility(View.VISIBLE);
                                           /* inHouseAdArea.setVisibility(View.GONE);
                                            */
//                                        }
                                    }
                                })
                        .build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }

    @SuppressLint("MissingPermission")
    public static void refreshFragmentAd(Context context, Activity activity, View view, String appName,
                                         String pkgName, int isSmallAd, int nativeThemeColor) {
        FrameLayout nativeAds =view.findViewById(R.id.fl_adplaceholder);
        LinearLayout AdsAreaEmpty = view.findViewById(R.id.ads_area_empty);
      /*
        LinearLayoutCompat inHouseAdArea =  view.findViewById(R.id.inHouseAd);*/
        String nativeAD = "";
        try {
            nativeAD = SharedPrefUtils.getStringData(activity,
                    Constants.NATIVE_AD);
            if (nativeAD == null) {
                nativeAD = "no";
            }
        } catch (Exception e) {
            e.printStackTrace();
            nativeAD = "";
        }
        AdLoader.Builder builder = new AdLoader.Builder(activity, nativeAD);

        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    // OnLoadedListener implementation.
                    @Override
                    public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.
                        try {
                            boolean isDestroyed = false;

                            isDestroyed = activity.isDestroyed();
                            if (isDestroyed || activity.isFinishing() || activity.isChangingConfigurations()) {
                                nativeAd1.destroy();
                                return;
                            }
                            // You must call destroy on old ads when you are done with them,
                            // otherwise you will have a memory leak.
                            if (nativeAd1 != null) {
                                nativeAd1.destroy();
                            }
                            nativeAd1 = nativeAd;
                            int layout = getNativeLayout(isSmallAd, nativeThemeColor);
                            NativeAdView adView =
                                    (NativeAdView) activity.getLayoutInflater().inflate(layout, null);
                            populateNativeAdView(nativeAd, adView,isSmallAd);

                            nativeAds.removeAllViews();
                            nativeAds.addView(adView);

                            //correct
                            nativeAds.setVisibility(View.VISIBLE);
                            AdsAreaEmpty.setVisibility(View.GONE);
                          /*  inHouseAdArea.setVisibility(View.GONE);
                           */
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        VideoOptions videoOptions =
                new VideoOptions.Builder().setStartMuted(false).build();

        NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {

                                        try {
//                                            if (InHouseAds.getModelAdsList().size() > 0) {
//                                                InHouseNativeAds.showFragmentInHouseAds(context, view, appName, pkgName, isSmallAd);
//                                                nativeAds.setVisibility(View.GONE);
//                                                AdsAreaEmpty.setVisibility(View.GONE);
//                                                inHouseAdArea.setVisibility(View.VISIBLE);
//                                            } else {
                                                nativeAds.setVisibility(View.GONE);
                                            AdsAreaEmpty.setVisibility(View.VISIBLE);
                                              /*  inHouseAdArea.setVisibility(View.GONE);
                                                */
//                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                        .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private static int getNativeLayout(int isSmallAd, int nativeThemeColor) {
        int layout = R.layout.ad_unified_white;

        if(nativeThemeColor == ENUMS.BLACK) {
            if (isSmallAd == ENUMS.SMALL_ADS) {
                layout = R.layout.ad_unified_black_small;
            } else if (isSmallAd == ENUMS.LARGE_ADS) {
                layout = R.layout.ad_unified_black;
            }
        }
        else if(nativeThemeColor == ENUMS.WHITE){
            if (isSmallAd == ENUMS.SMALL_ADS) {
                layout = R.layout.ad_unified_white_small;
            } else if (isSmallAd == ENUMS.LARGE_ADS) {
                layout = R.layout.ad_unified_white;
            }
        }

        return layout;
    }

    private static void populateNativeAdView(NativeAd nativeAd, NativeAdView adView, int isSmallAd) {
        // Set the media view.
        try {

            if (isSmallAd == ENUMS.LARGE_ADS) {
                adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
            }
            // Set other ad assets.
            adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
            adView.setBodyView(adView.findViewById(R.id.ad_body));
            adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
            adView.setIconView(adView.findViewById(R.id.ad_app_icon));
            adView.setPriceView(adView.findViewById(R.id.ad_price));
            adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
            adView.setStoreView(adView.findViewById(R.id.ad_store));
            adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

            // The headline and mediaContent are guaranteed to be in every NativeAd.
            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
            if (isSmallAd == ENUMS.LARGE_ADS) {
                adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        try {
            if (nativeAd.getBody() == null) {
                adView.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                adView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (nativeAd.getCallToAction() == null) {
                adView.getCallToActionView().setVisibility(View.INVISIBLE);
            } else {
                adView.getCallToActionView().setVisibility(View.VISIBLE);
                ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (nativeAd.getIcon() == null) {
                adView.getIconView().setVisibility(View.GONE);
            } else {
                ((ImageView) adView.getIconView()).setImageDrawable(
                        nativeAd.getIcon().getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (nativeAd.getPrice() == null) {
                adView.getPriceView().setVisibility(View.INVISIBLE);
            } else {
                adView.getPriceView().setVisibility(View.VISIBLE);
                ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (nativeAd.getStore() == null) {
                adView.getStoreView().setVisibility(View.INVISIBLE);
            } else {
                adView.getStoreView().setVisibility(View.VISIBLE);
                ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (nativeAd.getStarRating() == null) {
                adView.getStarRatingView().setVisibility(View.INVISIBLE);
            } else {
                ((RatingBar) adView.getStarRatingView())
                        .setRating(nativeAd.getStarRating().floatValue());
                adView.getStarRatingView().setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (nativeAd.getAdvertiser() == null) {
                adView.getAdvertiserView().setVisibility(View.INVISIBLE);
            } else {
                ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
                adView.getAdvertiserView().setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // This method tells the Google Mobile Ads SDK that you have finished populating your
            // native ad view with this native ad.
            adView.setNativeAd(nativeAd);

            // Get the video controller for the ad. One will always be provided, even if the ad doesn't
            // have a video asset.
            VideoController vc = nativeAd.getMediaContent().getVideoController();

            // Updates the UI to say whether or not this ad has a video asset.
            if (vc.hasVideoContent()) {


                // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
                // VideoController will call methods on this object when events occur in the video
                // lifecycle.
                vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                    @Override
                    public void onVideoEnd() {
                        // Publishers should allow native ads to complete video playback before
                        // refreshing or replacing them with another ad in the same UI location.

                        super.onVideoEnd();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
