package id.pptik.ilham.sahabatbawaslu.features.forum;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.ForumsInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityAddForumBinding;
import id.pptik.ilham.sahabatbawaslu.models.ForumsModel;
import id.pptik.ilham.sahabatbawaslu.view_models.ForumsViewModel;

public class AddForumActivity extends AppCompatActivity {
    @BindView(R.id.text_view_tambah_hashtag)TextView textViewTambahHashtag;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.linearLayoutFlexibleEditText)LinearLayout linearLayoutFlexibleEditText;
    @BindView(R.id.imageButtonAddHashtag)ImageButton imageButtonAddHashTag;

    private int hashTagCounter = 1;
    private ActivityAddForumBinding activityAddForumBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Binding View Model to XML layout
        activityAddForumBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_forum);
        ForumsViewModel forumsViewModel = new ForumsViewModel(new ForumsModel());
        activityAddForumBinding.setForums(forumsViewModel);

        //Binding interface
        activityAddForumBinding.setAddforumsevent(new ForumsInterface() {
            @Override
            public void onClickAddForums() {

            }
        });

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.oranyeGelap));

        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.add_forum_label));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageButtonAddHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHashtagEditText(hashTagCounter);
                hashTagCounter++;
            }
        });

        textViewTambahHashtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHashtagEditText(hashTagCounter);
                hashTagCounter++;
            }
        });
    }

    private void addHashtagEditText(int hashTagCounter){
        EditText editTextHashtag = new EditText(this);
        editTextHashtag.setId(hashTagCounter);
        editTextHashtag.setHint(R.string.tuliskan_hashtag_label);
        linearLayoutFlexibleEditText.addView(editTextHashtag);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
