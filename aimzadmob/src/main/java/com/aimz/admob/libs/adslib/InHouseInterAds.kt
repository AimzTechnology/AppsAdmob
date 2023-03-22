/*
package com.aimz.admob.libs.adslib

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation.findNavController
import com.bumptech.glide.Glide
import com.aimz.admob.libs.R
import com.google.android.material.card.MaterialCardView

class InHouseInterAds {
    companion object {
        fun onBackInHouseInterAd(context: Context, activity: Activity, appName: String, pkgName: String) {
            if(isAdLoaded()) {
                val dialog = getDialog(context, activity, appName, pkgName)

                val closeImg: MaterialCardView = dialog.findViewById(R.id.close)
                val cancelBtn: AppCompatButton = dialog.findViewById(R.id.cancel)

                closeImg.setOnClickListener {
                    dialog.dismiss()
                    activity.finish()
                }
                cancelBtn.setOnClickListener {
                    dialog.dismiss()
                    activity.finish()
                }

                dialog.show()
            }
            else {
                activity.finish()
            }
        }

        fun redirectActivityInHouseInterAd(context: Context, activity: Activity, appName: String, pkgName: String,
                               intent: Intent, isFinish: Boolean) {

            Log.i("MyLog", "App Name: $appName")
            Log.i("MyLog", "Package Name: $pkgName")

            if(isAdLoaded()) {
                Log.i("MyLog", "In house inter loaded - show ad")

                val dialog = getDialog(context, activity, appName, pkgName)

                val closeImg: MaterialCardView = dialog.findViewById(R.id.close)
                val cancelBtn: AppCompatButton = dialog.findViewById(R.id.cancel)

                closeImg.setOnClickListener {
                    dialog.dismiss()
                    activity.startActivity(intent)
                    if (isFinish) {
                        activity.finish()
                    }
                }
                cancelBtn.setOnClickListener {
                    dialog.dismiss()
                    activity.startActivity(intent)
                    if (isFinish) {
                        activity.finish()
                    }
                }

                dialog.show()
            }
            else {
                Log.i("MyLog", "In house inter NOT loaded - NOT show ad")
                activity.startActivity(intent)
                if (isFinish) {
                    activity.finish()
                }
            }
        }

        fun emptyInHouseInterAd(context: Context, activity: Activity, appName: String, pkgName: String) {

            if(isAdLoaded()) {
                val dialog = getDialog(context, activity, appName, pkgName)

                val closeImg: MaterialCardView = dialog.findViewById(R.id.close)
                val cancelBtn: AppCompatButton = dialog.findViewById(R.id.cancel)

                closeImg.setOnClickListener { dialog.dismiss() }
                cancelBtn.setOnClickListener { dialog.dismiss() }

                dialog.show()
            }
        }

        fun navFragmentInHouseInterAd(context: Context, activity: Activity, appName: String,
                                              pkgName: String, view: View, fragmentId: Int, bundle: Bundle,
                                              backStack: Boolean) {

            if(isAdLoaded()) {
                val dialog = getDialog(context, activity, appName, pkgName)

                val closeImg: MaterialCardView = dialog.findViewById(R.id.close)
                val cancelBtn: AppCompatButton = dialog.findViewById(R.id.cancel)

                closeImg.setOnClickListener {
                    dialog.dismiss()
                    findNavController(view).popBackStack(fragmentId, backStack)
                    findNavController(view).navigate(fragmentId, bundle)
                }
                cancelBtn.setOnClickListener {
                    dialog.dismiss()
                    findNavController(view).popBackStack(fragmentId, backStack)
                    findNavController(view).navigate(fragmentId, bundle)
                }

                dialog.show()
            }
            else {
                findNavController(view).popBackStack(fragmentId, backStack)
                findNavController(view).navigate(fragmentId, bundle)
            }
        }

        fun commitFragmentInHouseInterAd(context: Context, activity: Activity, appName: String,
                                         pkgName: String, fragmentTransaction: FragmentTransaction
        ) {

            if(isAdLoaded()) {
                val dialog = getDialog(context, activity, appName, pkgName)

                val closeImg: MaterialCardView = dialog.findViewById(R.id.close)
                val cancelBtn: AppCompatButton = dialog.findViewById(R.id.cancel)

                closeImg.setOnClickListener {
                    dialog.dismiss()
                    // Commit the transaction
                    fragmentTransaction.commit()
                }
                cancelBtn.setOnClickListener {
                    dialog.dismiss()
                    // Commit the transaction
                    fragmentTransaction.commit()
                }

                dialog.show()
            }
            else {
                // Commit the transaction
                fragmentTransaction.commit()
            }
        }

        private fun getDialog(context: Context, activity: Activity, appName: String, pkgName: String): Dialog {
            val dialog = Dialog(activity, android.R.style.Theme_NoTitleBar_Fullscreen)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            //  dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            dialog.setContentView(R.layout.custom_interstitial_layout)

            val title: TextView = dialog.findViewById(R.id.title)
            val subtitle: TextView = dialog.findViewById(R.id.subtitle)
            val icon: ImageView = dialog.findViewById(R.id.app_icon)
            val adImage: ImageView = dialog.findViewById(R.id.ad_image)
            val installBtn: AppCompatButton = dialog.findViewById(R.id.install)
            val infoImg: MaterialCardView = dialog.findViewById(R.id.info)
            val ratingBar: RatingBar = dialog.findViewById(R.id.ad_stars)

            try {
                val adTitle = InHouseAds.getModelAdsList()[0].adsTitle
                val adSubtitle = InHouseAds.getModelAdsList()[0].adsSubText
                val iconLink = InHouseAds.getModelAdsList()[0].adsIcon
                val imageLink = InHouseAds.getModelAdsList()[0].adsImage
                val adRating = InHouseAds.getModelAdsList()[0].adsRating
                val finalPackageName = InHouseAds.getModelAdsList()[0].appPackage

                title.text = adTitle
                subtitle.text = adSubtitle
                ratingBar.rating = adRating.toFloat()

                Glide.with(activity)
                    .load(iconLink)
                    .into(icon)

                Glide.with(activity)
                    .load(imageLink)
                    .into(adImage)


                infoImg.setOnClickListener { open(context, activity, finalPackageName, appName, pkgName) }
                installBtn.setOnClickListener { open(context, activity, finalPackageName, appName, pkgName) }
            }
            catch (e: Exception) {
                e.printStackTrace()
                Log.i("MyLog", e.toString())
            }
            return dialog
        }

        private fun open(context: Context, activity: Activity, finalPackageName: String,
                         appName: String, pkgName: String) {
            try {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$finalPackageName")
                    )
                )
            } catch (e: Exception) {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$finalPackageName")
                    )
                )
            }

            try {
                AdsClick.onAdClick(
                    context,
                    appName,
                    pkgName,
                    finalPackageName,
                    Constants.INTERSTIAL
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun isAdLoaded(): Boolean {
            return (InHouseAds.getModelAdsList().size > 0)
        }
    }
}*/
