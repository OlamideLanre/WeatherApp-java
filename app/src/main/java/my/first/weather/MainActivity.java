package my.first.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    TextView Location, temp, FeelsLike, WindSpeed,Humidity, Description, uvIndex;
    EditText City;
    TextView date1, temp1, date2, temp2, date3, temp3, date4,temp4,date5,temp5,date6,temp6,date7,temp7;
    Button reqBtn;
    private final String API_KEY="UKZMQDUKGVPGQER6FNA4J9NSG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        City=findViewById(R.id.City);
        Location=findViewById(R.id.location);
        temp=findViewById(R.id.degree);
        FeelsLike=findViewById(R.id.FLvalue);
        WindSpeed=findViewById(R.id.WSvalue);
        Humidity=findViewById(R.id.Hvalue);
        Description=findViewById(R.id.description);
        reqBtn=findViewById(R.id.button);
        uvIndex=findViewById(R.id.UVindex);


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
        date6=findViewById(R.id.date6);
        temp6=findViewById(R.id.Temp6);
        date6=findViewById(R.id.date7);
        temp7=findViewById(R.id.Temp7);


        reqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityToFetch=City.getText().toString();
                getCurrentWeather(cityToFetch);
                Toast.makeText(MainActivity.this, "city name: "+cityToFetch, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getCurrentWeather(String city){
        AsyncHttpClient client = new AsyncHttpClient();
        final String REQUEST_URL=
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + city + "?key=" + API_KEY ;
        client.get(REQUEST_URL, new AsyncHttpResponseHandler() {
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


                            double feelslike=jsonweather.getDouble("feelslike");
                            double feelslikeInCelsius=(feelslike - 32)/1.8;

                            double humidity=jsonweather.getDouble("humidity");
                            double windspeed=jsonweather.getDouble("windspeed");
                            int uvindex=jsonweather.getInt("uvindex");
                            String location= jsonresponse.getString("address");

//                            CONVERTING TO 1 DECIMAL PLACE
                            DecimalFormat df = new DecimalFormat("#.#");
                            temperatureInCelsius = Double.parseDouble(df.format(temperatureInCelsius));
                            feelslikeInCelsius= Double.parseDouble(df.format(feelslikeInCelsius));

                            Location.setText(location);
                            temp.setText(String.valueOf(temperatureInCelsius));
                            Description.setText(description);
                            FeelsLike.setText(String.valueOf(feelslikeInCelsius));
                            Humidity.setText(String.valueOf(humidity +" %"));
                            WindSpeed.setText(String.valueOf(windspeed +" km/h"));
                            uvIndex.setText(String.valueOf(uvindex));



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
                            double temprature=jsondata.getDouble("temp");
                            double temperatureInCelsius=(temprature - 32)/1.8;

//                            CONVERTING TO 1 DECIMAL PLACE

                            temperatureInCelsius = Double.parseDouble(df.format(temperatureInCelsius));

                            date1.setText(date);
                            temp1.setText(String.valueOf(temperatureInCelsius));

//                            GETTING DATA @ INDEX 2
                            JSONObject jsondata2=jsonArray.getJSONObject(2);
                            String dateData2=jsondata2.getString("datetime");
                            double temperature2=jsondata2.getDouble("temp");
                            double temperatureInCelsius2=(temperature2 - 32)/1.8;
                            temperatureInCelsius2 = Double.parseDouble(df.format(temperatureInCelsius2));

                            date2.setText(dateData2);
                            temp2.setText(String.valueOf(temperatureInCelsius2));

//                            DATA @ INDEX 3
                            JSONObject jsondata3=jsonArray.getJSONObject(3);
                            String dateData3=jsondata3.getString("datetime");
                            double temperature3=jsondata3.getDouble("temp");
                            double temperatureInCelsius3=(temperature3 - 32)/1.8;
                            temperatureInCelsius3 = Double.parseDouble(df.format(temperatureInCelsius3));

                            date3.setText(dateData3);
                            temp3.setText(String.valueOf(temperatureInCelsius3));

//                            DATA @ INDEX 4
                            JSONObject jsondata4=jsonArray.getJSONObject(4);
                            String dateData4=jsondata4.getString("datetime");
                            double temperature4=jsondata4.getDouble("temp");
                            double temperatureInCelsius4=(temperature4 - 32)/1.8;
                            temperatureInCelsius4 = Double.parseDouble(df.format(temperatureInCelsius4));

                            date4.setText(dateData4);
                            temp4.setText(String.valueOf(temperatureInCelsius4));

//                            DATA @ INDEX 5
                            JSONObject jsondata5=jsonArray.getJSONObject(5);
                            String dateData5=jsondata5.getString("datetime");
                            double temperature5=jsondata5.getDouble("temp");
                            double temperatureInCelsius5=(temperature5 - 32)/1.8;
                            temperatureInCelsius5 = Double.parseDouble(df.format(temperatureInCelsius5));

                            date5.setText(dateData5);
                            temp5.setText(String.valueOf(temperatureInCelsius5));

//                            DATA @ INDEX 6
//                            JSONObject jsondata6=jsonArray.getJSONObject(6);
//                            String dateData6=jsondata6.getString("datetime");
//                            double temperature6=jsondata6.getDouble("temp");
//                            double temperatureInCelsius6=(temperature6 - 32)/1.8;
//                            temperatureInCelsius6 = Double.parseDouble(df.format(temperatureInCelsius6));
//
//                            date6.setText(dateData6);
//                            temp6.setText(String.valueOf(temperatureInCelsius6));

 //                            DATA @ INDEX 7
                            JSONObject jsondata7=jsonArray.getJSONObject(7);
                            String dateData7=jsondata7.getString("datetime");
                            double temprature7=jsondata7.getDouble("temp");
                            double temperatureInCelsius7=(temprature7 - 32)/1.8;
                            temperatureInCelsius7 = Double.parseDouble(df.format(temperatureInCelsius7));

                            date7.setText(dateData7);
                            temp7.setText(String.valueOf(temperatureInCelsius7));





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