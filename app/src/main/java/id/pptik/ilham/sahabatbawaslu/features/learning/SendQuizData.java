package id.pptik.ilham.sahabatbawaslu.features.learning;

/**
 * Created by Ilham on 08/05/18.
 */

public interface SendQuizData {
    void onSubmitAnswers(int score, int questionsTotal, int correctAnswer,
                         int wrongAnswer, int noAnswer);
}
