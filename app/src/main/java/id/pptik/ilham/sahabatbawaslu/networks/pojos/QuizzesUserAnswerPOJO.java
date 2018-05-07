package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ilham on 07/05/18.
 */

public class QuizzesUserAnswerPOJO {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("rc")
    @Expose
    private String rc;
    @SerializedName("rm")
    @Expose
    private String rm;
    @SerializedName("results")
    @Expose
    private Results results;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public class Results {

        @SerializedName("quiz_id")
        @Expose
        private String quizId;
        @SerializedName("post_by")
        @Expose
        private PostBy postBy;
        @SerializedName("score")
        @Expose
        private Integer score;
        @SerializedName("attempt_duration")
        @Expose
        private Integer attemptDuration;
        @SerializedName("date_taken")
        @Expose
        private String dateTaken;
        @SerializedName("total_question")
        @Expose
        private Integer totalQuestion;
        @SerializedName("correct_answer")
        @Expose
        private Integer correctAnswer;
        @SerializedName("wrong_answer")
        @Expose
        private Integer wrongAnswer;
        @SerializedName("no_answer")
        @Expose
        private Integer noAnswer;
        @SerializedName("attempt")
        @Expose
        private Integer attempt;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getQuizId() {
            return quizId;
        }

        public void setQuizId(String quizId) {
            this.quizId = quizId;
        }

        public PostBy getPostBy() {
            return postBy;
        }

        public void setPostBy(PostBy postBy) {
            this.postBy = postBy;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Integer getAttemptDuration() {
            return attemptDuration;
        }

        public void setAttemptDuration(Integer attemptDuration) {
            this.attemptDuration = attemptDuration;
        }

        public String getDateTaken() {
            return dateTaken;
        }

        public void setDateTaken(String dateTaken) {
            this.dateTaken = dateTaken;
        }

        public Integer getTotalQuestion() {
            return totalQuestion;
        }

        public void setTotalQuestion(Integer totalQuestion) {
            this.totalQuestion = totalQuestion;
        }

        public Integer getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(Integer correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        public Integer getWrongAnswer() {
            return wrongAnswer;
        }

        public void setWrongAnswer(Integer wrongAnswer) {
            this.wrongAnswer = wrongAnswer;
        }

        public Integer getNoAnswer() {
            return noAnswer;
        }

        public void setNoAnswer(Integer noAnswer) {
            this.noAnswer = noAnswer;
        }

        public Integer getAttempt() {
            return attempt;
        }

        public void setAttempt(Integer attempt) {
            this.attempt = attempt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public class PostBy {

            @SerializedName("user_id")
            @Expose
            private String userId;
            @SerializedName("username")
            @Expose
            private String username;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

        }


    }
}
