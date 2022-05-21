package com.example.yummybites;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.yummybites.database.DatabaseHelper;
import com.example.yummybites.databinding.ActivityAccountSectionDetailsBinding;
import com.example.yummybites.utils.AppConstant;
import com.example.yummybites.utils.AppUtil;
import com.example.yummybites.utils.SharedPreferenceUtil;
import com.example.yummybites.utils.ValidateUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AccountSectionDetailsActivity extends AppCompatActivity {
    ActivityAccountSectionDetailsBinding binding;
    DatabaseHelper databaseHelper;
    SharedPreferenceUtil sharedPreferenceUtil;
    RecyclerAddressAdapter addressAdapter;
    private LocationRequest locationRequest;
    AppLocationService appLocationService;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    List<String> list;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_section_details);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        databaseHelper = new DatabaseHelper(this);
        ColorDrawable colorDrawable
                = new ColorDrawable(R.drawable.background_border);
        actionBar.setBackgroundDrawable(colorDrawable);
        appLocationService = new AppLocationService(getApplicationContext());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        binding = ActivityAccountSectionDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.recyclerAddress.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        binding.plus.setVisibility(View.INVISIBLE);
        binding.edit.setText("Edit");
        binding.edit.setTextColor(R.color.black);
        binding.currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLastLocation();

//                Location location = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
//
//                if (location != null) {
//                    double latitude = location.getLatitude();
//                    double longitude = location.getLongitude();
//                    LocationAddress locationAddress = new LocationAddress();
//                    locationAddress.getAddressFromLocation(latitude, longitude,
//                            getApplicationContext(), new GeocoderHandler());
//                } else {
//                    showSettingsAlert();
//                }

            }
        });
        setText_viaCursor();
        setOnClickListeners();
        getUserDetails();

    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    //last known location of the User
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(AccountSectionDetailsActivity.this,
                                            Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    String add_latitude = String.valueOf(addresses.get(0).getLatitude());
                                    String add_longitude = String.valueOf(addresses.get(0).getLongitude());
                                    String add_country_name = String.valueOf(addresses.get(0).getCountryName());
                                    String add_Locality = String.valueOf(addresses.get(0).getLocality());
                                    String add_address_line = String.valueOf(addresses.get(0).getAddressLine(0));
                                    String final_address = add_latitude + "," + add_longitude + ","
                                            + add_country_name + "," + add_Locality + "," + add_address_line;
                                    binding.setAddress.setText(final_address);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
        } else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(AccountSectionDetailsActivity.this,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, REQUEST_CHECK_SETTINGS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                AppUtil.getInstance().showToast(getApplicationContext(), AppConstant.msg_permission);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void setText_viaCursor() {
        Cursor cursor = databaseHelper.getIdFromDb(sharedPreferenceUtil.getString(AppConstant.EMAIL, ""));
        int i = cursor.getInt(0);
        Cursor cursor1 = databaseHelper.getAddressFromDb(i);
        ArrayList<String> mArrayList = new ArrayList<>();
        for (cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1.moveToNext()) {
            // The Cursor is now set to the right position
            mArrayList.add(cursor1.getString(0));
            putData_inRecycleView(mArrayList);
        }
    }

    private void getUserDetails() {
        Cursor cursor = databaseHelper.getNameFromDb(
                sharedPreferenceUtil.getString(AppConstant.EMAIL, ""));
        String name = cursor.getString(0);
        Cursor cursor2 = databaseHelper.getNumberFromDb(
                sharedPreferenceUtil.getString(AppConstant.EMAIL, ""));
        String number = cursor2.getString(0);
        binding.userAccountName.setText(name);
        binding.phnNumber.setText(number);
        binding.userMailAccount.setText(sharedPreferenceUtil.getString(AppConstant.EMAIL, ""));
    }

    private void setOnClickListeners() {
        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.plus.setVisibility(View.VISIBLE);
            }
        });

        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


    }

    public void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_address_fragment);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        Button button = dialog.findViewById(R.id.save_address_button);
        TextInputEditText address = dialog.findViewById(R.id.address_location);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = databaseHelper.getIdFromDb(sharedPreferenceUtil.getString(AppConstant.EMAIL, ""));
                int i = cursor.getInt(0);
                if (!(ValidateUtil.validateAddress(address))) {
                    return;
                }
                if (databaseHelper.registerAddress(address.getText().toString(), i)) {
                    Cursor cursor1 = databaseHelper.getAddressFromDb(i);
                    list = new ArrayList<>();
                    for (cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1.moveToNext()) {
                        // The Cursor is now set to the right position

                        list.add(cursor1.getString(0));
                        putData_inRecycleView(list);
                    }
                    dialog.dismiss();
                }
            }
        });
    }

    private void putData_inRecycleView(List<String> list) {
//        Log.i("TAGGGGGGG", String.valueOf(list));
        addressAdapter = new RecyclerAddressAdapter(getApplicationContext(), list);
        binding.recyclerAddress.setAdapter(addressAdapter);
    }

}

