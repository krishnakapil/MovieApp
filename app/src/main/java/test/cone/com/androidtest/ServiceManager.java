package test.cone.com.androidtest;

import android.net.Uri;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import test.cone.com.androidtest.model.Configuration;
import test.cone.com.androidtest.model.GeneralData;
import test.cone.com.androidtest.model.MediaResponse;

public class ServiceManager {
    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String API_KEY = "3171503381140f5ef3bdb4895c3cdc59";

    private static OkHttpClient client = new OkHttpClient();

    private static Gson gson = new Gson();

    public static Configuration configuration;

    public static SparseArray<GeneralData> sGenres;

    public static String makeCall(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.d(ServiceManager.class.getSimpleName(), "Request url " + url);

        Response response = client.newCall(request).execute();
        String json = response.body().string();
        Log.d(ServiceManager.class.getSimpleName(), "Response " + json);
        return json;
    }

    public static void loadConfiguaration() throws IOException {
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
        builder.path("3/configuration");
        builder.appendQueryParameter("api_key", API_KEY);

        String url = builder.build().toString();
        String response = makeCall(url);

        configuration = gson.fromJson(response, Configuration.class);
    }

    public static MediaResponse getMediaList(int page) throws IOException {
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();

        boolean isMovie = true;

        builder.path("3/movie/now_playing");

        builder.appendQueryParameter("api_key", API_KEY);
        builder.appendQueryParameter("page", String.valueOf(page));

        String url = builder.build().toString();
        String response = makeCall(url);

        MediaResponse mediaResponse = gson.fromJson(response, MediaResponse.class);
        mediaResponse.setMediaType(isMovie);
        return mediaResponse;
    }

    public static void getGenreList() throws IOException {
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
        builder.path("3/genre/movie/list");
        builder.appendQueryParameter("api_key", API_KEY);


        String url = builder.build().toString();
        String response = makeCall(url);

        GenreResponse genreResponse = gson.fromJson(response, GenreResponse.class);
        if (sGenres == null) {
            sGenres = new SparseArray<>();
        }

        if (genreResponse != null && genreResponse.getGenres() != null) {

            for (GeneralData genre : genreResponse.getGenres()) {
                sGenres.put(genre.getId(), genre);
            }
        }
    }
}