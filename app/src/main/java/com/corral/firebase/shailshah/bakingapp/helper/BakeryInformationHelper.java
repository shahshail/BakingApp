package com.corral.firebase.shailshah.bakingapp.helper;

import android.graphics.Bitmap;

/**
 * Created by shailshah on 10/25/17.
 */

public class BakeryInformationHelper {

    private static String ID;
    private static String bakeryId;
    private static String itemName;
    private static String[] itemQuentity;
    private static String[] itemMeasure;
    private static String[] itemIngredient;
    private static String[] stepsId;
    private static String[] stepsShortDescription;
    private static String[] stepsDescription;
    private static String[] stepsVideoUrl;
    private static Bitmap stepsThumbnailUrl;
    private static String itemServings;
    private static String itemImagePath;
    private static String[] thumbnails;


    public static String getBakeryId() {
        return bakeryId;
    }

    public static void setBakeryId(String bakeryId) {
        BakeryInformationHelper.bakeryId = bakeryId;
    }

    public static String getItemName() {
        return itemName;
    }

    public static void setItemName(String itemName) {
        BakeryInformationHelper.itemName = itemName;
    }

    public static String[] getItemQuentity() {
        return itemQuentity;
    }

    public static void setItemQuentity(String[] itemQuentity) {
        BakeryInformationHelper.itemQuentity = itemQuentity;
    }

    public static String[] getItemMeasure() {
        return itemMeasure;
    }

    public static void setItemMeasure(String[] itemMeasure) {
        BakeryInformationHelper.itemMeasure = itemMeasure;
    }

    public static String[] getItemIngredient() {
        return itemIngredient;
    }

    public static void setItemIngredient(String[] itemIngredient) {
        BakeryInformationHelper.itemIngredient = itemIngredient;
    }

    public static String[] getStepsId() {
        return stepsId;
    }

    public static void setStepsId(String[] stepsId) {
        BakeryInformationHelper.stepsId = stepsId;
    }

    public static String[] getStepsShortDescription() {
        return stepsShortDescription;
    }

    public static void setStepsShortDescription(String[] stepsShortDescription) {
        BakeryInformationHelper.stepsShortDescription = stepsShortDescription;
    }

    public static String[] getStepsDescription() {
        return stepsDescription;
    }

    public static void setStepsDescription(String[] stepsDescription) {
        BakeryInformationHelper.stepsDescription = stepsDescription;
    }

    public static String[] getStepsVideoUrl() {
        return stepsVideoUrl;
    }

    public static void setStepsVideoUrl(String[] stepsVideoUrl) {
        BakeryInformationHelper.stepsVideoUrl = stepsVideoUrl;
    }

    public static Bitmap getStepsThumbnailUrl() {
        return stepsThumbnailUrl;
    }

    public static void setStepsThumbnailUrl(Bitmap stepsThumbnailUrl) {
        BakeryInformationHelper.stepsThumbnailUrl = stepsThumbnailUrl;
    }

    public static String getItemServings() {
        return itemServings;
    }

    public static void setItemServings(String itemServings) {
        BakeryInformationHelper.itemServings = itemServings;
    }

    public static String getItemImagePath() {
        return itemImagePath;
    }

    public static void setItemImagePath(String itemImagePath) {
        BakeryInformationHelper.itemImagePath = itemImagePath;
    }

    public static String getID() {
        return ID;
    }

    public static void setID(String ID) {
        BakeryInformationHelper.ID = ID;
    }


    public static String[] getThumbnails() {
        return thumbnails;
    }

    public static void setThumbnails(String[] thumbnails) {
        BakeryInformationHelper.thumbnails = thumbnails;
    }
}
