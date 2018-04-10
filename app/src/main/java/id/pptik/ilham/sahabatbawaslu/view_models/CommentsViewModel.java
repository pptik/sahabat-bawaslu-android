package id.pptik.ilham.sahabatbawaslu.view_models;

import android.databinding.BaseObservable;

import id.pptik.ilham.sahabatbawaslu.models.CommentsModel;
import id.pptik.ilham.sahabatbawaslu.models.NewsModel;

/**
 * Created by Ilham on 28/02/18.
 */

public class CommentsViewModel extends BaseObservable {
    private String content;

    public CommentsViewModel(CommentsModel commentsModel) {
        this.content = commentsModel.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
