package ict602.zakatgoldapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Homepage extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText goldWeight, goldValue;
    Button Calc, Reset;
    TextView totalgoldvalue, zakatpayable, totalzakat;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    float gweight;
    float gvalue;

    SharedPreferences sharedPref;
    SharedPreferences sharedPref2;
    private Menu menu;


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about_us:
                //Toast.makeText(this,"About Zakat Gold",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, About.class);
                startActivity(intent);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        goldWeight = (EditText) findViewById(R.id.gold_weight);
        goldValue = (EditText) findViewById(R.id.gold_value);
        Calc = (Button) findViewById(R.id.buttonCalc);
        Reset = (Button) findViewById(R.id.buttonReset);
        totalgoldvalue = (TextView) findViewById(R.id.totalgoldvalue);
        zakatpayable = (TextView) findViewById(R.id.zakatpayable);
        totalzakat = (TextView) findViewById(R.id.totalzakat);

        Calc.setOnClickListener(this);
        Reset.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        sharedPref = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        gweight = sharedPref.getFloat("weight", 0.0F);

        sharedPref2 = this.getSharedPreferences("value", Context.MODE_PRIVATE);
        gvalue = sharedPref2.getFloat("value", 0.0F);

        goldWeight.setText(""+gweight);
        goldValue.setText(""+gvalue);
    }


    public void Calculation(){
        DecimalFormat df = new DecimalFormat("##.00");
        float gweight = Float.parseFloat(goldWeight.getText().toString());
        float gvalue = Float.parseFloat(goldValue.getText().toString());
        String stat = spinner.getSelectedItem().toString();
        double totalGoldvalue;
        double uruf;
        double zakatPayable;
        double totalZakat;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("weight", gweight);
        editor.apply();
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putFloat("value", gvalue);
        editor2.apply();


        if (stat.equals("Keep")){
            totalGoldvalue= gweight * gvalue;
            uruf= gweight - 85;

            if(uruf>=0.0) {
                zakatPayable = uruf * gvalue;
                totalZakat = zakatPayable * 0.025;
            }

            else{
                zakatPayable = 0.0;
                totalZakat = zakatPayable * 0.025;

            }

            totalgoldvalue.setText("Total Gold Value: RM"+ df.format(totalGoldvalue));
            zakatpayable.setText("Zakat Payable: RM"+ df.format(zakatPayable));
            totalzakat.setText("Total Zakat: RM"+ df.format(totalZakat));
        }

        else{
            totalGoldvalue= gweight * gvalue;
            uruf= gweight - 200;

            if(uruf>=0.0) {
                zakatPayable = uruf * gvalue;
                totalZakat = zakatPayable * 0.025;
            }

            else{
                zakatPayable = 0.0;
                totalZakat = zakatPayable * 0.025;

            }

            totalgoldvalue.setText("Total Gold Value: RM"+ df.format(totalGoldvalue));
            zakatpayable.setText("Zakat Payable: RM"+ df.format(zakatPayable));
            totalzakat.setText("Total Zakat: RM"+ df.format(totalZakat));

        }

    }

    @Override
    public void onClick(View v) {

        try {
            switch (v.getId()) {

                case R.id.buttonCalc:
                    Calculation();
                    break;

                case R.id.buttonReset:
                    goldWeight.setText("");
                    goldValue.setText("");
                    totalgoldvalue.setText("");
                    zakatpayable.setText("");
                    totalzakat.setText("");

                    break;

            }
        } catch (java.lang.NumberFormatException nfe) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();

        } catch (Exception exp) {
            Toast.makeText(this,"Unknown Exception" + exp.getMessage(),Toast.LENGTH_SHORT).show();

            Log.d("Exception",exp.getMessage());

        }
    }

}