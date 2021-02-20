package com.example.demomvvm.roomDataBase;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

class DaisRepository {
    private DaisDao mDaisDao;
    String jsonString;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    DaisRepository(Application application) {
        DaisRoomDatabase db = DaisRoomDatabase.getDatabase(application);
        mDaisDao = db.claimDao();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
//    LiveData<List<Word>> getAllWords() {
//        return mAllWords;
//    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    void insert(Dais dais) {
        new insertAsyncTask(mDaisDao).execute(dais);
    }

    void update(Dais dais) {
        new updateAsyncTask(mDaisDao).execute(dais);
    }

    private static class insertAsyncTask extends AsyncTask<Dais, Void, Void> {
        private DaisDao mAsyncTaskDao;

        insertAsyncTask(DaisDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Dais... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Dais, Void, Void> {
        private DaisDao mAsyncTaskDao;

        updateAsyncTask(DaisDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Dais... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteData() {
        new deletetAsyncTask(mDaisDao).execute();
    }

    public LiveData<String> getJsonData(String screenName) {
        LiveData<String> jsonString = mDaisDao.jsonString(screenName);

        return jsonString;
    }

    private static class deletetAsyncTask extends AsyncTask<Dais, Void, Void> {
        private DaisDao mAsyncTaskDao;

        deletetAsyncTask(DaisDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Dais... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

}
