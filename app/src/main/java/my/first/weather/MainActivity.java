package my.first.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView date1, temp1, date2, temp2, date3, temp3, date4,temp4,date5,temp5;
    FloatingActionButton SearchBtn;
    private final String API_KEY="UKZMQDUKGVPGQER6FNA4J9NSG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in= new Intent(MainActivity.this, SearchPage.class);
                startActivity(in);

            }
        });

//        reqBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String cityToFetch=City.getText().toString();
//                getCurrentWeather(cityToFetch);
//                Toast.makeText(MainActivity.this, "city name: "+cityToFetch, Toast.LENGTH_SHORT).show();
//            }
//        });

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
                        Toast.makeText(MainActivity.this, "status code: "+statusCode, Toast.LENGTH_SHORT).show();

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

                            Location.setText(location);
                            temp.setText(String.valueOf(temperatureInCelsius + "\u00B0"));
                            Description.setText(description);
                            FeelsLike.setText(String.valueOf(feelslikeInCelsius));
                            Humidity.setText(humidity +" %");
                            WindSpeed.setText(windspeed +" km/h");
                            uvIndex.setText(String.valueOf(uvindex));
                            sunrise.setText(sunriseTime);
                            sunset.setText(sunsetTime);



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
                            temp1.setText(temperatureInCelsius +"/"+maxTemperatureInCel);

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
                            temp2.setText(MintemperatureInCelsius2 +"/"+maxTemperatureInCel2);

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
                            temp3.setText(temperatureInCelsius3 +"/"+maxTemp3InCel);

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
                            temp4.setText(MintemperatureInCelsius4 +"/"+MaxTemperatureInCel);

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
                            temp5.setText(temperatureInCelsius5 +"/"+ maxTemp5InCel);

                        }
                        else{
                        Toast.makeText(MainActivity.this, "something happened", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

//    public void sevenDaysForecast(String city){
//        AsyncHttpClient client= new AsyncHttpClient();
//        final String REQUEST_URL=
//                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + city + "?key=" + API_KEY ;
//        client.get(REQUEST_URL, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//            }
//        });
//    }
}