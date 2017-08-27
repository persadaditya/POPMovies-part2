package com.app.phedev.popmovie.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by phedev in 2017.
 */

public class MovieJobService extends JobService {
    private AsyncTask<Void,Void,Void> mFetchMovieTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        mFetchMovieTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();
                MovieSyncTask.syncMovie(context);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(job, false);
            }
        };
        mFetchMovieTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mFetchMovieTask != null){
            mFetchMovieTask.cancel(true);
        }
        return true;
    }
}
