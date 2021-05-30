package com.example.recipies;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapters.BaseRecipeCardAdapter;
import models.BaseRecipe;
import models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseRecipeListView#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.R)
public class BaseRecipeListView extends Fragment {
    private RecyclerView recyclerView;
    ArrayList<BaseRecipe> recipes = new ArrayList<>();
    BaseRecipeCardAdapter baseRecipeCardAdapter;
    public BaseRecipeListView() {
        // Required empty public constructor
    }


    public static BaseRecipeListView newInstance() {
        BaseRecipeListView fragment = new BaseRecipeListView();
       

        FirebaseFirestore.getInstance().collection("users").document(User.getDoc_id())
                .collection("likes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    BaseRecipe recipe = dc.getDocument().toObject(BaseRecipe.class);
                                    fragment.recipes.add(recipe);
                                    fragment.baseRecipeCardAdapter.notifyDataSetChanged();
                                    break;
                                case MODIFIED:
                                    break;
                                case REMOVED:
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_base_recipe_list_view, container, false);
        recyclerView = view.findViewById(R.id.recipe_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        baseRecipeCardAdapter = new BaseRecipeCardAdapter(this.getContext(), recipes);
        recyclerView.setAdapter(baseRecipeCardAdapter);
        return view;
    }
}