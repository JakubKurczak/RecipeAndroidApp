package com.example.recipies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import adapters.AddableRecipieCardAdapter;
import adapters.BaseRecipeCardAdapter;
import models.BaseRecipe;
import models.ComplexRecipie;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<BaseRecipe> recipeList=new ArrayList<>();
    AddableRecipieCardAdapter baseRecipeCardAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.search_recipies);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        baseRecipeCardAdapter = new AddableRecipieCardAdapter(this, this.recipeList);
        this.recyclerView.setAdapter(baseRecipeCardAdapter);
        handleIntent(getIntent());
    }

    public void add_recipies(){

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String queryText = intent.getStringExtra(SearchManager.QUERY);
            FirebaseFirestore.getInstance().collection("recipies")
                    .whereGreaterThan("name", queryText)
                    .whereLessThanOrEqualTo("name", queryText+ '\uf8ff')
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc: task.getResult()){
                                ComplexRecipie recipie = doc.toObject(ComplexRecipie.class);
                                BaseRecipe baseRecipe = new BaseRecipe(recipie.getName(),recipie.getLikes(),doc.getId(),recipie.getStep_pic_url().get(0));
                                recipeList.add(baseRecipe);
                                baseRecipeCardAdapter.notifyDataSetChanged();
                            }
                        }
                }
            });
        }
    }
}