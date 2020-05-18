package admin.example.foodie.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import admin.example.foodie.Respositories.UserRepository;
import admin.example.foodie.models.ResponseUser;

public class RestaurantsViewModel extends ViewModel {

    private MutableLiveData<ResponseUser> mutableLiveData;
    private UserRepository userRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        userRepository = new UserRepository().getInstance();
        mutableLiveData = userRepository.getUser();
    }

    public LiveData<ResponseUser> getRestaurantRepository() {
        return mutableLiveData;
    }

}
