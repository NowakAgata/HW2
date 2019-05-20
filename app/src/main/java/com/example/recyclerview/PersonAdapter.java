package com.example.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private ArrayList<Person> people;
    Context thisContext ;


    public PersonAdapter(Context context, ArrayList<Person> list)
    {
        people = list ;
        thisContext = context ;

    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtName ;
        ImageView picView, deleteView ;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            txtName = itemView.findViewById(R.id.tvName);
            picView = itemView.findViewById(R.id.imPic);
            deleteView = itemView.findViewById(R.id.deleteButton);

//            deleteView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    delete_row(getAdapterPosition());
//                    }
//                }
//            );
        }



    }
    @NonNull
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonAdapter.ViewHolder viewHolder, int i) {

        viewHolder.itemView.setTag(i);
        viewHolder.txtName.setText(people.get(i).getName());
        String pic = people.get(i).getPic() ;
        if(pic != null){
            Bitmap cameraImage = PicUtils.decodePic(
                    people.get(i).getPic(),
                    200,
                    200);
            viewHolder.picView.setImageBitmap(cameraImage);


        }else
            viewHolder.picView.setImageResource(R.drawable.avatar_2);

    }

    @Override
    public int getItemCount() {
        return people.size();
    }

}
