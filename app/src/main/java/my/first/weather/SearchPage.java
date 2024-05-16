package my.first.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SearchPage extends AppCompatActivity {

//    TextView backBtn;
    TextInputEditText textField;
    AutoCompleteTextView autoCompleteTextView;
    FloatingActionButton backBtn;

    MainActivity mainActivity;

    TextView searchIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        backBtn=findViewById(R.id.floating_backButton);
        searchIcon=findViewById(R.id.spSearchIcon);
        mainActivity= new MainActivity();

        String[] cities=getResources().getStringArray(R.array.cities);
        autoCompleteTextView=findViewById(R.id.autoTextView);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedCity=(String) adapterView.getItemAtPosition(position);
                if (selectedCity.isEmpty()){
                    Toast.makeText(SearchPage.this, "Select a city or Enable GPS", Toast.LENGTH_LONG).show();
                }else{
                    Intent searchIntent=new Intent(SearchPage.this,MainActivity.class);
                    searchIntent.putExtra("selectedCity",selectedCity);
                    startActivity(searchIntent);
//                    Toast.makeText(SearchPage.this, "City selected: "+selectedCity, Toast.LENGTH_SHORT).show();
                }
//                startActivity(searchIntenToast.makeText(SearchPage.this, "City selected: "+selectedCity, Toast.LENGTH_SHORT).show();t);

            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoCompleteTextView.getText().toString().trim().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchPage.this);
                    builder.setTitle("Field cannot be empty");
                    builder.setMessage("Enter a city!");
                    // Set Cancelable true for when the user clicks on the outside the Dialog Box then it will disappear
                    builder.setCancelable(true);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    Intent search2= new Intent(SearchPage.this, MainActivity.class);
                    String TextInput=autoCompleteTextView.getText().toString();
                    search2.putExtra("inputedcity",TextInput);
                    startActivity(search2);
                }
//                Toast.makeText(SearchPage.this, "Inputed city", Toast.LENGTH_SHORT).show();
            }
        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(SearchPage.this,MainActivity.class);
                startActivity(i);

            }
        });
    }
}