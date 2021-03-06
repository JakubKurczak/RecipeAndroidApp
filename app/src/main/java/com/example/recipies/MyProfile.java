package com.example.recipies;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import adapters.BaseRecipeCardAdapter;
import models.BaseRecipe;
import models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfile extends Fragment {

    ArrayList<BaseRecipe> my_recipies = new ArrayList<>();

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    BaseRecipeCardAdapter baseRecipeCardAdapter;

    public MyProfile() {
        // Required empty public constructor

    }


    public static MyProfile newInstance(String doc_id) {
        MyProfile fragment = new MyProfile();
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
                                    boolean is_in_recipies = false;
                                    for(BaseRecipe r: my_recipies)
                                        if (r.getName() == recipe.getName())
                                            is_in_recipies =true;
                                    if(!is_in_recipies)
                                        my_recipies.add(recipe);
                                    recyclerView.getAdapter().notifyDataSetChanged();
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
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floatingActionButton = view.findViewById(R.id.add_recipe);
        recyclerView = view.findViewById(R.id.my_recipies);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);
        baseRecipeCardAdapter = new BaseRecipeCardAdapter(this.getContext(), my_recipies);
        this.recyclerView.setAdapter(baseRecipeCardAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddRecipie.class);
                startActivity(intent);
            }
        });

    }
}