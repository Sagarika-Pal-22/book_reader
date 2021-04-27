package com.example.bookreader;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyInterface {


    @FormUrlEncoded
    @POST("register.php")
    Call<String> register(@Field("name") String name, @Field("number") String number,
                          @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<String> login(@Field("number") String number, @Field("password") String password);

    @FormUrlEncoded
    @POST("fetch_profile.php")
    Call<String>fetch_profile(@Field("user_id")String user_id);

    @FormUrlEncoded
    @POST("update_profile.php")
    Call<String> profile_update(@Field("user_id") String user_id,@Field("name") String name,
                                @Field("number") String number, @Field("pincode") String pincode,
                                @Field("address") String address, @Field("locality") String locality, @Field("city") String city,
                                @Field("state") String state);

    @FormUrlEncoded
    @POST("add_address.php")
    Call<String> add_address(@Field("user_id") String user_id, @Field("pin_code") String pin_code,
                          @Field("address") String address, @Field("locality") String locality,
                             @Field("city") String city, @Field("state") String state);

    @GET("fetch_banner_image.php")
    Call<String> banner_fetch();

    @GET("fetch_home_category.php")
    Call<String> home_category_fetch();

    @FormUrlEncoded
    @POST("fetch_home_category_books.php")
    Call<String> fetch_product_by_category(@Field("cat_id") String cat_id);

    @GET("fetch_bestSeller.php")
    Call<String> fetch_best_seller();

    @GET("fetch_publishersChoice.php")
    Call<String> fetch_publisher_choice();

    @GET("fetch_most_liked.php")
    Call<String> fetch_most_liked();

    @GET("fetch_top_hard.php")
    Call<String> fetch_top_hard();

    @GET("fetch_top_digital.php")
    Call<String> fetch_top_digital();

    @GET("fetch_top_audio.php")
    Call<String> fetch_top_audio();

//    @GET("fetch_newRelease.php")
//    Call<String> fetch_new_release();

    @GET("fetch_top_rated.php")
    Call<String> fetch_top_rated();

    @GET("search_booklist.php")
    Call<String> booklist();

    @GET("search_all_books.php")
    Call<String> search_all_books();

    @FormUrlEncoded
    @POST("search_each_books.php")
    Call<String> search_each_books(@Field("id") String id, @Field("type") String type);

    @GET("fetch_genre.php")
    Call<String> fetch_genre();

    @FormUrlEncoded
    @POST("fetch_genre_books.php")
    Call<String> fetch_genre_byGenreIds(@Field("genre_id") String genre_id);

    @FormUrlEncoded
    @POST("fetch_library.php")
    Call<String> fetch_library(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("single_page_fetch.php")
    Call<String> fetch_single_page(@Field("id") String id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("subscription_plans.php")
    Call<String> fetch_subscription_plan(@Field("book_id") String book_id);

    @FormUrlEncoded
    @POST("add_subscription.php")
    Call<String> add_subscription(@Field("user_id") String user_id, @Field("product_id") String product_id, @Field("validity") String validity);

    @FormUrlEncoded
    @POST("fetch_similar_books.php")
    Call<String> fetch_similar(@Field("genre_id") String genre_id, @Field("book_type") String book_type, @Field("p_id") String p_id);

    @FormUrlEncoded
    @POST("review_add.php")
    Call<String> review_add(@Field("user_id") String user_id, @Field("product_id") String product_id,
                            @Field("comment") String comment, @Field("rate") String rate);

    @FormUrlEncoded
    @POST("review_fetch.php")
    Call<String> fetch_review(@Field("p_id") String p_id);

    @FormUrlEncoded
    @POST("add_favourite.php")
    Call<String> add_favourite(@Field("product_id") String product_id, @Field("user_id") String user_id,
                            @Field("status") String status);

    @FormUrlEncoded
    @POST("check_favourite.php")
    Call<String> check_favourite(@Field("product_id") String product_id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("fetch_favourite.php")
    Call<String> fetch_favourite(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("add_to_cart.php")
    Call<String> add_cart(@Field("user_id") String user_id, @Field("product_id") String product_id,
                          @Field("selling_price") String selling_price);

    @FormUrlEncoded
    @POST("fetch_cart.php")
    Call<String> fetch_cart(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("remove_cart.php")
    Call<String> remove_cart(@Field("user_id") String user_id, @Field("product_id") String product_id, @Field("selling_price") String selling_price);

    @FormUrlEncoded
    @POST("notification.php")
    Call<String> fetch_notification(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("change_notification_status.php")
    Call<String> change_notification_status(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("add_order.php")
    Call<String> add_order(@Field("user_id") String user_id, @Field("total_price") String total_price,
                           @Field("payment_type") String payment_type);

    @FormUrlEncoded
    @POST("fetch_orderlist.php")
    Call<String> fetch_order_list(@Field("user_id") String user_id);


}
