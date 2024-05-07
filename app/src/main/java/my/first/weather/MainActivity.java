package my.first.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    TextView Location, temp, FeelsLike, WindSpeed,Humidity, Description, uvIndex, sunset, sunrise;
    EditText City;
    TextView date1, temp1, date2, temp2, date3, temp3, date4,temp4,date5,temp5,date0,temp0;
    FloatingActionButton SearchBtn;

    ImageView Icon1,Icon2,Icon3,Icon4,Icon5,Icon0;
    private final String API_KEY="UKZMQDUKGVPGQER6FNA4J9NSG";

    VideoView videoView;
    Uri uri;

    @Override
    protected void onResume() {
        super.onResume();
        videoView.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent receive= new Intent(MainActivity.this,SearchPage.class);
//        startActivityForResult(receive,NEW_CITY_CODE);


//        City=findViewById(R.id.City);
        Location=findViewById(R.id.location);
        temp=findViewById(R.id.degree);
        FeelsLike=findViewById(R.id.FLvalue);
        WindSpeed=findViewById(R.id.WSvalue);
        Humidity=findViewById(R.id.Hvalue);
        Description=findViewById(R.id.description);
        SearchBtn=findViewById(R.id.floating_action_button);
        uvIndex=findViewById(R.id.UVindex);
        sunset=findViewById(R.id.sunSetValue);
        sunrise=findViewById(R.id.sunRiseValue);

//        FUTURE FORECAST DATE
        date1=findViewById(R.id.date1);
        temp1=findViewById(R.id.Temp1);
        date2=findViewById(R.id.date2);
        temp2=findViewById(R.id.Temp2);
        date3=findViewById(R.id.date3);
        temp3=findViewById(R.id.Temp3);
        date4=findViewById(R.id.date4);
        temp4=findViewById(R.id.Temp4);
        date5=findViewById(R.id.date5);
        temp5=findViewById(R.id.Temp5);
        date0=findViewById(R.id.date0);
        temp0=findViewById(R.id.Temp0);

//        ICONS
        Icon0=findViewById(R.id.icon0);
        Icon1=findViewById(R.id.icon1);
        Icon2=findViewById(R.id.icon2);
        Icon3=findViewById(R.id.icon3);
        Icon4=findViewById(R.id.icon4);
        Icon5=findViewById(R.id.icon5);

//        SET BACKGROUND VIDEO
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoView=(VideoView) findViewById(R.id.bgVideo);




        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in= new Intent(MainActivity.this, SearchPage.class);
                startActivity(in);

            }
        });


        String selectedCity=getIntent().getStringExtra("selectedCity");
        if (selectedCity!=null){
            getCurrentWeather(selectedCity);
            Toast.makeText(this, "Fetching...", Toast.LENGTH_LONG).show();
        }


        String inputedCity=getIntent().getStringExtra("inputedcity");
        if (inputedCity!=null){
            getCurrentWeather(inputedCity);
            Toast.makeText(this, "Fetching...", Toast.LENGTH_SHORT).show();
        }

    }

    public void getCurrentWeather(String city){
        AsyncHttpClient client = new AsyncHttpClient();
        final String REQUEST_URL=
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + city + "?key=" + API_KEY ;
        client.get(REQUEST_URL, new AsyncHttpResponseHandler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    if (statusCode==200){
                        Log.i("weather: ","Request successful");
//                        Toast.makeText(MainActivity.this, "status code: "+statusCode, Toast.LENGTH_SHORT).show();

                        if (responseBody!=null){
                           String responseBodyString= new String(responseBody);

                            JSONObject jsonresponse= new JSONObject(responseBodyString);
                            Log.i("json response: ","response: "+ jsonresponse);
                            JSONArray jsonArray= jsonresponse.getJSONArray("days");
                            JSONObject jsonweather=jsonArray.getJSONObject(0);

                            String description=jsonweather.getString("conditions");

                            // comes in fahrenheit, second line is to convert to celcius.
                            double temprature=jsonweather.getDouble("temp");
                            double temperatureInCelsius=(temprature - 32)/1.8;
//                            int temperatureInInteger=(int) temperatureInCelsius;


                            double feelslike=jsonweather.getDouble("feelslike");
                            double feelslikeInCelsius=(feelslike - 32)/1.8;

                            double humidity=jsonweather.getDouble("humidity");
                            double windspeed=jsonweather.getDouble("windspeed");
                            int uvindex=jsonweather.getInt("uvindex");
                            String location= jsonresponse.getString("address");

//                            CONVERTING TO 1 DECIMAL PLACE
                            DecimalFormat df = new DecimalFormat("#");
                            temperatureInCelsius = Double.parseDouble(df.format(temperatureInCelsius));
                            feelslikeInCelsius= Double.parseDouble(df.format(feelslikeInCelsius));

                            String sunsetTime=jsonweather.getString("sunset");
                            String sunriseTime=jsonweather.getString("sunrise");
                            String Bgicon=jsonweather.getString("icon");

                            Location.setText(location);
                            temp.setText(temperatureInCelsius + "\u00B0");
                            temp0.setText(temperatureInCelsius + "\u00B0");
                            Description.setText(description);
                            FeelsLike.setText(feelslikeInCelsius + "\u00B0");
                            Humidity.setText(humidity +" %");
                            WindSpeed.setText(windspeed +" km/h");
                            uvIndex.setText(String.valueOf(uvindex));
                            sunrise.setText(sunriseTime);
                            sunset.setText(sunsetTime);
                            date0.setText("Today");


//                            SETTING ICON OF FORECAST
                            ImageView[] forecastIcons = {Icon0,Icon1, Icon2, Icon3, Icon4, Icon5};

                            JSONArray forecastArray = jsonresponse.getJSONArray("days");

                            for (int i = 0; i < forecastIcons.length && i < forecastArray.length(); i++) {
                                JSONObject forecast = forecastArray.getJSONObject(i);
                                String icon = forecast.getString("icon");

                                // Set the icon for the corresponding ImageView
                                 int resID = getResources().getIdentifier(icon, "drawable", getPackageName());
//                                int resID = getIconResourceID(icon); // Get the resource ID for the icon name
                                if (resID != 0) {
                                    forecastIcons[i].setImageResource(resID);
                                }
                            }
//                              CHANGING BACKGROUND BASED ON ICON
                            if (Bgicon.equals("rain")){
                                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rain_vid));
                                videoView.start();
                            } else if (Bgicon.equals("clear-day")) {
                                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.clear_sky));
                                videoView.start();
                            } else if (Bgicon.equals("partly-cloudy-day")) {
                                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.partly_cloudy_vid));
                                videoView.setVideoURI(uri);
                                videoView.start();
                            }else if(Bgicon.equals("thunderstorm")){
                                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.thunderstorm));
                                videoView.start();
                            } else if (Bgicon.equals("snow")) {
                                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.snow));
                               videoView.start();
                            }else {
                                Toast.makeText(MainActivity.this, "not working", Toast.LENGTH_SHORT).show();
                            }


