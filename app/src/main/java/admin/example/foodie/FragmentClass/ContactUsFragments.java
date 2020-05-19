package admin.example.foodie.FragmentClass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.example.foodie.R;


public class ContactUsFragments extends Fragment {

  View rootview;
    public ContactUsFragments() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview=inflater.inflate(R.layout.fragment_contactus_fragments , container , false);




        return rootview;
    }
}
