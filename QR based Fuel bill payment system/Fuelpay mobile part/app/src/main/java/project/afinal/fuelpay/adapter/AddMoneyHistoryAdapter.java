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
import project.afinal.fuelpay.helper.AddMoneyHistoryHelper;
import project.afinal.fuelpay.helper.StationDetailsAreaHelper;

/**
 * Created by ASUS on 11/24/2017.
 */

public class AddMoneyHistoryAdapter extends ArrayAdapter<AddMoneyHistoryHelper> {
    private Context context;
    public ArrayList<AddMoneyHistoryHelper> addMoneyHistoryInfo;
    private static LayoutInflater inflater = null;

    public AddMoneyHistoryAdapter(Context context,
                                  ArrayList<AddMoneyHistoryHelper> mAreaInfo) {
        // TODO Auto-generated constructor stub
        super(context, R.layout.adapter_add_money_history);
        this.context = context;
        this.addMoneyHistoryInfo = mAreaInfo;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return addMoneyHistoryInfo.size();
    }

    @Override
    public AddMoneyHistoryHelper getItem(int position) {
        return addMoneyHistoryInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getPosition(AddMoneyHistoryHelper item) {
        return addMoneyHistoryInfo.indexOf(item);
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
        TextView accountNumber;
        TextView date;
        TextView status;
        TextView amount;
        ImageView imageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.adapter_add_money_history, null);

        holder.accountNumber = (TextView) rowView.findViewById(R.id.accountNumber);
        holder.date = (TextView) rowView.findViewById(R.id.date);
        holder.status = (TextView) rowView.findViewById(R.id.status);
        holder.amount = (TextView) rowView.findViewById(R.id.amount);
        holder.imageView = (ImageView) rowView.findViewById(R.id.imageView);


        holder.accountNumber.setText(addMoneyHistoryInfo.get(position).getAccountNumber());
        holder.date.setText(addMoneyHistoryInfo.get(position).getDateTime());
        holder.status.setText(addMoneyHistoryInfo.get(position).getStatus());
        holder.amount.setText("BDT "+addMoneyHistoryInfo.get(position).getAmount());


        if (addMoneyHistoryInfo.get(position).getStatus().equals("pending")) {
            holder.imageView.setImageResource(R.drawable.red);
        }
        else{
            holder.imageView.setImageResource(R.drawable.green);
        }
        return rowView;
    }
}

