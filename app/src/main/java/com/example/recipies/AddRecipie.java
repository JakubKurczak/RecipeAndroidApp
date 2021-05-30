package com.example.recipies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.display.DisplayManager;
import android.media.Image;
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
import java.util.ArrayList;
import java.util.List;

import models.BaseRecipe;
import models.ComplexRecipie;
import models.User;

public class AddRecipie extends Activity {

    ArrayList<Spinner> chosen_units = new ArrayList<>();
    ArrayList<TextInputLayout> chosen_quantity = new ArrayList<>();
    ArrayList<TextInputLayout> chosen_name = new ArrayList<>();
    ArrayList<ImageView> step_image= new ArrayList<>();
    ArrayList<EditText> step_description= new ArrayList<>();
    TextInputLayout name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipie);
        name = findViewById(R.id.recipe_name);
        TextInputLayout quantity = findViewById(R.id.ingredient_quantity);
        TextInputLayout name = findViewById(R.id.ingredient_name);
        chosen_quantity.add(quantity);
        chosen_name.add(name);

        Spinner unit_choice = findViewById(R.id.ingredient_unit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.units,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_choice.setAdapter(adapter);
        chosen_units.add(unit_choice);
        unit_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unit_choice.setSelection(position);
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + unit_choice.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        Button button = findViewById(R.id.containedButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredient();
            }
        });

        ImageView imageView = findViewById(R.id.step_1_image);
        this.step_image.add(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
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

        EditText editText = findViewById(R.id.step_1_text);
        step_description.add(editText);
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
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            step_image.get(step_image.size()-1).setImageBitmap(imageBitmap);
        }
    }

    public void addIngredient(){
        LinearLayout linearLayout = new LinearLayout(this);

        LinearLayout.LayoutParams params_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_1.setMargins(pxToDp(15),pxToDp(15),pxToDp(15),pxToDp(15));
        linearLayout.setLayoutParams(params_1);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextInputLayout textInputLayout = new TextInputLayout(this);
        LinearLayout.LayoutParams params_2 = new LinearLayout.LayoutParams(pxToDp(50), LinearLayout.LayoutParams.WRAP_CONTENT);
        textInputLayout.setLayoutParams(params_2);


        TextInputEditText textInputEditText = new TextInputEditText(this);
        LinearLayout.LayoutParams params_3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textInputEditText.setLayoutParams(params_3);

        textInputLayout.addView(textInputEditText);

        chosen_quantity.add(textInputLayout);

        Spinner spinner = new Spinner(this);
        LinearLayout.LayoutParams params_4 = new LinearLayout.LayoutParams(pxToDp(70), LinearLayout.LayoutParams.WRAP_CONTENT);
        params_4.setMargins(pxToDp(5),0,pxToDp(5),0);
        spinner.setLayoutParams(params_4);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.units,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        chosen_units.add(spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + spinner.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextInputLayout textInputLayout_2 = new TextInputLayout(this);
        LinearLayout.LayoutParams params_5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_5.setMargins(pxToDp(5),0,pxToDp(5),0);
        textInputLayout_2.setLayoutParams(params_5);


        TextInputEditText textInputEditText_2 = new TextInputEditText(this);
        LinearLayout.LayoutParams params_6 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textInputEditText_2.setLayoutParams(params_6);

        textInputLayout_2.addView(textInputEditText_2);
        chosen_name.add(textInputLayout_2);

        linearLayout.addView(textInputLayout);
        linearLayout.addView(spinner);
        linearLayout.addView(textInputLayout_2);

        LinearLayout ingredients = findViewById(R.id.ingredients);
        ingredients.addView(linearLayout);

    }

    void addStep(){
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(this);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textViewParams);
        textView.setText("Step "+String.valueOf(step_image.size()+1));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);

        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pxToDp(300));
        imageView.setLayoutParams(imageViewParams);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });



        EditText editText = new EditText(this);
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pxToDp(300));
        editText.setLayoutParams(editTextParams);
        editText.setSingleLine(false);
        editText.setVerticalScrollBarEnabled(true);

        step_description.add(editText);
        step_image.add(imageView);


        linearLayout.addView(textView);
        linearLayout.addView(imageView);
        linearLayout.addView(editText);

        LinearLayout steps = findViewById(R.id.steps);
        steps.addView(linearLayout);
    }

    void onCancel(){
        finish();
    }

    void onFinish(){

        String recipie_name = name.getEditText().getText().toString();
        List<String> units = new ArrayList<>();
        List<Integer> quantity = new ArrayList<>();
        List<String> ingredient = new ArrayList<>();
        List<String> pic_url = new ArrayList<>();
        List<String> step_desc = new ArrayList<>();

        for(Spinner u: chosen_units){
            units.add(u.getSelectedItem().toString());
        }

        for(TextInputLayout l: chosen_quantity){
            quantity.add(Integer.valueOf(l.getEditText().getText().toString()));
        }

        for(TextInputLayout l: chosen_name){
            ingredient.add(l.getEditText().getText().toString());
        }

        for(EditText e: step_description){
            step_desc.add(e.getText().toString());
        }

        for(int index = 0;index < step_image.size();index++){
            ImageView imageView = step_image.get(index);
            String pic_name_url = User.getMail()+ recipie_name +"step_"+String.valueOf(index+1);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(pic_name_url);
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

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

        ComplexRecipie complexRecipie = new ComplexRecipie(recipie_name,quantity,units,ingredient,pic_url,step_desc,0);

        FirebaseFirestore.getInstance().collection("recipies").
                add(complexRecipie).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                BaseRecipe recipe = new BaseRecipe(recipie_name,0,documentReference.getPath(),pic_url.get(0));
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