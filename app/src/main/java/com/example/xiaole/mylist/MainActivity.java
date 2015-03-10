package com.example.xiaole.mylist;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity {
    String[] colourNames;
    TextView text;
    String[] colourNumber;
    String backColor;
    RelativeLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout=(RelativeLayout)findViewById(R.id.main);
        try {
            File myFile = new File("/sdcard/color.txt");
            if(myFile.exists()) { //if the file exists, set the background to that color
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader( //get buffer from the file
                        new InputStreamReader(fIn));
                String aDataRow = "";
                String aBuffer = "";
                while ((aDataRow = myReader.readLine()) != null) {
                    aBuffer = aDataRow;//get the color
                }
                mainLayout.setBackgroundColor(Color.parseColor(aBuffer)); //set the background color
                myReader.close();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        colourNames = getResources().getStringArray(R.array.listArray);
        colourNumber = getResources().getStringArray(R.array.listValues);
        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_listview, colourNames);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                backColor = "#"+ colourNumber[position].toUpperCase().replaceFirst("^00", "");
                //text.setText(backColor);
                mainLayout.setBackgroundColor(Color.parseColor(backColor));

                }
                });
    registerForContextMenu(lv);
}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Colour");
        menu.add(0, v.getId(), 0, "Write colour to SDCard");
        menu.add(0, v.getId(), 0, "Read colour from SDCard");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Write colour to SDCard") {
            //Toast.makeText(getApplicationContext(), "making a call", Toast.LENGTH_LONG).show();
            writeToSDCard(backColor);
            //int listPosition = info.position;
            //String colour = colourNames[listPosition];
            //text.setText(colour);

        } else if (item.getTitle() == "Read colour from SDCard") {
            //Toast.makeText(getApplicationContext(), "sending a message", Toast.LENGTH_LONG).show();
            readFromSDCard();
        } else {
            return false;
        }
        return true;
    }

    public void writeToSDCard(String color) {
        try {
            File myFile = new File("/sdcard/color.txt");
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(output);
            myOutWriter.append(color); //save the color into the file
            myOutWriter.close();
            output.close();
            Toast.makeText(getBaseContext(),
                    "Done writing SD 'color.txt'",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void readFromSDCard(){

        try {
            File myFile = new File("/sdcard/color.txt");
            //Read text from file
            if (myFile.exists()) { //if the file exists, set the background to that color
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader( //get buffer from the file
                        new InputStreamReader(fIn));
                String aDataRow = "";
                String aBuffer = "";
                while ((aDataRow = myReader.readLine()) != null) {
                    aBuffer = aDataRow;//get the color
                }
                mainLayout.setBackgroundColor(Color.parseColor(aBuffer)); //set the background color
                myReader.close();
            } else {
                Toast.makeText(getBaseContext(),
                        "No color in the SDCard",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
