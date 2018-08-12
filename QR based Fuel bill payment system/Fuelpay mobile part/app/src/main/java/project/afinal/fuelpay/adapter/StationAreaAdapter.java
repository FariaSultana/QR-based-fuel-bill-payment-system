package project.afinal.fuelpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.android.gms.analytics.ecommerce.Product;

import java.util.ArrayList;

import project.afinal.fuelpay.R;
import project.afinal.fuelpay.helper.StationAreaHelper;

/**
 * Created by ASUS on 11/24/2017.
 */

public class StationAreaAdapter extends ArrayAdapter<StationAreaHelper> implements Filterable {
    private Context context;
    public ArrayList<StationAreaHelper> allareaInfo;
    public ArrayList<StationAreaHelper> allareaInfoDis;
    private static LayoutInflater inflater = null;

    public StationAreaAdapter(Context context,
                              ArrayList<StationAreaHelper> mAreaInfo) {
        // TODO Auto-generated constructor stub
        super(context, R.layout.adapter_serach_area);
        this.context = context;
        this.allareaInfo = mAreaInfo;
        this.allareaInfoDis = mAreaInfo;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return allareaInfo.size();
    }

    @Override
    public StationAreaHelper getItem(int position) {
        return allareaInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getPosition(StationAreaHelper item) {
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
        TextView location;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.adapter_serach_area, null);

        holder.location = (TextView) rowView.findViewById(R.id.location);


        holder.location.setText(allareaInfo.get(position).getLocation());


        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + allareaInfo.get(position).getLocation(), Toast.LENGTH_LONG).show();
            }
        });*/
        return rowView;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                allareaInfo = (ArrayList<StationAreaHelper>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<StationAreaHelper> FilteredArrList = new ArrayList<StationAreaHelper>();

                if (allareaInfoDis == null) {
                    allareaInfoDis = new ArrayList<StationAreaHelper>(allareaInfo); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = allareaInfoDis.size();
                    results.values = allareaInfoDis;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < allareaInfoDis.size(); i++) {
                        String data = allareaInfoDis.get(i).getLocation();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new StationAreaHelper(allareaInfoDis.get(i).getLocation(),allareaInfoDis.get(i).getStationid()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}

