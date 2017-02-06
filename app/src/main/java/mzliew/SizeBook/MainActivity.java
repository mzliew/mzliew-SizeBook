package mzliew.SizeBook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/*
    MainActivity is where the app starts and shows the list of people who we keep track
    of their sizes.
    - Option to add a new record via the "Add Record" button
    - Loads from a save file containing GSON of each record
    - Maintains a count of the individuals entered
    - Allows deletion and editing of records by long clicking a record.
 */
public class MainActivity extends AppCompatActivity {
    ArrayList<SizeInfo> personList = new ArrayList<>();
    private static final String FILENAME = "file.sav";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromFile();
        showCount(personList);
        showList();
    }
    private void showList(){
        ListView PersonView = (ListView)findViewById(R.id.PersonList);
        final ArrayAdapter<SizeInfo> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, personList);
        PersonView.setAdapter(listAdapter);
        // Taken from http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
        // 2017-01-28 16:21:55
        PersonView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setMessage("Selecting " + personList.get(position) + " to");
                adb.setCancelable(true);
                final SizeInfo sizeinfo = personList.get(position);
                adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        personList.remove(sizeinfo);
                        listAdapter.notifyDataSetChanged();
                        showCount(personList);
                        saveInFile();
                    }
                });
                adb.setNegativeButton("View/Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this,Info.class);
                        intent.putExtra("person",position);
                        startActivity(intent);
                    }
                });
                adb.show();
                return false;
            }
        });
    }

    private void showCount(ArrayList personList) {
        TextView count = (TextView)findViewById(R.id.count);
        if(personList.isEmpty()) {
            count.setText("Count: 0");
        }
        else {
            int size = personList.size();
            count.setText("Count: " + size);
        }
    }
    private void loadFromFile(){
        try{
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-01-28 14:54:02
            personList = gson.fromJson(in, new TypeToken<ArrayList<SizeInfo>>(){}.getType());
            fis.close();
        }catch (FileNotFoundException e){
            personList = new ArrayList<>();
        } catch (IOException e)
        {
            throw new RuntimeException();
        }
    }

    private void saveInFile(){
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(personList,out);
            out.flush();
            fos.close();
        } catch (IOException e){
            throw new RuntimeException();
        }
    }

    public void NewRecord(View view) {
        Intent intent = new Intent(MainActivity.this, NewRecord.class);
        startActivity(intent);
    }

}
