package com.example.recipies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Distribution;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import adapters.SnapHelperOneByOne;
import adapters.StepAdapter;
import models.ComplexRecipie;

public class ShowRecipie extends AppCompatActivity {
    ComplexRecipie complexRecipie;
    RecyclerView recyclerView;
    StepAdapter stepAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipie);

        recyclerView = findViewById(R.id.step_recycler_view);

        String complexRecipieRef = getIntent().getStringExtra("complexRecipieRef");
        complexRecipieRef = complexRecipieRef.split("/")[ complexRecipieRef.split("/").length -1];
        FirebaseFirestore.getInstance().collection("recipies").document(complexRecipieRef).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                complexRecipie = documentSnapshot.toObject(ComplexRecipie.class);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
                linearSnapHelper.attachToRecyclerView(recyclerView);
                recyclerView.setLayoutManager(linearLayoutManager);
                stepAdapter = new StepAdapter(getApplicationContext(),complexRecipie);
                recyclerView.setAdapter(stepAdapter);
                show();
            }
        });
    }

    void show(){
        getSupportActionBar().setTitle(complexRecipie.getName());
        LinearLayout mainLinearLayout = findViewById(R.id.main_window);



        for(int index =0;index < complexRecipie.getIngredient_name().size();index++){
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView quantity = new TextView(this);
            LinearLayout.LayoutParams quantityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            quantityParams.setMargins(5,5,5,5);
            quantity.setLayoutParams(quantityParams);
            quantity.setText(String.valueOf(complexRecipie.getQuantity().get(index)));
            quantity.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

            TextView unit = new TextView(this);
            LinearLayout.LayoutParams unitParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            unitParams.setMargins(5,5,5,5);
            unit.setLayoutParams(unitParams);
            unit.setText(complexRecipie.getUnit().get(index));
            unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

            TextView name = new TextView(this);
            LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            nameParams.setMargins(5,5,5,5);
            name.setLayoutParams(nameParams);
            name.setText(complexRecipie.getIngredient_name().get(index));
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

            linearLayout.addView(quantity);
            linearLayout.addView(unit);
            linearLayout.addView(name);

            mainLinearLayout.addView(linearLayout);
        }


    }
    int pxToDp (int px){
        return (int)(px * (this.getResources().getDisplayMetrics().xdpi/ DisplayMetrics.DENSITY_DEFAULT));
    }


}