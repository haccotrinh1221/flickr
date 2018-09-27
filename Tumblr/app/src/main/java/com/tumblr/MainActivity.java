package com.tumblr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tumblr.api.ApiService;
import com.tumblr.model.Photo;
import com.tumblr.model.PhotosResponse;
import com.tumblr.utils.ApiUtils;
import com.tumblr.view.adapter.PhotoAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ApiService mService;
    private RecyclerView mRecyclerView;
    private PhotoAdapter mAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            loadPhoto();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mService=  ApiUtils.getService();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_photo);
        mAdapter = new PhotoAdapter(this, new ArrayList<Photo>(0), new PhotoAdapter.PostItemListener() {

            @Override
            public void onPostClick(String id) {
                Toast.makeText(MainActivity.this, "Post id is" + id, Toast.LENGTH_SHORT).show();

            }

        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        loadPhoto();
    }
    public void loadPhoto() {
        mService.searchPhotos(randomizer(3),20).enqueue(new Callback<PhotosResponse>() {
        @Override
        public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
            if(response.isSuccessful()) {
                mAdapter.updateAnswers(response.body().getPhotos().getPhoto());
            }else {
                int statusCode  = response.code();
            }
        }

        @Override
        public void onFailure(Call<PhotosResponse> call, Throwable t) {
//                showErrorMessage();
//                Log.d("MainActivity", "error loading from API");

        }
    });

    }
    private String randomizer(int length){
        char i[] = new char[length];
        for (int j = 0; j < length; j++) {
            i[j] =(char)((int)5*Math.random()+(int)'a');
        }
        return new String(i);
    }

}
