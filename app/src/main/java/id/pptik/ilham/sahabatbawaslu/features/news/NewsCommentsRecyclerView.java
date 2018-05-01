package id.pptik.ilham.sahabatbawaslu.features.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddAnswerPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddSubCommentPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.NewsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.VotePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsCommentsRecyclerView extends RecyclerView.Adapter<NewsCommentsRecyclerView.ViewHolder> {
    public static List<String> usernameList = new ArrayList<String>();
    public static ArrayList<List<String>> usernameSubKomentarList = new ArrayList<List<String>>();
    public static List<String> datePostList= new ArrayList<String>();
    public static ArrayList<List<String>> datePostSubKomentarList= new ArrayList<List<String>>();
    public static List<String> contentPostList= new ArrayList<String>();
    public static ArrayList<List<String>> contentPostSubKomentarList= new ArrayList<List<String>>();
    public static List<String> userPictureProfileList= new ArrayList<String>();
    public static ArrayList<List<String>> userPictureProfileSubKomentarList= new ArrayList<List<String>>();
    public static List<Integer> commentNumbersList= new ArrayList<Integer>();
    public static List<String> commentIdList= new ArrayList<String>();
    public static List<Boolean> statusOpenSubComment = new ArrayList<Boolean>();
    public static List<Boolean> statusOpenTextViewSubComment = new ArrayList<Boolean>();

    private String contentId;
    private String[] username, usernameSubKomentar, datePost, datePostSubKomentar,
            contentPost,contentPostSubKomentar,
            userPictureProfile,userPictureProfileSubKomentar,
            textNumberCommentId;

    private Integer[] textNumberCommentNumbers;

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    private RestServiceInterface restServiceInterface;
    private SharedPreferences sharedPreferences;

    public static final String CONTENT_ID = "";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername, tvUsername2, tvCommentContent, tvCommentContent2,
                tvDatePost, tvDatePost2, tvJumlahKomentar;

        public ImageView ivUserpicture,ivUserpicture2;
        public CardView cardViewCommentLevel0,cardViewCommentLevel1,cardViewSubKomentarTextView;
        public RecyclerView recyclerViewSubComment;

        public Button buttonTambahSubKomentar;
        public EditText editTextTambahSubKomentar;

        private RestServiceInterface restServiceInterface;

        public static Snackbar snackbar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView) itemView.findViewById(R.id.username);
            tvUsername2 = (TextView) itemView.findViewById(R.id.username_2);
            tvDatePost = (TextView) itemView.findViewById(R.id.date_post);
            tvDatePost2 = (TextView) itemView.findViewById(R.id.date_post_2);
            tvCommentContent = (TextView) itemView.findViewById(R.id.comment_content);
            tvCommentContent2 = (TextView) itemView.findViewById(R.id.comment_content_2);
            cardViewCommentLevel0 = (CardView) itemView.findViewById(R.id.card_view_comment_level_0);
            cardViewCommentLevel1 = (CardView) itemView.findViewById(R.id.card_view_comment_level_1);
            cardViewSubKomentarTextView = (CardView) itemView.findViewById(R.id.card_view_sub_komentar_text_view);
            ivUserpicture = (ImageView) itemView.findViewById(R.id.user_picture);
            ivUserpicture2 = (ImageView) itemView.findViewById(R.id.user_picture_2);
            tvJumlahKomentar = (TextView) itemView.findViewById(R.id.text_view_jumlah_komentar);
            recyclerViewSubComment = (RecyclerView) itemView.findViewById(R.id.recycler_view_sub_komentar);
            buttonTambahSubKomentar = (Button) itemView.findViewById(R.id.button_tambah_sub_komen);
            editTextTambahSubKomentar = (EditText) itemView.findViewById(R.id.edit_text_tambah_sub_komentar);
        }
    }

    public NewsCommentsRecyclerView(List<String> usernameListParam, List<String> datePostListParam,
                                    List<String> contentPostListParam, List<String> userPictureProfileListParam,
                                    List<String> commentIdListParam, List<Integer> commentNumbersPostListParam,
                                    ArrayList<List<String>> usernameListParam2, ArrayList<List<String>> datePostListParam2,
                                    ArrayList<List<String>> userPictureProfileListParam2,ArrayList<List<String>> contentPostListParam2,
                                    String contentId) {
        this.contentId = contentId;
        this.usernameList = usernameListParam;
        this.datePostList = datePostListParam;
        this.contentPostList = contentPostListParam;
        this.userPictureProfileList = userPictureProfileListParam;

        this.usernameSubKomentarList = usernameListParam2;
        this.datePostSubKomentarList = datePostListParam2;
        this.contentPostSubKomentarList = contentPostListParam2;
        this.userPictureProfileSubKomentarList = userPictureProfileListParam2;

        this.commentNumbersList = commentNumbersPostListParam;
        this.commentIdList = commentIdListParam;

        username = new String[usernameList.size()];
        datePost = new String[datePostList.size()];
        contentPost = new String[contentPostList.size()];
        userPictureProfile = new String[userPictureProfileList.size()];

        usernameSubKomentar = new String[usernameSubKomentarList.size()];
        datePostSubKomentar = new String[datePostSubKomentarList.size()];
        contentPostSubKomentar = new String[contentPostSubKomentarList.size()];
        userPictureProfileSubKomentar = new String[userPictureProfileSubKomentarList.size()];


        textNumberCommentId = new String[commentIdList.size()];
        textNumberCommentNumbers = new Integer[commentNumbersList.size()];


        username = usernameList.toArray(username);
        datePost = datePostList.toArray(datePost);
        contentPost = contentPostList.toArray(contentPost);
        userPictureProfile = userPictureProfileList.toArray(userPictureProfile);

        /*usernameSubKomentar = usernameSubKomentarList.toArray(usernameSubKomentar);
        datePostSubKomentar = datePostSubKomentarList.toArray(datePostSubKomentar);
        contentPostSubKomentar = contentPostSubKomentarList.toArray(contentPostSubKomentar);
        userPictureProfileSubKomentar = userPictureProfileSubKomentarList.toArray(userPictureProfileSubKomentar);*/

        textNumberCommentId = commentIdList.toArray(textNumberCommentId);
        textNumberCommentNumbers= commentNumbersList.toArray(textNumberCommentNumbers);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news_comments_content, parent, false);

        sharedPreferences = parent.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String access_token = sharedPreferences.getString("accessToken", "abcde");

        if (textNumberCommentNumbers[position] > 0){
            statusOpenSubComment.add(true);
        }else{
            statusOpenSubComment.add(false);
        }

        statusOpenTextViewSubComment.add(false);

        //Identitas pengguna dan resume kegiatan
        holder.tvUsername.setText(username[position]);
        holder.tvDatePost.setText(datePost[position]);
        holder.tvCommentContent.setText(contentPost[position]);
        holder.tvDatePost.setText(datePost[position]);

        holder.ivUserpicture.setImageDrawable(null);
        holder.tvJumlahKomentar.setText("\u2022 "+textNumberCommentNumbers[position]+" Komentar");
        Glide.with(holder.ivUserpicture.getContext()).load(userPictureProfile[position]).into(holder.ivUserpicture);

        holder.recyclerViewSubComment.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.recyclerViewSubComment.setLayoutManager(mLayoutManager);

        mAdapter = new NewsSubCommentsRecyclerView(usernameSubKomentarList.get(position),datePostSubKomentarList.get(position),
                contentPostSubKomentarList.get(position),userPictureProfileSubKomentarList.get(position));

        mAdapter.notifyDataSetChanged();
        holder.recyclerViewSubComment.setAdapter(mAdapter);

        /*RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(holder.tvUsername.getWidth(), 10, 0, 0);
        holder.recyclerViewSubComment.setLayoutParams(params);*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textNumberCommentNumbers[position] > 0){
                if (statusOpenSubComment.get(position)){
                    holder.recyclerViewSubComment.setVisibility(View.VISIBLE);
                    statusOpenSubComment.set(position,false);
                }else if(!statusOpenSubComment.get(position)){
                    holder.recyclerViewSubComment.setVisibility(View.GONE);
                    statusOpenSubComment.set(position,true);
                }
                }

                if(!statusOpenTextViewSubComment.get(position)){
                    holder.cardViewSubKomentarTextView.setVisibility(View.VISIBLE);
                    statusOpenTextViewSubComment.set(position,true);
                }else{
                    holder.cardViewSubKomentarTextView.setVisibility(View.GONE);
                    statusOpenTextViewSubComment.set(position,false);
                }
            }
        });

        holder.buttonTambahSubKomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), holder.editTextTambahSubKomentar.getText(), Toast.LENGTH_SHORT).show();
                addSubComment(v,holder.editTextTambahSubKomentar.getText().toString(),
                        textNumberCommentId[position].toString(),contentId);
            }
        });
    }

    private void addSubComment(final View v, String comment, String commentId, String contentId){
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken", "abcde");

        Call<AddSubCommentPOJO> addSubCommentPOJOCall = restServiceInterface.subCommentCreate(
                comment, commentId, contentId, 2, access_token);

        addSubCommentPOJOCall.enqueue(new Callback<AddSubCommentPOJO>() {
            @Override
            public void onResponse(Call<AddSubCommentPOJO> call, Response<AddSubCommentPOJO> response) {
                AddSubCommentPOJO addSubCommentPOJO = response.body();
                if(addSubCommentPOJO.getSuccess()){
                    Toast.makeText(v.getContext(), addSubCommentPOJO.getRm(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(), addSubCommentPOJO.getRm(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddSubCommentPOJO> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return username.length;
    }
}
