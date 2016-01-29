package ggj16.ka.bluetooth;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import ggj16.ka.bluetooth.net.BluetoothNetworkConnection;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        BluetoothNetworkConnection network = new BluetoothNetworkConnection(this);
		initialize(new Main(network), config);

	}
}
