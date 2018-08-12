package project.afinal.fuelpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import project.afinal.fuelpay.R;
import project.afinal.fuelpay.helper.FuelRateHelper;
import project.afinal.fuelpay.helper.StationDetailsAreaHelper;

/**
 * Created by ASUS on 11/24/2017.
 */

public class FuelRateAdapter extends ArrayAdapter<FuelRateHelper> {
    private Context context;
    public ArrayList<FuelRateHelper> allareaInfo;
    private static LayoutInflater inflater = null;

    public FuelRateAdapter(Context context,
                           ArrayList<FuelRateHelper> mAreaInfo) {
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
    public FuelRateHelper getItem(int position) {
        return allareaInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getPosition(FuelRateHelper item) {
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
        TextView fuelType;
        TextView weight;
        TextView amount;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.adapter_fuel_rate, null);

        holder.fuelType = (TextView) rowView.findViewById(R.id.fuelType);
        holder.weight = (TextView) rowView.findViewById(R.id.weight);
        holder.amount = (TextView) rowView.findViewById(R.id.amount);



        holder.fuelType.setText(allareaInfo.get(position).getFuelType());
        holder.weight.setText(allareaInfo.get(position).getWeight());
        holder.amount.setText("BDT "+allareaInfo.get(position).getPrice());

        return rowView;
    }
}

