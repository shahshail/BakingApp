package com.corral.firebase.shailshah.bakingapp.helper;

/**
 * Created by shailshah on 10/31/17.
 */

public class BakeryStepsHelper {


    private static String ingredientResult;
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
}
