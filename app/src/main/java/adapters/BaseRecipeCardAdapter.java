package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.storage.StorageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipies.R;
import com.example.recipies.ShowRecipie;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import models.BaseRecipe;

public class BaseRecipeCardAdapter extends RecyclerView.Adapter<BaseRecipeCardAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BaseRecipe> recipes;

    public BaseRecipeCardAdapter(Context context, ArrayList<BaseRecipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public BaseRecipeCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_recipe_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecipeCardAdapter.ViewHolder holder, int position) {
        BaseRecipe model = recipes.get(position);
        holder.baseRecipe =model;
        holder.likesNumber.setText(Integer.toString(model.getLikeNumber()));
        holder.recipeName.setText(model.getName());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(model.getPhoto_url());
        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap bmp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.mainImage.setImageBitmap(bmp);
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
        BaseRecipe baseRecipe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            likesNumber = itemView.findViewById(R.id.like_number);
            mainImage = itemView.findViewById(R.id.main_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowRecipie.class);
                    intent.putExtra("complexRecipieRef",baseRecipe.getRec_ref());
                    context.startActivity(intent);
                }
            });
        }


    }
}
