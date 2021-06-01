package adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipies.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import models.BaseRecipe;

public class IngredientsAdderAdapter extends RecyclerView.Adapter<IngredientsAdderAdapter.ViewHolder>{
    Context context;
    List<Integer> quantity_list;
    List<String> unit_list;
    List<String> ingredient;
    List<Integer> spinner_position_list;
    public int item_count =1;

    public IngredientsAdderAdapter(Context context, List<Integer> quantity_list, List<String> unit_list, List<String> ingredient,List<Integer> spinner_position_list) {
        this.context = context;
        this.quantity_list = quantity_list;
        this.unit_list = unit_list;
        this.ingredient = ingredient;
        this.spinner_position_list = spinner_position_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_input,parent,false);
        return new IngredientsAdderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.list_position = position;
        holder.quantity.getEditText().setText(String.valueOf(quantity_list.get(position)));
        holder.unit_choice.setSelection(spinner_position_list.get(position));
        holder.name.getEditText().setText(ingredient.get(position));
    }

    @Override
    public int getItemCount() {
        return quantity_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextInputLayout quantity;
        TextInputLayout name;
        Spinner unit_choice;
        int list_position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            quantity = itemView.findViewById(R.id.ingredient_quantity);
            quantity.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    quantity_list.set(list_position, Integer.valueOf(s.toString()));
                }
            });

            name = itemView.findViewById(R.id.ingredient_name);
            name.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    ingredient.set(list_position, s.toString());
                }
            });

            unit_choice = itemView.findViewById(R.id.ingredient_unit);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.units,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            unit_choice.setAdapter(adapter);

            unit_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    unit_list.set(list_position, unit_choice.getSelectedItem().toString());
                    spinner_position_list.set(list_position, position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    unit_choice.setSelection(0);
                }
            });

        }
    }
}
