package id.pptik.ilham.sahabatbawaslu.features.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.models.NewsModel;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddForumPojo;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddNewsMediaPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddNewsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ProfilePOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.UpdateDPPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.UploadImagePOJO;
import id.pptik.ilham.sahabatbawaslu.utils.FileUtils;
import id.pptik.ilham.sahabatbawaslu.view_models.NewsViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

import static android.content.Context.SEARCH_SERVICE;
public class ProfileUserFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.user_profile_email)TextView textViewEmail;
    @BindView(R.id.user_profile_name)TextView textViewUsername;
    @BindView(R.id.user_profile_phone)TextView textViewPhone;
    @BindView(R.id.profile_poin)TextView textViewPoin;
    @BindView(R.id.user_profile_photo)ImageButton IButtonPhoto;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    public RestServiceInterface restServiceInterface;
    public ProfileUserFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile_detail, container, false);

        progressDialog = new ProgressDialog(view.getContext());
        ButterKnife.bind(this,view);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken", "abcde");
        final String role=sharedPreferences.getString("role","2");
        getProfileDetail(access_token,view.getContext(),role);
        IButtonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), 100);

            }
        });
        return view;
    }
    private void getProfileDetail(String accessToken, final Context context, final String role) {
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        if (RestServiceClass.isNetworkAvailable(context)) {
            Call<ProfilePOJO> profilePOJOCall = restServiceInterface.profile(accessToken,"bebasla ya");
            profilePOJOCall.enqueue(new Callback<ProfilePOJO>() {
                @Override
                public void onResponse(Call<ProfilePOJO> call, Response<ProfilePOJO> response) {
                    ProfilePOJO profilePOJO = response.body();
                    if (Integer.parseInt(role)==2){
                        textViewPoin.setText(profilePOJO.getResults().getPoin().toString());
                    }else {
                        textViewPoin.setText(profilePOJO.getResults().getLeaderPoin().toString());
                    }
                    textViewEmail.setText(profilePOJO.getResults().getEmail());
                    textViewUsername.setText(profilePOJO.getResults().getUsername());
                    textViewPhone.setText(profilePOJO.getResults().getPhoneNumber());

                    IButtonPhoto.setImageDrawable(null);
                    Glide.with(IButtonPhoto.getContext()).load(profilePOJO.getResults().getDisplayPicture()).into(IButtonPhoto);
                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ProfilePOJO> call, Throwable t) {
                    progressDialog.setProgress(100);
                    progressDialog.dismiss();

                    Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            progressDialog.setProgress(100);
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setMessage(R.string.pastikan_internet_label)
                    .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            android.net.Uri selectedImage = data.getData();

            uploadFile(selectedImage,getContext());

        }
    }
    private void uploadFile(final Uri fileUri, final Context context){
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();
        File originalFile = FileUtils.getFile(context,fileUri);
        RequestBody filePart = RequestBody.create(MediaType.parse(context.getContentResolver().getType(fileUri)),originalFile);
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

                    final String httpPath = uploadImagePOJO.getResults().getHttpPath();
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                    final String access_token = sharedPreferences.getString("accessToken", "abcde");

                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    Call<UpdateDPPOJO> updateFCMIdPOJOCall = restServiceInterface.UPDATE_DPPOJO_CALL(access_token,httpPath);
                    updateFCMIdPOJOCall.enqueue(new Callback<UpdateDPPOJO>() {
                        @Override
                        public void onResponse(Call<UpdateDPPOJO> call, Response<UpdateDPPOJO> response) {
                            UpdateDPPOJO updateFCMIdPOJO = response.body();
                            Log.d("UPDATE DP RESPONSE",updateFCMIdPOJO.getRm());
                            progressDialog.setProgress(100);
                            progressDialog.dismiss();

                            IButtonPhoto.setImageDrawable(null);
                            Glide.with(IButtonPhoto.getContext()).load(fileUri).into(IButtonPhoto);


                            Toast.makeText(context, "Success Change Display Picture", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<UpdateDPPOJO> call, Throwable t) {
                            progressDialog.setProgress(100);
                            progressDialog.dismiss();

                            Toast.makeText(context, "Failed Change Display Picture", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<UploadImagePOJO> call, Throwable t) {
                progressDialog.setProgress(100);
                progressDialog.dismiss();
                Toast.makeText(context, "Failed Upload Display Picture", Toast.LENGTH_SHORT).show();
                Log.e("ERROR UPLOAD: ",call.toString());
            }
        });

    }
}
