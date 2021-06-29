package com.example.recipies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

import adapters.BaseRecipeCardAdapter;
import adapters.ShortCartAdapter;
import models.BaseRecipe;
import models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HorizontalFragmentRecipeList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HorizontalFragmentRecipeList extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<BaseRecipe> recipes = new ArrayList<>();
    ShortCartAdapter baseRecipeCardAdapter;

    public HorizontalFragmentRecipeList() {
        // Required empty public constructor
    }


    public static HorizontalFragmentRecipeList newInstance() {
        HorizontalFragmentRecipeList fragment = new HorizontalFragmentRecipeList();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore.getInstance().collection("users").document(User.getDoc_id())
                .collection("recipies")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    BaseRecipe recipe = dc.getDocument().toObject(BaseRecipe.class);
                                    recipes.add(recipe);
                                    baseRecipeCardAdapter.notifyDataSetChanged();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_horizontal_recipe_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.horizontal_recipe_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        baseRecipeCardAdapter= new ShortCartAdapter(this.getContext(), recipes);
        recyclerView.setAdapter(baseRecipeCardAdapter);
    }

}