package adapters;

import android.app.Activity;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipies.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class StepAdderAdapter extends RecyclerView.Adapter<StepAdderAdapter.ViewHolder> {
    Context context;
    List<Bitmap> bitmap_list;
    List<String> step_description_list;
    public int item_count =1;

    public StepAdderAdapter(Context context, List<Bitmap> bitmap_list, List<String> step_description_list) {
        this.context = context;
        this.bitmap_list = bitmap_list;
        this.step_description_list = step_description_list;
    }

    @NonNull
    @Override
    public StepAdderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_input_layout,parent,false);
        return new StepAdderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdderAdapter.ViewHolder holder, int position) {
        holder.list_position = position;
        holder.step_count.setText("Step "+(position+1));
        holder.description.getEditText().setText(step_description_list.get(position));
        holder.image.setImageBitmap(bitmap_list.get(position));
    }

    @Override
    public int getItemCount() {
        return step_description_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextInputLayout description;
        ImageView image;
        TextView step_count;
        int list_position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            step_count = itemView.findViewById(R.id.step_count);
            image = itemView.findViewById(R.id.step_1_image);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        ((Activity)context).startActivityForResult(takePictureIntent, 1);
                    } catch (ActivityNotFoundException e) {
                        // display error state to the user
                    }
                }
            });
            image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((Activity)context).startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 3);
                    return true;
                }
            });

            description = itemView.findViewById(R.id.step_1_text_description);
            description.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    step_description_list.set(list_position, s.toString());
                }
            });

        }
    }
}
