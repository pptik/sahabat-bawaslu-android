package id.pptik.ilham.sahabatbawaslu.features.forum;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.ForumsInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityAddForumBinding;
import id.pptik.ilham.sahabatbawaslu.features.news.AddNewsActivity;
import id.pptik.ilham.sahabatbawaslu.models.ForumsModel;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddForumPojo;
import id.pptik.ilham.sahabatbawaslu.view_models.ForumsViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity.greaterThanEqualLollipop;

public class AddForumActivity extends AppCompatActivity {
    @BindView(R.id.text_view_tambah_hashtag)
    TextView textViewTambahHashtag;
    @BindView(R.id.edit_text_title)
    EditText editTextTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.linearLayoutFlexibleEditText)
    LinearLayout linearLayoutFlexibleEditText;
    @BindView(R.id.imageButtonAddHashtag)
    ImageButton imageButtonAddHashTag;

    private int hashTagCounter = 0;
    private String accessToken;
    private SharedPreferences sharedPreferences;
    private ActivityAddForumBinding activityAddForumBinding;
    private RestServiceInterface restServiceInterface;
    private Map<String, String> hashTagMap;
    private List<String> hashTagContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        hashTagMap = new HashMap<String, String>();
        hashTagContent = new ArrayList<String>();

        //Binding View Model to XML layout
        activityAddForumBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_forum);
        ForumsViewModel forumsViewModel = new ForumsViewModel(new ForumsModel());
        activityAddForumBinding.setForums(forumsViewModel);


        //Binding interface
        activityAddForumBinding.setAddforumsevent(new ForumsInterface() {
            @Override
            public void onClickAddForums() {
                //Ambil tag yang ada kalau hashTagContent lebih dari 0
                if (hashTagCounter > 0) {

                    for (int counter = 0; counter < hashTagCounter; counter++) {
                        EditText editText = (EditText) (linearLayoutFlexibleEditText).getChildAt(counter);
                        hashTagContent.add(counter, editText.getText().toString());
                    }

                    for (int counter = 0; counter < hashTagContent.size(); counter++) {

                        hashTagMap.put("Tags[" + counter + "]", hashTagContent.get(counter));

                    }
                }else if(hashTagCounter == 0){
                    hashTagMap.put("Tags[0]", "");
                }
                sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
                accessToken = sharedPreferences.getString("accessToken", "abcde");

                Call<AddForumPojo> addForumPojoCall = restServiceInterface.
                        createForum(editTextTitle.getText().toString(), hashTagMap, accessToken);

                addForumPojoCall.enqueue(new Callback<AddForumPojo>() {
                    @Override
                    public void onResponse(Call<AddForumPojo> call, Response<AddForumPojo> response) {
                        AddForumPojo addForumPojo = response.body();
                        finish();
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        Toast.makeText(AddForumActivity.this, addForumPojo.getRm().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<AddForumPojo> call, Throwable t) {
                        Log.e("ERROR", t.getLocalizedMessage());
                    }
                });
            }
        });

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (greaterThanEqualLollipop){
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.oranyeGelap));
        }

        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.add_forum_label));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageButtonAddHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHashtagEditText(hashTagCounter, v.getContext());
                hashTagCounter++;
            }
        });

        textViewTambahHashtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHashtagEditText(hashTagCounter, v.getContext());
                hashTagCounter++;
            }
        });
    }

    private void addHashtagEditText(final int hashTagCounter, Context context) {
        final EditText editTextHashtag = new EditText(context);
        editTextHashtag.setId(hashTagCounter);
        editTextHashtag.setHint(R.string.tuliskan_hashtag_label);
        linearLayoutFlexibleEditText.addView(editTextHashtag);
        //final int counterLocalEditText = editTextHashtag.getId();

        //hashTagContent.add(editTextHashtag.getText().toString());
        editTextHashtag.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /*if (hasFocus){

                    if(hashTagContent.size() == hashTagCounter){
                        hashTagContent.add(hashTagCounter,editTextHashtag.getText().toString());
                    }else{
                        hashTagContent.set(hashTagCounter,editTextHashtag.getText().toString());
                    }
                    Log.d("COU",Integer.toString(hashTagCounter));
                    Log.d("COU",editTextHashtag.getText().toString());
                }*/
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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
