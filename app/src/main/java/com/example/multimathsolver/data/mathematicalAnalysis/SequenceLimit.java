package com.example.multimathsolver.data.mathematicalAnalysis;

public class SequenceLimit {
    public static String solve(String sequence) {
        // Формируем URL запроса к Wolfram Alpha API
        SequenceLimitThread thread = new SequenceLimitThread(sequence);
        thread.start();
        try {
            thread.join();
            return thread.getXmlText();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}