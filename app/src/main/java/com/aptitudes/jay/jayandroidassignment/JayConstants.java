package com.aptitudes.jay.jayandroidassignment;

/**
 * Created by Admin on 8/13/2016.
 */
public class JayConstants {
    public static String getJayKeyQuestionArray="questions";
    public static String jayKeyQuestion="question";
    public static String jayKeyAns="ans";
    public static String jayKeyOptions="options";
    public static int jayTotalRandomQuestions=5;

    public enum Ratings {
        ZERO,
        POOR,
        TRYAGAIN,
        GOODJOB,
        EXCELLENT,
        GENIUS;

        public String getRatingText(int score) {
            switch (this) {
                case ZERO: case POOR: case TRYAGAIN:
                    return "Please try again!";
                case GOODJOB:
                    return "Good job!";
                case EXCELLENT:
                    return "Excellent work!";
                case GENIUS:
                    return "You are a genius!";
                default:
                    return " ";
            }
        }

    }
}
