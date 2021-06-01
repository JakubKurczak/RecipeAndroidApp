package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.recipies.R;
import com.example.recipies.ShowRecipie;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import models.BaseRecipe;
import models.ComplexRecipie;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private Context context;
    private ComplexRecipie recipie;

    public StepAdapter(Context context, ComplexRecipie recipie) {
        this.context = context;
        this.recipie = recipie;
    }

    public StepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step,parent,false);
        return new StepAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.ViewHolder holder, int position) {
        String recipie_description = recipie.getStep_desc().get(position);
        String step_img_url = recipie.getStep_pic_url().get(position);
        holder.step_description.setText(recipie_description);
        holder.step_count.setText("Step "+(position+1));

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(step_img_url);
        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap bmp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.step_image.setImageBitmap(bmp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipie.getStep_pic_url().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView step_description;
        ImageView step_image;
        TextView step_count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            step_description = itemView.findViewById(R.id.step_desciption);
            step_image = itemView.findViewById(R.id.step_image);
            step_count = itemView.findViewById(R.id.step_count);
        }

    }
}
