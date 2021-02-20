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

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

public class DaisViewModel extends AndroidViewModel {

    private DaisRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    public DaisViewModel(Application application) {
        super(application);
        mRepository = new DaisRepository(application);
    }

    /**
     * @param dais insert data to room db
     */
    public void insert(Dais dais) {
        mRepository.insert(dais);
    }

    /**
     * delete data from room db
     */
    public void delete() {
        mRepository.deleteData();
    }

    public void update(Dais dais) {
        mRepository.update(dais);
    }

    /**
     * @param screenName get data from room db
     */
    public LiveData<String> getJsonData(String screenName) {
        return mRepository.getJsonData(screenName);
    }

}