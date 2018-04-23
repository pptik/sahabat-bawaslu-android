package id.pptik.ilham.sahabatbawaslu.view_models;

import android.databinding.BaseObservable;

import java.util.List;

import id.pptik.ilham.sahabatbawaslu.models.ForumsModel;
import id.pptik.ilham.sahabatbawaslu.models.NewsModel;

/**
 * Created by Ilham on 28/02/18.
 */

public class ForumsViewModel extends BaseObservable {
    private String title;
    private List<String> hashtag;

    public ForumsViewModel(ForumsModel forumsModel) {
        this.title = forumsModel.title;
        this.hashtag = forumsModel.hashtag;
    }

    public List<String> getHashtag() {
        return hashtag;
    }

    public void setHashtag(List<String> hashtag) {
        this.hashtag = hashtag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
