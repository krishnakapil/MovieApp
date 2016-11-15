package test.cone.com.androidtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import test.cone.com.androidtest.model.Media;
import test.cone.com.androidtest.model.MediaResponse;

public class HomeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MediaResponse> {

    private static final String EXTRA_PAGE = "extra_page";
    private ProgressBar progressBar;
    private EmptyRecyclerView recyclerView;

    private int type;
    private String query;
    private ArrayList<Media> media;
    private int position;
    private int currentPage = 1;
    private int totalPages;

    private MediaAdapter mediaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadResources(savedInstanceState);
    }

    private void loadResources(Bundle savedInstanceState) {
        progressBar = (ProgressBar) findViewById(R.id.progrss_bar);
        recyclerView = (EmptyRecyclerView) findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setEmptyView(findViewById(R.id.empty_view));

        if (media == null) {
            loadData(currentPage);
        }
    }

    private void loadData(int page) {
        Bundle bundle = new Bundle(1);
        bundle.putInt(EXTRA_PAGE, page);
        getSupportLoaderManager().restartLoader(this.type, bundle, this);
        if (page == 1) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public Loader<MediaResponse> onCreateLoader(int id, Bundle args) {
        return new MediaLoader(HomeActivity.this, args.getInt(EXTRA_PAGE));
    }

    @Override
    public void onLoadFinished(Loader<MediaResponse> loader, MediaResponse data) {
        getSupportLoaderManager().destroyLoader(this.type);
        ArrayList<Media> results = null;
        if (data != null && data.getResults() != null) {
            results = new ArrayList<>(Arrays.asList(data.getResults()));
            if (media == null) {
                media = results;
            } else {
                media.addAll(results);
            }
            currentPage = data.getPage();
            totalPages = data.getTotalPages();
        }

        populateView();
    }

    private void populateView() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        if (mediaAdapter == null) {
            mediaAdapter = new MediaAdapter(media, HomeActivity.this, currentPage, totalPages);
            recyclerView.setAdapter(mediaAdapter);
        } else {
            mediaAdapter.notifyPageChange(currentPage);
        }
        mediaAdapter.setListner(new MediaAdapter.MediaAdapterInterface() {
            @Override
            public void loadNextPage(int page) {
                loadData(page);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<MediaResponse> loader) {

    }

    public static class MediaLoader extends AsyncTaskLoader<MediaResponse> {
        private int page;

        public MediaLoader(Context context, int page) {
            super(context);
            this.page = page;
        }

        @Override
        public MediaResponse loadInBackground() {
            try {
                return ServiceManager.getMediaList(page);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }
}