//                            NOTIFICATION FOR EXTREME WEATHER(WIP)
//                            JSONArray Alerts= jsonresponse.getJSONArray("alerts");
//                            JSONObject alertDay=Alerts.getJSONObject(0);
//                            String alertEvent=alertDay.getString("event");
//                            String alertHeading=alertDay.getString("headline");
//                            NotificationCompat.Builder builder=(NotificationCompat.Builder)new NotificationCompat.Builder(getApplicationContext());
//                            builder.setSmallIcon(R.drawable.baseline_cloud_24)
//                            .setContentTitle(alertEvent)
//                            .setContentText(alertHeading)
//                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//                            NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                            notificationManager.notify(0,builder.build());





                        }else {
                            Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "1something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                try {
                        if (responseBody!=null){
                            DecimalFormat df = new DecimalFormat("#.#");
                            String responseBodyString= new String(responseBody);

                            JSONObject jsonresponse= new JSONObject(responseBodyString);
                            JSONArray jsonArray= jsonresponse.getJSONArray("days");
                            JSONObject jsondata=jsonArray.getJSONObject(1);

                            String date=jsondata.getString("datetime");
                            double temprature=jsondata.getDouble("tempmin");
                            double temperatureInCelsius=(temprature - 32)/1.8;
                            temperatureInCelsius = Double.parseDouble(df.format(temperatureInCelsius));

                            double maxTemperature=jsondata.getDouble("tempmax");
                            double maxTemperatureInCel=(maxTemperature -32)/1.8;
                            maxTemperatureInCel=Double.parseDouble(df.format(maxTemperatureInCel));

//                            CONVERTING TO 1 DECIMAL PLACE
                            date1.setText(date);
                            temp1.setText(temperatureInCelsius + "\u00B0" +"/"+maxTemperatureInCel + "\u00B0");

//                            GETTING FORECAST @ INDEX 2
                            JSONObject jsondata2=jsonArray.getJSONObject(2);
                            String dateData2=jsondata2.getString("datetime");
                            double Mintemperature2=jsondata2.getDouble("tempmin");
                            double MintemperatureInCelsius2=(Mintemperature2 - 32)/1.8;
                            MintemperatureInCelsius2 = Double.parseDouble(df.format(MintemperatureInCelsius2));

                            double maxTemperature2=jsondata2.getDouble("tempmax");
                            double maxTemperatureInCel2=(maxTemperature2 -32)/1.8;
                            maxTemperatureInCel2=Double.parseDouble(df.format(maxTemperatureInCel2));


                            date2.setText(dateData2);
                            temp2.setText(MintemperatureInCelsius2 + "\u00B0" +"/"+maxTemperatureInCel2 + "\u00B0");

//                            DATA @ INDEX 3
                            JSONObject jsondata3=jsonArray.getJSONObject(3);
                            String dateData3=jsondata3.getString("datetime");
                            double temperature3=jsondata3.getDouble("tempmin");
                            double temperatureInCelsius3=(temperature3 - 32)/1.8;
                            temperatureInCelsius3 = Double.parseDouble(df.format(temperatureInCelsius3));

                            double maxTemp3=jsondata3.getDouble("tempmax");
                            double maxTemp3InCel=(maxTemp3 - 32)/1.8;
                            maxTemp3InCel=Double.parseDouble((df.format(maxTemp3InCel)));

                            date3.setText(dateData3);
                            temp3.setText(temperatureInCelsius3 + "\u00B0" +"/"+maxTemp3InCel + "\u00B0");

//                            DATA @ INDEX 4
                            JSONObject jsondata4=jsonArray.getJSONObject(4);
                            String dateData4=jsondata4.getString("datetime");

//                            minimum temp
                            double Mintemperature4=jsondata4.getDouble("tempmin");
                            double MintemperatureInCelsius4=(Mintemperature4 - 32)/1.8;
                            MintemperatureInCelsius4 = Double.parseDouble(df.format(MintemperatureInCelsius4));
//                            maximum temp
                            double MaxTemperature4=jsondata4.getDouble("tempmax");
                            double MaxTemperatureInCel=(MaxTemperature4 - 32)/1.8;
                            MaxTemperatureInCel=Double.parseDouble(df.format(MaxTemperatureInCel));

                            date4.setText(dateData4);
                            temp4.setText(MintemperatureInCelsius4 + "\u00B0" +"/"+MaxTemperatureInCel + "\u00B0");

//                            DATA @ INDEX 5
                            JSONObject jsondata5=jsonArray.getJSONObject(5);
                            String dateData5=jsondata5.getString("datetime");
                            double temperature5=jsondata5.getDouble("temp");
                            double temperatureInCelsius5=(temperature5 - 32)/1.8;
                            temperatureInCelsius5 = Double.parseDouble(df.format(temperatureInCelsius5));

                            double maxTemp5=jsondata5.getDouble("tempmax");
                            double maxTemp5InCel=(maxTemp5 -32)/1.8;
                            maxTemp5InCel=Double.parseDouble(df.format(maxTemp5InCel));

                            date5.setText(dateData5);
                            temp5.setText(temperatureInCelsius5 + "\u00B0" +"/"+ maxTemp5InCel + "\u00B0");

                        }
                        else{
                        Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
//                Log.i(LOGCAT,"Successful, status code: "+statusCode);
                Toast.makeText(MainActivity.this, "successful, status code: "+statusCode, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("failure: ","message "+ statusCode);
                Toast.makeText(MainActivity.this, "failed. statuscode: "+statusCode, Toast.LENGTH_SHORT).show();
                if (statusCode==400){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    // Set the message show for the Alert time
                    builder.setMessage("City does not exist!");

                    // Set Alert Title
//                    builder.setTitle("Alert !");

                    // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                    builder.setCancelable(true);
                    // Create the Alert dialog
                    AlertDialog alertDialog = builder.create();
                    // Show the Alert Dialog box
                    alertDialog.show();
                }else if (statusCode==500||statusCode==404||statusCode==401||statusCode==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    // Set the message show for the Alert time
                    builder.setMessage("Something went wrong! \n Try again later");

                    builder.setCancelable(true);
                    // Create the Alert dialog
                    AlertDialog alertDialog = builder.create();
                    // Show the Alert Dialog box
                    alertDialog.show();
                }

            }
        });
    }

}