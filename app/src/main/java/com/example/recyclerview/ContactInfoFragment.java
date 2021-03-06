package com.example.recyclerview;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactInfoFragment extends Fragment {

    private static int maxWidth = -1;
    private static int maxHeight = -1;
    public ContactInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_contact_info, container, false);

    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        maxWidth = 200;
        maxHeight = 200;
        Intent intent = getActivity().getIntent();
        if(intent != null){
            int i = intent.getIntExtra("TAG", 1);
            displayPerson(i);
        }
    }

     public void displayPerson(int i){
        FragmentActivity activity = getActivity();
        final ImageView ivPic = activity.findViewById(R.id.iviPic);
        TextView tvName = activity.findViewById(R.id.tviName);
        TextView tvDate = activity.findViewById(R.id.tviDate);
        TextView tvDesc = activity.findViewById(R.id.tviDesc);

        final Person person = ApplicationClass.people.get(i);

        tvName.setText(person.getName());
        tvDate.setText(person.getDate());
        tvDesc.setText(person.getDescription());

        if(maxWidth == -1 || maxHeight == -1){
            maxWidth = 100;
            maxHeight = 100;
        }
        if(person.getPic() != null && !person.getPic().isEmpty()) {
            Handler handler = new Handler() ;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bitmap cameraImage = PicUtils.decodePic(person.getPic(),
                            maxWidth,
                            maxHeight);
                    ivPic.setImageBitmap(cameraImage);
                }
            },200);
        }
        else
            ivPic.setImageResource(R.drawable.avatar_2);

    }


}
