package id.pptik.ilham.sahabatbawaslu.networks;

import java.util.List;
import java.util.Map;

import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddCommentPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddForumPojo;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddNewsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.CommentsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.DashboardPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ForumsListPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.LeaderboardPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.LoginPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialDetailPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialsListPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.NewsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ProvincesPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.SignUpPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.VotePOJO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Ilham on 01/03/18.
 */

public interface RestServiceInterface {
    //Esensial
    @Headers({
        "Content-Type: application/x-www-form-urlencoded",
        "current_device: test1",
        "current_number: test1",
        "carrier: test1"
    })
    @FormUrlEncoded
    @POST("users/signin")
    Call<LoginPOJO> userLogin(@Field("field") String field, @Field("force") Boolean force,
                               @Field("password") String password, @Field("app_id") Integer app_id);

    @FormUrlEncoded
    @POST("users/leaderboards")
    Call<LeaderboardPOJO> leaderboard(@Field("Skip") int skip, @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("users/simple/signup")
    Call<SignUpPOJO> userSignUp(@Field("Username") String username, @Field("PhoneNumber") String phone_number,
                               @Field("Email") String email, @Field("Password") String password,
                                @Field("ReferenceCode") String reference_code,
                                @Field("SignupType") int signup_type);

    //Materi
    @FormUrlEncoded
    @POST("material/list")
    Call<MaterialsListPOJO> materialsList(@Field("Skip") int skip, @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("material/detail")
    Call<MaterialDetailPOJO> materialDetail(@Field("MaterialID") String materialId, @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("material/search/title")
    Call<MaterialsListPOJO> materialsSearchTitle(@Field("Skip") int skip,
                                                 @Field("SearchString") String search_string, @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("material/filter/content")
    Call<MaterialsListPOJO> materialsSortBy(@Field("Skip") int skip,
                                                 @Field("Type") int sort_type, @Header("access_token") String access_token);
    //Berita
    @FormUrlEncoded
    @POST("dashboard")
    Call<DashboardPOJO> dashboard(@Field("Skip") int skip, @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("dashboard/search/title")
    Call<DashboardPOJO> dashboardSearchTitle(@Field("Skip") int skip,
                                             @Field("SearchString") String search_string, @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("dashboard/sort/by")
    Call<DashboardPOJO> dashboardSortBy(@Field("Skip") int skip,
                                             @Field("SortType") int sort_type,
                                        @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("dashboard/filter/content")
    Call<DashboardPOJO> dashboardFilterByContent(@Field("Skip") int skip,
                                             @Field("ContentCode") int content_code,
                                        @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("dashboard/vote")
    Call<VotePOJO> voteAction(@Field("ContentID") String contentID,
                              @Field("ActivityCode") int activityCode,
                              @Field("ContentCode") int contentCode,
                              @Field("Title") String title,
                              @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("news/create/text")
    Call<AddNewsPOJO> newsCreateText(@Field("Content") String content,
                                     @Header("access_token") String access_token);
    @FormUrlEncoded
    @POST("news/detail")
    Call<NewsPOJO> newsDetail(@Field("NewsID") String newsID,
                              @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("comments/list")
    Call<CommentsPOJO> commentsList(@Field("ContentID") String newsID,
                                    @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("comments/create")
    Call<AddCommentPOJO> commentCreate(@Field("Comment") String comment,
                                       @Field("ContentID") String contentId,
                                       @Field("Type") int type,
                                       @Header("access_token") String access_token);
    //Forum
    @FormUrlEncoded
    @POST("forums/list")
    Call<ForumsListPOJO> forumsList(@Field("Skip") int skip,
                                    @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("forums/create")
    Call<AddForumPojo> createForum(@Field("Title") String title,
                                   @FieldMap Map<String, String> hashtags,
                                   @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("forums/list/search")
    Call<ForumsListPOJO> searchForum(@Field("SearchString") String searchString,
                                   @Header("access_token") String access_token);
}
