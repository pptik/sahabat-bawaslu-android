package id.pptik.ilham.sahabatbawaslu.view_models;

import android.databinding.BaseObservable;

import id.pptik.ilham.sahabatbawaslu.models.NewsModel;
import id.pptik.ilham.sahabatbawaslu.models.UserModel;

/**
 * Created by Ilham on 28/02/18.
 */

public class NewsViewModel extends BaseObservable {
    private String content;

    public NewsViewModel(NewsModel newsModel) {
        this.content = newsModel.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
