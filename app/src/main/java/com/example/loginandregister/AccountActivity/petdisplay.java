package com.example.loginandregister.AccountActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loginandregister.R;

import java.util.List;

public class petdisplay extends RecyclerView.Adapter<petdisplay.MyViewHolder> {

    private List<pet_adapter> petlist;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView petName, petAge, petBreed;
        public Button addFavourite;
        public ImageView petImage;

        public MyViewHolder(View view) {
            super(view);
            petName = (TextView) view.findViewById(R.id.rec_txt_petname);
            petAge = (TextView) view.findViewById(R.id.rec_petage);
            petBreed = (TextView) view.findViewById(R.id.recy_petbreed);
            petImage = (ImageView) view.findViewById(R.id.rec_imgpet);
            addFavourite = (Button) view.findViewById(R.id.rec_btn_fav);

        }


    }

    public petdisplay(List<pet_adapter> petlist)
    {
        this.petlist = petlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_petview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        pet_adapter pets = petlist.get(position);
        holder.petName.setText(pets.getPet_name());
        holder.petAge.setText(pets.getPet_age());
        holder.petBreed.setText(pets.getPet_breed());
        holder.petImage.setImageURI(pets.getFilePath());

    }

    @Override
    public int getItemCount() {
        return petlist.size();
    }
}
