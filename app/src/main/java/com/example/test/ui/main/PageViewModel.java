package com.example.test.ui.main;

import java.util.ArrayList;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<Integer> mCode = Transformations.map(mIndex, new Function<Integer, Integer>() {
        @Override
        public Integer apply(Integer input) {
            if (input == 1) {
                return 150;
            } else if (input == 2){
                return 50;
            }
            return 0;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<Integer> getCode() {
        return mCode;
    }
}