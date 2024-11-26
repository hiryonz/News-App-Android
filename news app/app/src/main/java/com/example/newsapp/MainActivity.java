package com.example.newsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<DataArticle> items = new ArrayList<>();

    MyAdapter adapter;
    LinearProgressIndicator progress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        progress = findViewById(R.id.barraProgreso);

        AgregarItemsNews();
        getNews();
    }



    void AgregarItemsNews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(items);
        recyclerView.setAdapter(adapter);
    }

    void CambiarBarraProgreso(boolean mostrar) {
        if(mostrar) {
            progress.setVisibility(View.VISIBLE);
        } else {
            progress.setVisibility(View.INVISIBLE);

        }
    }


    interface NewsService {
        @GET("api/1/news")
        Call<NewsResponse> fetchNews(
                @Query("apikey") String apiKey,
                @Query("country") String country,
                @Query("language") String language
        );
    }

    public void getNews() {
        CambiarBarraProgreso(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsdata.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        NewsService newsService = retrofit.create(NewsService.class);

        Call<NewsResponse> call = newsService.fetchNews("pub_4779970008bd55937989b085f7f9995aaf839", "pa", "es");

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //List<DataArticle> dataArticles = response.body().getResults();

                        runOnUiThread(() -> {
                            CambiarBarraProgreso(false);
                            items = response.body().getResults();
                            adapter.UpdateData(items);
                            adapter.notifyDataSetChanged();

                        });
                }
            }


            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                Log.i("Error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
/*
    void getNews() {
        CambiarBarraProgreso(true);
        NewsApiClient newsApiClient = new NewsApiClient("f3810200047c4932beb56e444c4c4cc4");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .country("us")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        Log.i("article response", response.toString());

                        response.getArticles().forEach((a) -> {
                            Log.i("article title", a.getTitle());
                        });

                        runOnUiThread(() -> {
                            CambiarBarraProgreso(false);
                            items = response.getArticles();
                            adapter.UpdateData(items);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("Error", Objects.requireNonNull(throwable.getMessage()));
                    }
                }
        );
    }*/

}