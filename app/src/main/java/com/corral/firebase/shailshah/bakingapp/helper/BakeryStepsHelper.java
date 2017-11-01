package com.corral.firebase.shailshah.bakingapp.helper;

/**
 * Created by shailshah on 10/31/17.
 */

public class BakeryStepsHelper {


    private String ingredientResult;
   private static int stepPosition;

    public static int getStepPosition() {
        return stepPosition;
    }

    public static void setStepPosition(int s) {
        stepPosition = s;
    }

    public String getIngredientResult() {
        return ingredientResult;
    }

    public void setIngredientResult(String ingredientResult) {
        this.ingredientResult = ingredientResult;
    }
}
