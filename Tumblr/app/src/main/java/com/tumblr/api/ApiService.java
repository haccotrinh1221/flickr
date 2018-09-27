package com.tumblr.api;

import com.tumblr.model.PhotosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {
    String TAG = "ApiService";
    String FLICKR_SEARCH = "rest/?method=flickr.photos.search&api_key=3363f3ec8a3185c5bd9d9650a8fd3c57&per_page=20&format=json&nojsoncallback=1&extras=url_m,url_t,url_o";
    String FLICKR_PHOTO_DETAIL = "rest/?method=flickr.photos.getInfo&api_key=3363f3ec8a3185c5bd9d9650a8fd3c57&format=json&nojsoncallback=1";


    @GET(FLICKR_SEARCH)
    Call<PhotosResponse> searchPhotos(@Query("text") String searchString, @Query("page") int currentPage);

    @GET(FLICKR_PHOTO_DETAIL)
    Call<PhotosResponse> getPhotoDetail(@Query("photo_id") String photoId);

}

