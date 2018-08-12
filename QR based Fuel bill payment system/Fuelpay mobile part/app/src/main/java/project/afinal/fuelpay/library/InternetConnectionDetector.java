package project.afinal.fuelpay.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnectionDetector {

	private Context context;

	public InternetConnectionDetector(Context context) {
		this.context = context;
	}

	/**
	 * Checking for all possible Internet providers
	 * **/
	@SuppressWarnings("deprecation")
	public boolean isConnectedToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}
}
