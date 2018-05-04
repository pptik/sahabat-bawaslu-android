package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 04/05/18.
 */

public class QuizzDetailPOJO {
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

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("desc")
        @Expose
        private String desc;
        @SerializedName("post_by")
        @Expose
        private PostBy postBy;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private List<UpdatedAt> updatedAt = null;
        @SerializedName("user_detail")
        @Expose
        private UserDetail userDetail;
        @SerializedName("question_list")
        @Expose
        private List<QuestionList> questionList = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public PostBy getPostBy() {
            return postBy;
        }

        public void setPostBy(PostBy postBy) {
            this.postBy = postBy;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public List<UpdatedAt> getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(List<UpdatedAt> updatedAt) {
            this.updatedAt = updatedAt;
        }

        public UserDetail getUserDetail() {
            return userDetail;
        }

        public void setUserDetail(UserDetail userDetail) {
            this.userDetail = userDetail;
        }

        public List<QuestionList> getQuestionList() {
            return questionList;
        }

        public void setQuestionList(List<QuestionList> questionList) {
            this.questionList = questionList;
        }

        public class UserDetail {

            @SerializedName("display_picture")
            @Expose
            private String displayPicture;

            public String getDisplayPicture() {
                return displayPicture;
            }

            public void setDisplayPicture(String displayPicture) {
                this.displayPicture = displayPicture;
            }

        }

        public class UpdatedAt {

            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("user_id")
            @Expose
            private String userId;
            @SerializedName("username")
            @Expose
            private String username;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

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

        public class QuestionList {

            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("quiz_id")
            @Expose
            private String quizId;
            @SerializedName("question")
            @Expose
            private String question;
            @SerializedName("post_by")
            @Expose
            private PostBy postBy;
            @SerializedName("status")
            @Expose
            private Integer status;
            @SerializedName("poin")
            @Expose
            private Integer poin;
            @SerializedName("multiple_choice")
            @Expose
            private List<MultipleChoice> multipleChoice = null;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private List<Object> updatedAt = null;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getQuizId() {
                return quizId;
            }

            public void setQuizId(String quizId) {
                this.quizId = quizId;
            }

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public PostBy getPostBy() {
                return postBy;
            }

            public void setPostBy(PostBy postBy) {
                this.postBy = postBy;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public Integer getPoin() {
                return poin;
            }

            public void setPoin(Integer poin) {
                this.poin = poin;
            }

            public List<MultipleChoice> getMultipleChoice() {
                return multipleChoice;
            }

            public void setMultipleChoice(List<MultipleChoice> multipleChoice) {
                this.multipleChoice = multipleChoice;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public List<Object> getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(List<Object> updatedAt) {
                this.updatedAt = updatedAt;
            }

            public class MultipleChoice {

                @SerializedName("text")
                @Expose
                private String text;
                @SerializedName("correct")
                @Expose
                private Boolean correct;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public Boolean getCorrect() {
                    return correct;
                }

                public void setCorrect(Boolean correct) {
                    this.correct = correct;
                }

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

}
