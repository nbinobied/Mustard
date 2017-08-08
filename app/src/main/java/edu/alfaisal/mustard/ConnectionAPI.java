package edu.alfaisal.mustard;

import retrofit.http.Multipart;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

public interface ConnectionAPI {
    @Multipart
    //POST Login: attempting to login
    @Headers({"Content-Type : application/json"})
    @POST("/oauth/token")
    void login(@Header ("Authorization") String autho, @Part("username") String username, @Part("password") String password, @Part ("grant_type") String grantType, Callback<Token> token);
    //USER INFORMATION
    //GET User info: retrieve the user information
    @Headers({"Content-Type : application/json"})
    @GET("/v1/users/")
    void info(@Header ("Authorization") String accessToken, Callback<User> user);
    //POST update info: updating the user information
    //
    //POSTS
    //GET Posts: retrieve the available posts in the server
    @GET("/v1/posts/")
    void fetch(@Header ("Authorization") String accessToken, Callback<List<Post>> postList);
    //POST posts: attempt to post
    @Multipart
    @Headers({"Content-Type : multipart/form-data j"})
    @POST("/v1/posts")
    void post(@Header ("Authorization") String accessToken, @Part("post_image")TypedFile image, @Part("post_caption")String caption, Callback<Post> post);
}
