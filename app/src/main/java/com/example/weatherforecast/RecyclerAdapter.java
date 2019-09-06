package com.example.weatherforecast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
   List<Double> temp;
    List<String> ID;
    int dayOfWeek;

    public RecyclerAdapter( List<Double> temp, int dayOfWeek, List<String> ID ){
        this.temp = temp;
        this.dayOfWeek = dayOfWeek;
        this.ID = ID;

    }



    @Override
    public RecyclerViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder( RecyclerViewHolder recyclerViewHolder, int i) {

        String weekDayName;
        switch ((dayOfWeek+i+1)%7){
            case 1:
                weekDayName ="Sun";
                break;
            case 2:
                weekDayName ="Mon";
                break;
            case 3:
                weekDayName ="Tue";
                break;
            case 4:
                weekDayName ="Wed";
                break;
            case 5:
                weekDayName ="Thu";
                break;
            case 6:
                weekDayName ="Fri";
                break;
            case 0:
                weekDayName ="Sat";
                break;
            default:
                weekDayName ="Sun";
        }

        recyclerViewHolder.txtDayOfWeek.setText(weekDayName );
        recyclerViewHolder.txtTemp.setText(Math.round(temp.get(i))+"°C");

        switch (Integer.valueOf(ID.get(i))){

            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 231:
            case 232:
                recyclerViewHolder.imgID.setImageResource(R.drawable.d11);
                break;
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 321:
            case 520:
            case 521:
            case 522:
            case 531:
                recyclerViewHolder.imgID.setImageResource(R.drawable.d09);
                break;
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
                recyclerViewHolder.imgID.setImageResource(R.drawable.d10);
                break;
            case 511:
            case 600:
            case 601:
            case 602:
            case 611:
            case 612:
            case 613:
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                recyclerViewHolder.imgID.setImageResource(R.drawable.d13);
                break;
            case 701:
            case 711:
            case 721:
            case 731:
            case 741:
            case 751:
            case 761:
            case 762:
            case 771:
            case 781:
                recyclerViewHolder.imgID.setImageResource(R.drawable.d50);
                break;
            case 800:
                recyclerViewHolder.imgID.setImageResource(R.drawable.clearsky);
                break;
            case 801:
                recyclerViewHolder.imgID.setImageResource(R.drawable.d02);
                break;
            case 802:
                recyclerViewHolder.imgID.setImageResource(R.drawable.d03);
                break;
            case 803:
            case 804:
                recyclerViewHolder.imgID.setImageResource(R.drawable.d04);
                break;

        }

        }


    @Override
    public int getItemCount() {
        return 4;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView txtTemp;
        TextView txtDayOfWeek;
        ImageView imgID;


        public RecyclerViewHolder( View itemView) {
            super(itemView);
            txtDayOfWeek =itemView.findViewById(R.id.txtDayOfWeek);
            txtTemp = itemView.findViewById(R.id.txtTemp);
            imgID = itemView.findViewById(R.id.imgID);

        }
    }
}