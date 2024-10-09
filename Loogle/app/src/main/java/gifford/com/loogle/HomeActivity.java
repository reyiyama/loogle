package gifford.com.loogle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

  static String PACKAGE_NAME;

  static LocationManager locationManager;
  static final int REQUEST_CODE_FINE_LOCATION = 200;

  Button firstHomeButton;
  TextView requestText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    PACKAGE_NAME = getApplicationContext().getPackageName();

    locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
    boolean gpsGranted = initializeLocationServices(locationManager);

    firstHomeButton = findViewById(R.id.home_button_first);
    requestText = findViewById(R.id.home_text_request);
    setFirstHomeButton(firstHomeButton, requestText, gpsGranted);
  }

  /**
   * Runs when user clicks allow or deny on permissions dialog.
   * @param requestCode Tells which permission its returning for.
   * @param permissions List of permissions requested.
   * @param grantResults Result of allow or deny for each permission.
   */
  public void onRequestPermissionsResult(
      int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == REQUEST_CODE_FINE_LOCATION && grantResults.length > 0) {
      setFirstHomeButton(firstHomeButton, requestText,
          grantResults[0] == PackageManager.PERMISSION_GRANTED);
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  private boolean initializeLocationServices(LocationManager locationManager) {
    Log.d("INIT", "Initialize started");

    boolean gpsEnabled = false;

    if (locationManager != null) {
      gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    } else {
      Log.e("INIT", "Location manager null");
    }

    if (!gpsEnabled) {
      Log.e("INIT", "GPS not enabled");
      Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
      startActivity(intent);
    } else {
      Log.d("INIT", "GPS enabled");
    }

    boolean gpsGranted = false;

    if (ContextCompat.checkSelfPermission(getApplicationContext(),
        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
      Log.e("INIT", "Location permission denied");
      ActivityCompat.requestPermissions(HomeActivity.this,
          new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_FINE_LOCATION);
    } else {
      Log.d("INIT", "Location permission granted");
      gpsGranted = true;
    }

    return gpsGranted;
  }

  private void setFirstHomeButton(
      Button firstHomeButton, TextView requestText, boolean gpsGranted) {
    if (gpsGranted) {
      requestText.setVisibility(View.INVISIBLE);

      firstHomeButton.setText(R.string.home_button_new);
      firstHomeButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Log.d("BUTTONCLICK", "New Button click");
          if (locationManager != null) {
            String provider = locationManager.getBestProvider(new Criteria(), false);
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {
              Location location = locationManager.getLastKnownLocation(provider);
              if (location != null) {
                //TODO - send location to database
                Toast.makeText(getApplicationContext(),
                    "Location:   " + String.format(
                        Locale.US, "%.2f°   %.2f°",
                        location.getLatitude(), location.getLongitude()),
                    Toast.LENGTH_SHORT).show();
              } else {
                Log.e("BUTTONCLICK","Can't find location");
              }
            } else {
              Log.e("BUTTONCLICK", "Permissions denied");
            }
          } else {
            Log.e("BUTTONCLICK", "Location manager null");
          }
        }
      });
    } else {
      requestText.setVisibility(View.VISIBLE);

      firstHomeButton.setText(R.string.home_button_give);
      firstHomeButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Log.d("BUTTONCLICK", "Give Button click");
          if (ContextCompat.checkSelfPermission(getApplicationContext(),
              Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Log.e("BUTTONCLICK", "Location permission denied");
            ActivityCompat.requestPermissions(HomeActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_FINE_LOCATION);
          } else {
            Log.d("BUTTONCLICK", "Location permission granted");
          }
        }
      });
    }
  }
}
