package test.cone.com.androidtest;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new ConfigurationLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public static class ConfigurationLoader extends AsyncTaskLoader<Void> {

        public ConfigurationLoader(Context context) {
            super(context);
        }

        @Override
        public Void loadInBackground() {
            try {
                ServiceManager.loadConfiguaration();
                ServiceManager.getGenreList();
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }
}

