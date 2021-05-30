package com.example.recipies;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import models.BaseRecipe;
import models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFeed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFeed extends Fragment {

    BaseRecipeListView recentlyAdded;
    HorizontalFragmentRecipeList recommendedRecipes;


    public MyFeed() {
        // Required empty public constructor
    }


    public static MyFeed newInstance() {
        MyFeed fragment = new MyFeed();

        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recentlyAdded = BaseRecipeListView.newInstance();
        recommendedRecipes = HorizontalFragmentRecipeList.newInstance();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.vertical_fragment, recentlyAdded)
                .commit();

        getChildFragmentManager().beginTransaction()
                .replace(R.id.horizontal_fragment, recommendedRecipes)
                .commit();
    }

    public BaseRecipeListView getRecentlyAdded() {
        return recentlyAdded;
    }

    public void setRecentlyAdded(BaseRecipeListView recentlyAdded) {
        this.recentlyAdded = recentlyAdded;
    }

    public HorizontalFragmentRecipeList getRecommendedRecipes() {
        return recommendedRecipes;
    }

    public void setRecommendedRecipes(HorizontalFragmentRecipeList recommendedRecipes) {
        this.recommendedRecipes = recommendedRecipes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_feed, container, false);
    }
}