package com.corral.firebase.shailshah.bakingapp.helper;

/**
 * Created by shailshah on 10/31/17.
 */

public class BakeryStepsHelper {


    private static String nextPosition;
    private static int stepPosition;
    private static boolean twoPaneMode;
    private static  String totalSteps;

    public static int getStepPosition() {
        return stepPosition;
    }

    public static void setStepPosition(int s) {
        stepPosition = s;
    }



    public static String getNextPosition() {
        return nextPosition;
    }

    public static void setNextPosition(String nextPosition) {
        BakeryStepsHelper.nextPosition = nextPosition;
    }

    public static String getTotalSteps() {
        return totalSteps;
    }

    public static void setTotalSteps(String totalSteps) {
        BakeryStepsHelper.totalSteps = totalSteps;
    }

    public static boolean isTwoPaneMode() {
        return twoPaneMode;
    }

    public static void setTwoPaneMode(boolean twoPaneMode) {
        BakeryStepsHelper.twoPaneMode = twoPaneMode;
    }
}
