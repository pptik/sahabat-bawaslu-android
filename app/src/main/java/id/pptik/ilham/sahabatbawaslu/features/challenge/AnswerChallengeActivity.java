package id.pptik.ilham.sahabatbawaslu.features.challenge;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.NewsInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityAddNewsBinding;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityAnswerChallengeBinding;
import id.pptik.ilham.sahabatbawaslu.features.news.AddNewsActivity;
import id.pptik.ilham.sahabatbawaslu.models.NewsModel;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddNewsMediaPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddNewsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.UploadImagePOJO;
import id.pptik.ilham.sahabatbawaslu.utils.FileUtils;
import id.pptik.ilham.sahabatbawaslu.view_models.NewsViewModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnswerChallengeActivity extends AppCompatActivity {
    private ActivityAnswerChallengeBinding activityAddNewsBinding;
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    Button buttonSubmit;
    EditText editTextCaption;
    private RestServiceInterface restServiceInterface;
    SharedPreferences sharedPreferences;
    String access_token;
    @BindView(R.id.linearLayoutFlexibleHashtag)
    LinearLayout linearLayoutFlexibleHashtag;
    @BindView(R.id.linearLayoutFlexiblePhoto)
    LinearLayout linearLayoutFlexiblePhoto;
    @BindView(R.id.linearLayoutChallengeList)
    LinearLayout linearLayoutChallengeList;
    @BindView(R.id.text_view_tambah_foto)
    TextView textViewTambahFoto;
    @BindView(R.id.imageButtonAddPhotos)
    ImageButton imageButtonAddPhotos;
    @BindView(R.id.text_view_tambah_hashtag)
    TextView textViewTambahHashtag;
    @BindView(R.id.imageButtonAddHashtag)
    ImageButton imageButtonAddHashTag;
    @BindView(R.id.text_view_jawab_challenge)
    TextView textViewJawabChallenge;
    @BindView(R.id.imageButtonJawabChallenge)
    ImageButton imageButtonJawabChallenge;
    private int photosCounter = 0;
    private Map<String, JsonObject> photosMap;
    private List<JsonObject> photosContent;
    private ArrayList<String> photoFiles;
    private HashMap<String, String> hashTagMap;
    private ArrayList<String> hashTagContent;
    private int hashTagCounter = 0;
    private boolean isShowChallengeList = false;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photosMap = new HashMap<String, JsonObject>();
        photosContent = new ArrayList<JsonObject>();
        photoFiles = new ArrayList<String>();
        hashTagMap = new HashMap<String, String>();
        hashTagContent = new ArrayList<String>();

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        activityAddNewsBinding = DataBindingUtil.setContentView(this,R.layout.activity_answer_challenge);
        final NewsViewModel userViewModel = new NewsViewModel(new NewsModel());

        //Binding View Model
        editTextCaption = (EditText)findViewById(R.id.edit_text_caption);
        buttonSubmit = (Button)findViewById(R.id.button_masuk);

        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        access_token = sharedPreferences.getString("accessToken","abcde");


        activityAddNewsBinding.setNews(userViewModel);
        activityAddNewsBinding.setAddnewsevent(new NewsInterface() {
            @Override
            public void onClickAddNews() {
                //CEK HASTAG
                if (hashTagCounter > 0) {

                    for (int counter = 0; counter < hashTagCounter; counter++) {
                        EditText editText = (EditText) (linearLayoutFlexibleHashtag).getChildAt(counter);
                        hashTagContent.add(counter, editText.getText().toString());
                    }

                    for (int counter = 0; counter < hashTagContent.size(); counter++) {

                        hashTagMap.put("Tags[" + counter + "]", hashTagContent.get(counter));

                    }
                }else if(hashTagCounter == 0){
                    hashTagMap.put("Tags[0]", "");
                }

                if (photosCounter > 0) {//Berita media
                    for (int counter = 0; counter < photosContent.size(); counter++) {
                        photosMap.put("Files[" + counter + "]", photosContent.get(counter));
                    }

                    Log.d("PHOTOFILESARRAY",photoFiles.toString());

                    Call<AddNewsMediaPOJO> addNewsMediaPOJOCall = restServiceInterface.
                            newsCreateMedia(editTextCaption.getText().toString(), photoFiles.toString(), hashTagMap, access_token);
                    addNewsMediaPOJOCall.enqueue(new Callback<AddNewsMediaPOJO>() {
                        @Override
                        public void onResponse(Call<AddNewsMediaPOJO> call, Response<AddNewsMediaPOJO> response) {
                            AddNewsMediaPOJO addNewsMediaPOJO = response.body();
                            if (addNewsMediaPOJO.getSuccess()){
                                //Log.d("UPLOAD MEDIA RESPONSE",addNewsMediaPOJO.getRm());
                                /*finish();
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                Toast.makeText(AddNewsActivity.this, addNewsMediaPOJO.getRm().toString(), Toast.LENGTH_SHORT).show();*/
                                finish();
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }else{
                                //Toast.makeText(AddNewsActivity.this, addNewsMediaPOJO.getRm().toString(), Toast.LENGTH_SHORT).show();
                                Log.e("UPLOAD MEDIA RESPONSE",addNewsMediaPOJO.getRm());
                                finish();
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }
                        }

                        @Override
                        public void onFailure(Call<AddNewsMediaPOJO> call, Throwable t) {

                        }
                    });

                }else if(photosCounter == 0){//Berita teks
                    Call<AddNewsPOJO> callAddNews = restServiceInterface.newsCreateText(
                            editTextCaption.getText().toString(), hashTagMap, access_token);
                    callAddNews.enqueue(new Callback<AddNewsPOJO>() {
                        @Override
                        public void onResponse(Call<AddNewsPOJO> call, Response<AddNewsPOJO> response) {
                            AddNewsPOJO addNewsPOJO = response.body();

                            if(addNewsPOJO.getRc().equals("0000")){
                                finish();
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                Toast.makeText(AnswerChallengeActivity.this, addNewsPOJO.getRm().toString(), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AnswerChallengeActivity.this, addNewsPOJO.getRm().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddNewsPOJO> call, Throwable t) {
                            Toast.makeText(AnswerChallengeActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.oranyeGelap));

        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.add_news_label));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageButtonAddPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhotosEditText(photosCounter,v.getContext());

            }
        });

        textViewTambahFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhotosEditText(photosCounter,v.getContext());

            }
        });

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

        imageButtonJawabChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChallengeList(v.getContext());

            }
        });
        textViewJawabChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChallengeList(v.getContext());
            }
        });
    }

    private void showChallengeList(Context context) {
        if(!isShowChallengeList){
            isShowChallengeList = true;
            final TextView textViewPilihChallenge = new TextView(getApplicationContext());
            textViewPilihChallenge.setHint(R.string.pilih_challenge);
            textViewPilihChallenge.setHintTextColor(Color.BLACK);

            final Spinner spinnerChallengeList = new Spinner(getApplicationContext());
            Spinner.LayoutParams lp = new Spinner.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            spinnerChallengeList.setLayoutParams(lp);
            // Spinner Drop down elements
            List<String> categories = new ArrayList<String>();
            categories.add("Automobile");
            categories.add("Business Services");
            categories.add("Computers");
            categories.add("Education");
            categories.add("Personal");
            categories.add("Travel");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_dropdown_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

            // attaching data adapter to spinner
            spinnerChallengeList.setAdapter(dataAdapter);

            linearLayoutChallengeList.addView(textViewPilihChallenge);
            linearLayoutChallengeList.addView(spinnerChallengeList);
        }
    }

    private void addHashtagEditText(final int hashTagCounter, Context context) {
        final EditText editTextHashtag = new EditText(context);
        editTextHashtag.setId(hashTagCounter);
        editTextHashtag.setHint(R.string.tuliskan_hashtag_label);
        linearLayoutFlexibleHashtag.addView(editTextHashtag);
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

    private void addPhotosEditText(final int hashTagCounter, Context context) {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    final TextView textViewPilihGambar = new TextView(getApplicationContext());
                    textViewPilihGambar.setId(hashTagCounter);
                    textViewPilihGambar.setHint(R.string.ketuk_melihat_galeri);
                    textViewPilihGambar.setHintTextColor(Color.BLACK);
                    textViewPilihGambar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), 100);
                        }
                    });
                    linearLayoutFlexiblePhoto.addView(textViewPilihGambar);
                } else
                    Toast.makeText(getApplicationContext(), "Permisi dibutuhkan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();

    }

    private void uploadFile(Uri fileUri){
        File originalFile = FileUtils.getFile(this,fileUri);
        RequestBody filePart = RequestBody.create(null,originalFile);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image",originalFile.getName(),filePart);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://167.205.7.233:3077/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        RestServiceInterface restServiceInterfaceImage = retrofit.create(RestServiceInterface.class);


        Call<UploadImagePOJO> uploadImagePOJOCall = restServiceInterfaceImage.uploadMedia(file);

        uploadImagePOJOCall.enqueue(new Callback<UploadImagePOJO>() {
            @Override
            public void onResponse(Call<UploadImagePOJO> call, Response<UploadImagePOJO> response) {
                UploadImagePOJO uploadImagePOJO = response.body();
                if(uploadImagePOJO.getSuccess()){
                    final TextView textView = (TextView)findViewById(photosCounter);
                    textView.setText(uploadImagePOJO.getResults().getHttpPath());
                    textView.setTextColor(Color.BLACK);
                    textView.setEnabled(false);
                    /*JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse("{\"a\": \"A\"}").getAsJsonObject();*/

                    /*JsonObject jsonObject = parser.parse("{\n" +
                            "\"originalname\" : "+uploadImagePOJO.getResults().getOriginalname()+", \n" +
                            "\"filename\" : "+uploadImagePOJO.getResults().getFilename()+", \n" +
                            "\"http_path\" : "+uploadImagePOJO.getResults().getHttpPath()+", \n" +
                            "\"type\" : \"Image\", \n" +
                            "\"size\" : "+uploadImagePOJO.getResults().getSize()+" \n" +
                            "}\n").getAsJsonObject();*/

                    //photosContent.add(photosCounter, jsonObject);
                    final String originalName = uploadImagePOJO.getResults().getOriginalname();
                    final String fileName = uploadImagePOJO.getResults().getFilename();
                    final String httpPath = uploadImagePOJO.getResults().getHttpPath();
                    final String size = uploadImagePOJO.getResults().getSize().toString();

                    photoFiles.add("{\n" +
                            "\"originalname\" : \""+originalName.toString()+"\", \n" +
                            "\"filename\" : \""+fileName.toString()+"\", \n" +
                            "\"http_path\" : \""+httpPath.toString()+"\", \n" +
                            "\"type\" : \"Image\", \n" +
                            "\"size\" : "+size+" \n" +
                            "}\n");

                    //photoFiles.add("{\\\"a\\\": \\\"A\\\"}");

                    //photosContent.add(photosCounter,jsonObject);
                    photosCounter++;
                    /*try {
                        jsonObject.put("originalname",uploadImagePOJO.getResults().getOriginalname());
                        jsonObject.put("filename",uploadImagePOJO.getResults().getFilename());
                        jsonObject.put("http_path",uploadImagePOJO.getResults().getHttpPath());
                        jsonObject.put("type","Image");
                        jsonObject.put("size",uploadImagePOJO.getResults().getSize());
                        Log.d("RAW JSON OBJECT",jsonObject.toString());
                        photosContent.add(photosCounter,jsonObject);
                        photosCounter++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }
            }

            @Override
            public void onFailure(Call<UploadImagePOJO> call, Throwable t) {
                Log.e("ERROR UPLOAD: ",call.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            android.net.Uri selectedImage = data.getData();
            uploadFile(selectedImage);

        }
    }
}
