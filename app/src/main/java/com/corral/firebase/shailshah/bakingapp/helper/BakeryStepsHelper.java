package com.corral.firebase.shailshah.bakingapp.helper;

/**
 * Created by shailshah on 10/31/17.
 */

public class BakeryStepsHelper {


    private static String ingredientResult;
    private static int cursor;

    private static String nextPosition;
   private static int stepPosition;

    public static int getStepPosition() {
        return stepPosition;
    }

    public static void setStepPosition(int s) {
        stepPosition = s;
    }

    public static String getIngredientResult() {
        return ingredientResult;
    }

    public static void setIngredientResult(String ingredientResult) {
        ingredientResult = ingredientResult;
    }

    public static int getCursor() {
        return cursor;
    }

    public static void setCursor(int cursor) {
        BakeryStepsHelper.cursor = cursor;
    }

    public static String getNextPosition() {
        return nextPosition;
    }

    public static void setNextPosition(String nextPosition) {
        BakeryStepsHelper.nextPosition = nextPosition;
    }
}
