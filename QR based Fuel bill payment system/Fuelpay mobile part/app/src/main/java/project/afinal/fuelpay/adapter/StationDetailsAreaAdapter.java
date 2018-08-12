package project.afinal.fuelpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import project.afinal.fuelpay.R;
import project.afinal.fuelpay.helper.StationDetailsAreaHelper;

/**
 * Created by ASUS on 11/24/2017.
 */

public class StationDetailsAreaAdapter extends ArrayAdapter<StationDetailsAreaHelper> {
    private Context context;
    public ArrayList<StationDetailsAreaHelper> allareaInfo;
    private static LayoutInflater inflater = null;

    public StationDetailsAreaAdapter(Context context,
                                     ArrayList<StationDetailsAreaHelper> mAreaInfo) {
        // TODO Auto-generated constructor stub
        super(context, R.layout.adapter_details_serach_area);
        this.context = context;
        this.allareaInfo = mAreaInfo;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return allareaInfo.size();
    }

    @Override
    public StationDetailsAreaHelper getItem(int position) {
        return allareaInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getPosition(StationDetailsAreaHelper item) {
        return allareaInfo.indexOf(item);
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Number of types + 1 !!!!!!!!
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }


    public class Holder {
        TextView locationName;
        TextView location;
        TextView open;
        TextView close;
        TextView traffic;
        TextView mobile;
        TextView traffictext;
        ImageView imageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.adapter_details_serach_area, null);

        holder.locationName = (TextView) rowView.findViewById(R.id.locationName);
        holder.location = (TextView) rowView.findViewById(R.id.location);
        holder.open = (TextView) rowView.findViewById(R.id.open);
        holder.close = (TextView) rowView.findViewById(R.id.close);
        holder.mobile = (TextView) rowView.findViewById(R.id.mobile);
        holder.traffic = (TextView) rowView.findViewById(R.id.traffic);
        holder.traffictext = (TextView) rowView.findViewById(R.id.traffictext);
        holder.imageView = (ImageView) rowView.findViewById(R.id.imageView);


        holder.locationName.setText(allareaInfo.get(position).getName());
        holder.location.setText(allareaInfo.get(position).getLocation());
        holder.open.setText(allareaInfo.get(position).getStart_time());
        holder.close.setText(allareaInfo.get(position).getEnd_time());
        holder.mobile.setText(allareaInfo.get(position).getMobile_no());

        /*Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = mdformat.format(calendar.getTime());
        */
        if (allareaInfo.get(position).getStatus().equals("false")) {
            holder.imageView.setImageResource(R.drawable.green);
            holder.traffictext.setVisibility(View.VISIBLE);
            holder.traffic.setVisibility(View.VISIBLE);
            holder.traffic.setText(allareaInfo.get(position).getTraffic());
        }
        else{
            holder.imageView.setImageResource(R.drawable.red);
        }


        //  if(allareaInfo.get(position).getStart_time() >= strDate)


        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + allareaInfo.get(position).getLocation(), Toast.LENGTH_LONG).show();
            }
        });*/
        return rowView;
    }
}

