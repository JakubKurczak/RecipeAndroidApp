package com.example.recipies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.display.DisplayManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapters.IngredientsAdderAdapter;
import adapters.StepAdderAdapter;
import models.BaseRecipe;
import models.ComplexRecipie;
import models.User;

public class AddRecipie extends AppCompatActivity {

    List<Integer> quantity = new ArrayList<>(Arrays.asList(0));
    List<String> units= new ArrayList<>(Arrays.asList(""));
    List<String> ingredient= new ArrayList<>(Arrays.asList(""));
    List<String> step_description_list = new ArrayList<>(Arrays.asList(""));
    List<Bitmap> bitmap_list;
    List<Integer> spinner_position_list = new ArrayList<>(Arrays.asList(0));
    RecyclerView ingredients_recycler_view;
    IngredientsAdderAdapter ingredientsAdderAdapter;
    TextInputLayout name;

    RecyclerView steps_recycler_view;
    StepAdderAdapter stepAdderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipie);
        bitmap_list = new ArrayList<>(Arrays.asList(
                BitmapFactory.decodeResource(getResources(),R.drawable.ic_baseline_add_24)));
        name = findViewById(R.id.recipe_name);

        ingredientsAdderAdapter = new IngredientsAdderAdapter(
                getApplicationContext(),
                quantity,
                units,
                ingredient,
                spinner_position_list
                );

        ingredients_recycler_view = findViewById(R.id.ingredient_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        ingredients_recycler_view.setLayoutManager(linearLayoutManager);
        ingredients_recycler_view.setAdapter(ingredientsAdderAdapter);

        Button button = findViewById(R.id.containedButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredient();
            }
        });


        stepAdderAdapter = new StepAdderAdapter(
                this,
                bitmap_list,
                step_description_list
        );

        steps_recycler_view = findViewById(R.id.step_input_view);
        LinearLayoutManager linearLayoutManagerForSteps = new LinearLayoutManager(getApplicationContext());
        steps_recycler_view.setLayoutManager(linearLayoutManagerForSteps);
        steps_recycler_view.setAdapter(stepAdderAdapter);

        Button finish = findViewById(R.id.finish);
        Button cancel = findViewById(R.id.cancel);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel();
            }
        });

        Button add_step = findViewById(R.id.add_step);
        add_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStep();
            }
        });


    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, 1);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmap_list.set(bitmap_list.size() -1,imageBitmap);
            stepAdderAdapter.notifyDataSetChanged();
        }else if(requestCode == 3 && resultCode == RESULT_OK){

            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                bitmap_list.set(bitmap_list.size() -1,bitmap);
                stepAdderAdapter.notifyDataSetChanged();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void addIngredient(){
        quantity.add(0);
        units.add("");
        ingredient.add("");
        spinner_position_list.add(0);
        ingredientsAdderAdapter.item_count += 1;
        ingredientsAdderAdapter.notifyDataSetChanged();
    }

    void addStep(){
        bitmap_list.add(BitmapFactory.decodeResource(getResources(),R.drawable.ic_baseline_add_24));
        step_description_list.add("");
        stepAdderAdapter.item_count += 1;
        stepAdderAdapter.notifyDataSetChanged();
    }

    void onCancel(){
        finish();
    }

    void onFinish(){

        String recipie_name = name.getEditText().getText().toString();
        List<String> pic_url = new ArrayList<>();




        for(int index = 0;index < bitmap_list.size();index++){
            String pic_name_url = User.getMail()+ recipie_name +"step_"+String.valueOf(index+1);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(pic_name_url);
            Bitmap bitmap = bitmap_list.get(index);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = storageReference.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(),"failed "+pic_name_url,Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Toast.makeText(getApplicationContext(),"uploaded "+pic_name_url,Toast.LENGTH_LONG).show();

                }
            });

            pic_url.add(pic_name_url);
        }

        ComplexRecipie complexRecipie = new ComplexRecipie(recipie_name,quantity,units,ingredient,pic_url,step_description_list,0);

        FirebaseFirestore.getInstance().collection("recipies").
                add(complexRecipie).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                BaseRecipe recipe = new BaseRecipe(recipie_name,0,documentReference.getPath(),pic_url.get(pic_url.size()-1));
                FirebaseFirestore.getInstance().collection("users").document(User.getDoc_id())
                        .collection("recipies").add(recipe).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                });
            }
        });

        finish();
    }

    int pxToDp (int px){
        return (int)(px * (this.getResources().getDisplayMetrics().xdpi/ DisplayMetrics.DENSITY_DEFAULT));
    }
}