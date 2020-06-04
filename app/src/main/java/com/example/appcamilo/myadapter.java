package com.example.appcamilo;

import android.content.Context;
import android.icu.text.Transliterator;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> implements Filterable {

    Context context;
    ArrayList<blog> profiles;
    ArrayList<blog> profilesfull;
//******************************
    public myadapter (Context c , ArrayList<blog>p)
    {
        context = c;
        profiles = p;
        profilesfull = new ArrayList<>(p);
    }

//******************<**************

//*******************************************

//*********************************************

    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context mcontext = parent.getContext();
        int Layoutidparalistitem = R.layout.myrecycler;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        boolean attachtoparentrapido = false;

        View view = inflater.inflate(Layoutidparalistitem,parent,attachtoparentrapido);

        myviewholder viewHolder = new myviewholder(view);

        return viewHolder;

    }




    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.bind(position);

        holder.producto.setText(profiles.get(position).getProducto());
        holder.descripccion.setText(profiles.get(position).getDescripccion());
    }

    @Override
    public int getItemCount() {

        return profiles.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<blog> filteredList = new ArrayList<>();

            if (constraint==null || constraint.length()==0){

                filteredList.addAll(profilesfull);
            } else {

                String filterPattern = constraint.toString().toLowerCase().trim();

                for (blog item: profilesfull){

                    if (item.getProducto().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
                FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            profiles.clear();
            profiles.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


    //***************************************
    class myviewholder extends RecyclerView.ViewHolder
      {
            TextView producto;
            TextView descripccion;

          public myviewholder(@NonNull View itemView) {
              super(itemView);
              producto=(TextView) itemView.findViewById(R.id.textView24);
              descripccion=(TextView) itemView.findViewById(R.id.textView27);
          }

          public void bind(int listaIndex) {
              producto.setText(String.valueOf(listaIndex));
          }
      }
//***********************************************

}