//    final TextView textView = new TextView(getApplicationContext());
//                final Button button1 = new Button(getApplicationContext());
//
//                String address_verify = address.getText().toString();
//                if (!ValidateUtil.validateAddress(address)) {
//                    return;
//                }
//                else
//                {
//                    boolean var = databaseHelper.registerAddress(address_verify);
//                    if (var)
//                    {
//                        textView.setText("  "+address_verify);
//                        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                                getResources().getDimension(R.dimen.textsize));
//                        textView.setTextColor(R.color.color_border);
//                        textView.setMinHeight(120);
//                        textView.setGravity(Gravity.BOTTOM);
//                        textView.setBackgroundResource(R.drawable.card_corner);
//                        button1.setText("Remove");
//                        binding.addAddress.addView(textView);
//                        binding.addAddress.addView(button1);
////                        list.add(binding.addAddress);
//                        dialog.cancel();
//                        button1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view)
//                            {
//                                binding.addAddress.removeView(textView);
//                                binding.addAddress.removeView(button1);
////                                list.remove(binding.addAddress);
//                            }
//                        });
//                    }
//                }


// Location Methods

//    public void showSettingsAlert() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
//                AccountSectionDetailsActivity.this);
//        alertDialog.setTitle("SETTINGS");
//        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
//        alertDialog.setPositiveButton("Settings",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(
//                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        AccountSectionDetailsActivity.this.startActivity(intent);
//                    }
//                });
//        alertDialog.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//        alertDialog.show();
//    }
//
//    private class GeocoderHandler extends Handler {
//        @Override
//        public void handleMessage(Message message) {
//            String locationAddress;
//            switch (message.what) {
//                case 1:
//                    Bundle bundle = message.getData();
//                    locationAddress = bundle.getString("address");
//                    break;
//                default:
//                    locationAddress = null;
//            }
//            binding.setAddress.setText(locationAddress);
//        }
//    }


//                locationRequest = LocationRequest.create();
//                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//                locationRequest.setInterval(5000);
//                locationRequest.setFastestInterval(2000);
//
//                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                        .addLocationRequest(locationRequest);
//                builder.setAlwaysShow(true);
//
//                Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
//                        .checkLocationSettings(builder.build());
//
//                result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
//                    @Override
//                    public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
//
//                        try {
//                            LocationSettingsResponse response = task.getResult(ApiException.class);
//                            Toast.makeText(AccountSectionDetailsActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();
//
//                        } catch (ApiException e) {
//
//                            switch (e.getStatusCode()) {
//                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//
//                                    try {
//                                        ResolvableApiException resolvableApiException = (ResolvableApiException)e;
//                                        resolvableApiException.startResolutionForResult(AccountSectionDetailsActivity.this,REQUEST_CHECK_SETTINGS);
//                                    } catch (IntentSender.SendIntentException ex) {
//                                        ex.printStackTrace();
//                                    }
//                                    break;
//
//                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                                    //Device does not have location
//                                    break;
//                            }
//                        }
//                    }
//                });
//
//
//
//            }
//        });
//        setText_viaCursor();
//        setOnClickListeners();
//        getUserDetails();
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_CHECK_SETTINGS) {
//
//            switch (resultCode) {
//                case Activity.RESULT_OK:
//                    Toast.makeText(this, "GPS is tured on", Toast.LENGTH_SHORT).show();
//
//                case Activity.RESULT_CANCELED:
//                    Toast.makeText(this, "GPS required to be tured on", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        binding.currentLocation.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                if (ActivityCompat.checkSelfPermission(AccountSectionDetailsActivity.this,
//                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    getLocation();
//                } else {
//                    ActivityCompat.requestPermissions(AccountSectionDetailsActivity.this,
//                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
//
//                }
//            }
//        });


//    private void getLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
//                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                //Initialize Location
//                Location location = task.getResult();
//                if (location != null) {
//                    try {
//                        Geocoder geocoder = new Geocoder(AccountSectionDetailsActivity.this,
//                                Locale.getDefault());
//                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
//                                location.getLongitude(),
//                                1);

//                        String add_latitude = String.valueOf(addresses.get(0).getLatitude());
//                        String add_longitude = String.valueOf(addresses.get(0).getLongitude());
//                        String add_country_name = String.valueOf(addresses.get(0).getCountryName());
//                        String add_Locality = String.valueOf(addresses.get(0).getLocality());
//                        String add_address_line = String.valueOf(addresses.get(0).getAddressLine(0));
//                        String final_address = add_latitude + "," + add_longitude + ","
//                                + add_country_name + "," + add_Locality + "," + add_address_line;
//                        binding.setAddress.setText(final_address);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    AppUtil.getInstance().showToast(getApplicationContext(), "Unable to Fetch Location");
//                }
//            }
//        });
//    }