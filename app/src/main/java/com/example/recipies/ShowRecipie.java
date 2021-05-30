package com.example.recipies;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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

import models.ComplexRecipie;

public class ShowRecipie extends AppCompatActivity {
    ComplexRecipie complexRecipie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipie);
        String complexRecipieRef = getIntent().getStringExtra("complexRecipieRef");
        complexRecipieRef = complexRecipieRef.split("/")[ complexRecipieRef.split("/").length -1];
        FirebaseFirestore.getInstance().collection("recipies").document(complexRecipieRef).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                complexRecipie = documentSnapshot.toObject(ComplexRecipie.class);
                show();
                Toast.makeText(getApplicationContext(),complexRecipie.getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    void show(){
        getSupportActionBar().setTitle(complexRecipie.getName());
        LinearLayout mainLinearLayout = findViewById(R.id.main_window);

        TextView textView = new TextView(this);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textViewParams);
        textView.setText("Ingredients");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);

        mainLinearLayout.addView(textView);

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

        for(int index =0;index < complexRecipie.getStep_pic_url().size();index++){
            TextView step = new TextView(this);
            LinearLayout.LayoutParams stepParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            step.setLayoutParams(stepParams);
            step.setText("Step" + String.valueOf(index+1));
            step.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);



            TextView description = new TextView(this);
            LinearLayout.LayoutParams descriptionParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            description.setLayoutParams(descriptionParams);
            description.setText(complexRecipie.getStep_desc().get(index));
            description.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pxToDp(300));
            imageView.setLayoutParams(imageViewParams);

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(complexRecipie.getStep_pic_url().get(index));
            final long ONE_MEGABYTE = 1024 * 1024;
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Data for "images/island.jpg" is returns, use this as needed
                    Bitmap bmp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    imageView.setImageBitmap(bmp);
                }
            });

            mainLinearLayout.addView(step);
            mainLinearLayout.addView(imageView);
            mainLinearLayout.addView(description);
        }
    }
    int pxToDp (int px){
        return (int)(px * (this.getResources().getDisplayMetrics().xdpi/ DisplayMetrics.DENSITY_DEFAULT));
    }
}