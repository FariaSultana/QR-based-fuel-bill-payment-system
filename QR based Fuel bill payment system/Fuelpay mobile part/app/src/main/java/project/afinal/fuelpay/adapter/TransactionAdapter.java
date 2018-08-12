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
import project.afinal.fuelpay.helper.StationDetailsAreaHelper;
import project.afinal.fuelpay.helper.TransactionsHelper;

/**
 * Created by ASUS on 11/24/2017.
 */

public class TransactionAdapter extends ArrayAdapter<TransactionsHelper> {
    private Context context;
    public ArrayList<TransactionsHelper> allareaInfo;
    private static LayoutInflater inflater = null;

    public TransactionAdapter(Context context,
                              ArrayList<TransactionsHelper> mAreaInfo) {
        // TODO Auto-generated constructor stub
        super(context, R.layout.adapter_transaction);
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
    public TransactionsHelper getItem(int position) {
        return allareaInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getPosition(TransactionsHelper item) {
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
        TextView toAcc;
        TextView amount;
        TextView create_dateTime;
        TextView stationName;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.adapter_transaction, null);

        holder.toAcc = (TextView) rowView.findViewById(R.id.toAcc);
        holder.amount = (TextView) rowView.findViewById(R.id.amount);
        holder.create_dateTime = (TextView) rowView.findViewById(R.id.create_dateTime);
        holder.stationName = (TextView) rowView.findViewById(R.id.stationName);


        holder.toAcc.setText(allareaInfo.get(position).getToAcc());
        holder.amount.setText("BDT "+allareaInfo.get(position).getAmount());
        holder.create_dateTime.setText(allareaInfo.get(position).getCreate_dateTime());
        holder.stationName.setText(allareaInfo.get(position).getStationName());



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

