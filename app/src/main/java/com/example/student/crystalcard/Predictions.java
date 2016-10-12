package com.example.student.crystalcard;

import java.util.Random;

public class Predictions {

    private static Predictions predictions;
    private String[] answers;
    private Random random = new Random();
    private int rnd;

    private Predictions(){
        answers = new String[] {
                "Your wishes will come true.",
                "Your wishes will NEVER come true."
        };
    }

    public static Predictions get() {
        if(predictions == null){
            predictions = new Predictions();
        }
        return predictions;
    }

    public String getPrediction(){
        rnd = random.nextInt(answers.length);
        return answers[rnd];
    }
}
