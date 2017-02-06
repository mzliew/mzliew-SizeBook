package mzliew.SizeBook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    Info activity shows selected person's size information.
    - gets the position of the personList to get their SizeInfo
    - puts the information onto their respective categories
    - any changes made in their information will be saved once they click the "Done Editing" button
 */

public class Info extends AppCompatActivity {
    static ArrayList<SizeInfo> personList = new ArrayList<>();
    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        loadFromFile();
        Bundle extra = getIntent().getExtras();
        int position = extra.getInt("person");
        EditData(position);
    }

    public void EditData(final int position) {
        final EditText EditName = (EditText) findViewById(R.id.names);
        final EditText EditDate = (EditText) findViewById(R.id.date);
        final EditText EditNeck = (EditText) findViewById(R.id.neck);
        final EditText EditBust = (EditText) findViewById(R.id.bust);
        final EditText EditChest = (EditText) findViewById(R.id.chest);
        final EditText EditWaist = (EditText) findViewById(R.id.waist);
        final EditText EditHip = (EditText) findViewById(R.id.hip);
        final EditText EditInseam = (EditText) findViewById(R.id.inseam);
        final EditText EditComment = (EditText) findViewById(R.id.comment);
        final SizeInfo sizeinfo = personList.get(position);
        if (sizeinfo.getName() != null) {
            EditName.setText(sizeinfo.getName());
        }
        if (sizeinfo.getDate() != null) {
            EditDate.setText(sizeinfo.getDate());
        }
        if (sizeinfo.getNeck() != null) {
            EditNeck.setText(sizeinfo.getNeck());
        }
        if (sizeinfo.getBust() != null) {
            EditBust.setText(sizeinfo.getBust());
        }
        if (sizeinfo.getChest() != null) {
            EditChest.setText(sizeinfo.getChest());
        }
        if (sizeinfo.getWaist() != null) {
            EditWaist.setText(sizeinfo.getWaist());
        }
        if (sizeinfo.getHip() != null) {
            EditHip.setText(sizeinfo.getHip());
        }
        if (sizeinfo.getInseam() != null) {
            EditInseam.setText(sizeinfo.getInseam());
        }
        if (sizeinfo.getComment() != null) {
            EditComment.setText(sizeinfo.getComment());
        }

        if (EditName.getText().toString().trim().equals(""))
            EditName.setError("Name is required.");
        else {
            Button done = (Button) findViewById(R.id.Edit);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!EditName.getText().toString().trim().equals("")) {
                        String addName = EditName.getText().toString();
                        sizeinfo.setName(addName);
                    }
                    if (!EditDate.getText().toString().trim().equals("")) {
                        String addDate = EditDate.getText().toString();
                        sizeinfo.setDate(addDate);
                    }
                    if (!EditNeck.getText().toString().trim().equals("")) {
                        String addNeck = EditNeck.getText().toString();
                        sizeinfo.setNeck(addNeck);
                    }
                    if (!EditBust.getText().toString().trim().equals("")) {
                        String addBust = EditBust.getText().toString();
                        sizeinfo.setBust(addBust);
                    }
                    if (!EditChest.getText().toString().trim().equals("")) {
                        String addChest = EditChest.getText().toString();
                        sizeinfo.setChest(addChest);
                    }
                    if (!EditWaist.getText().toString().trim().equals("")) {
                        String addWaist = EditWaist.getText().toString();
                        sizeinfo.setWaist(addWaist);
                    }
                    if (!EditHip.getText().toString().trim().equals("")) {
                        String addHip = EditHip.getText().toString();
                        sizeinfo.setHip(addHip);
                    }

                    if (!EditInseam.getText().toString().trim().equals("")) {
                        String addInseam = EditInseam.getText().toString();
                        sizeinfo.setInseam(addInseam);
                    }
                    if (!EditComment.getText().toString().trim().equals("")) {
                        String addComment = EditComment.getText().toString();
                        sizeinfo.setComment(addComment);
                    }
                    saveInFile();
                    Intent intent = new Intent(Info.this, MainActivity.class);
                    startActivity(intent);
                }
            });
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
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(personList, out);
            out.flush();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}