package com.example.recipies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import adapters.BaseRecipeCardAdapter;
import adapters.ShortCartAdapter;
import models.BaseRecipe;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HorizontalFragmentRecipeList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HorizontalFragmentRecipeList extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<BaseRecipe> recipes = new ArrayList<>(Arrays.asList(new BaseRecipe("Bacon & eggs", 8),
            new BaseRecipe("Scramble eggs", 3), new BaseRecipe("Szakszuka", 41),
            new BaseRecipe("Oatmeal", 12), new BaseRecipe("French toast ", 3),
            new BaseRecipe("Water and slice of bread", 11)));


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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_horizontal_recipe_list, container, false);
        recyclerView = view.findViewById(R.id.horizontal_recipe_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ShortCartAdapter baseRecipeCardAdapter = new ShortCartAdapter(this.getContext(), recipes);
        recyclerView.setAdapter(baseRecipeCardAdapter);
        return view;
    }
}