package admin.example.foodie.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import admin.example.foodie.Respositories.FoodRepository;
import admin.example.foodie.models.Food;

import java.util.List;

public class FoodsViewModel extends ViewModel {
    private MutableLiveData<List<Food>> mutableLiveData;
    private FoodRepository foodRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        foodRepository = new FoodRepository().getInstance();
        mutableLiveData = foodRepository.getFoods();
    }

    public LiveData<List<Food>> getFoodRepository() {
        return mutableLiveData;
    }
}
