package mzliew.SizeBook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class NewRecord extends AppCompatActivity {
    static ArrayList<SizeInfo> personList = new ArrayList<>();
    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);
    }

    public void Info(View view) {
        EditText name = (EditText) findViewById(R.id.names);
        EditText date = (EditText) findViewById(R.id.date);
        EditText neck = (EditText) findViewById(R.id.neck);
        EditText bust = (EditText) findViewById(R.id.bust);
        EditText chest = (EditText) findViewById(R.id.chest);
        EditText waist = (EditText) findViewById(R.id.waist);
        EditText hip = (EditText) findViewById(R.id.hip);
        EditText inseam = (EditText) findViewById(R.id.inseam);
        EditText comment = (EditText) findViewById(R.id.comment);

        if (name.getText().toString().trim().equals(""))
            name.setError("Name is required.");
        else {
            SizeInfo sizeinfo = new SizeInfo();
            if (!name.getText().toString().trim().equals("")) {
                String addName = name.getText().toString();
                sizeinfo.setName(addName);
            }
            if (!date.getText().toString().trim().equals("")) {
                String addDate = date.getText().toString();
                sizeinfo.setDate(addDate);
            }
            if (!neck.getText().toString().trim().equals("")) {
                String addNeck = neck.getText().toString();
                sizeinfo.setNeck(addNeck);
            }
            if (!bust.getText().toString().trim().equals("")) {
                String addBust = bust.getText().toString();
                sizeinfo.setBust(addBust);
            }
            if (!chest.getText().toString().trim().equals("")) {
                String addChest = chest.getText().toString();
                sizeinfo.setChest(addChest);
            }
            if (!waist.getText().toString().trim().equals("")) {
                String addWaist = waist.getText().toString();
                sizeinfo.setWaist(addWaist);
            }
            if (!hip.getText().toString().trim().equals("")) {
                String addHip = hip.getText().toString();
                sizeinfo.setHip(addHip);
            }
            if (!inseam.getText().toString().trim().equals("")) {
                String addInseam = inseam.getText().toString();
                sizeinfo.setInseam(addInseam);
            }
            if (!comment.getText().toString().trim().equals("")) {
                String addComment = comment.getText().toString();
                sizeinfo.setComment(addComment);
            }
            saveInFile(sizeinfo);
            Intent intent = new Intent(NewRecord.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void saveInFile(SizeInfo sizeinfo) {
        try {
            personList.add(sizeinfo);
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(personList,out);
            out.flush();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}