package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipies.R;
import com.example.recipies.ShowRecipie;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import models.BaseRecipe;
import models.User;

public class AddableRecipieCardAdapter extends RecyclerView.Adapter<AddableRecipieCardAdapter.ViewHolder>{
    private Context context;
    private ArrayList<BaseRecipe> recipes;

    public AddableRecipieCardAdapter(Context context, ArrayList<BaseRecipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public AddableRecipieCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addable_recipie,parent,false);
        return new AddableRecipieCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddableRecipieCardAdapter.ViewHolder holder, int position) {
        BaseRecipe model = recipes.get(position);
        holder.recipe = model;
        holder.likesNumber.setText(Integer.toString(model.getLikeNumber()));
        holder.recipeName.setText(model.getName());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(model.getPhoto_url());
        final long ONE_MEGABYTE = 1024 * 1024*5;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap bmp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.mainImage.setImageBitmap(bmp);
            }
        });
        holder.showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShowRecipie.class);
                intent.putExtra("complexRecipieRef",model.getRec_ref());
                context.startActivity(intent);

            }
        });

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setLikeNumber(model.getLikeNumber()+1);
                FirebaseFirestore.getInstance().collection("users")
                        .document(User.getDoc_id()).collection("likes")
                        .add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        holder.addButton.setEnabled(false);
                        Toast.makeText(context,"Added recipe",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView recipeName;
        TextView likesNumber;
        ImageView mainImage;
        Button addButton;
        Button showButton;
        BaseRecipe recipe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            likesNumber = itemView.findViewById(R.id.like_number);
            mainImage = itemView.findViewById(R.id.main_image);
            addButton = itemView.findViewById(R.id.add_button);
            showButton = itemView.findViewById(R.id.show_button);


        }
    }
}
