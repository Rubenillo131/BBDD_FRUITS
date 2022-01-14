package com.example.bbdd_fruits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnadd;
    Button btnall;
    Button btnlast;
    Button btnname;
    Button btnsearch;

    EditText nametxt;
    EditText weighttxt;
    Spinner typetxt;
    CheckBox rottentxt;

    EditText namesearch;
    TextView datalist;
    SQLiteHelper sql;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnadd = findViewById(R.id.btnadd);
        btnall = findViewById(R.id.btnall);
        btnlast = findViewById(R.id.btnlast);
        btnname = findViewById(R.id.btnname);
        btnsearch = findViewById(R.id.btnsearch);

        nametxt = findViewById(R.id.etxt_delete);
        weighttxt = findViewById(R.id.etxt_weight);
        typetxt = findViewById(R.id.combobox);
        rottentxt = findViewById(R.id.checkBox2);

        namesearch = findViewById(R.id.etxt_name2);

        datalist = findViewById(R.id.showdb1);

        sql = new SQLiteHelper(this);
        db = sql.getWritableDatabase();

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(v);
                Toast.makeText(v.getContext(),"Se ha agregado correctamente",Toast.LENGTH_SHORT).show();
                namesearch.setVisibility(View.INVISIBLE);
                btnsearch.setVisibility(View.INVISIBLE);
            }
        });
        btnall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all(v);
                Toast.makeText(v.getContext(),"All selected",Toast.LENGTH_SHORT).show();
                namesearch.setVisibility(View.INVISIBLE);
                btnsearch.setVisibility(View.INVISIBLE);
            }
        });
        btnlast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                last(v);
                Toast.makeText(v.getContext(),"Last selected",Toast.LENGTH_SHORT).show();
                namesearch.setVisibility(View.INVISIBLE);
                btnsearch.setVisibility(View.INVISIBLE);
            }
        });
        btnname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Name selected",Toast.LENGTH_SHORT).show();
                namesearch.setVisibility(View.VISIBLE);
                btnsearch.setVisibility(View.VISIBLE);
            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byname(v);
                Toast.makeText(v.getContext(),"Search selected",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(this, DeleteActivity.class);
                startActivityForResult(intent, 0);
                Toast.makeText(this, "Changing to delete form", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void add(View view) {
        db=sql.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("Name",nametxt.getText().toString());
        values.put("Weight",weighttxt.getText().toString());
        values.put("Type",typetxt.getSelectedItem().toString());
        values.put("Rotten",rottentxt.isSelected());
        db.insert("fruitis",null, values);

    }

    public void search(Cursor cursor){
        cursor.moveToFirst();
        int r=cursor.getCount();
        int c=cursor.getColumnCount();
        String row="\n";
        for (int i = 0; i < r; i++) {
            row="\n";
            for(int j=0;j<c;j++){
                row=row+cursor.getString(j)+" ";
            }
            datalist.append(row);
            cursor.moveToNext();
        }
    }
    public void all(View view) {
        datalist.setText("");
        db=sql.getReadableDatabase();
        Cursor c=db.query("fruitis", null, null, null, null, null,null);
        search(c);
        nametxt.setText("");
        weighttxt.setText("");
        namesearch.setText("");
    }
    public void last(View view){
        datalist.setText("");
        db=sql.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM fruitis WHERE _id =(SELECT MAX(_id)FROM fruitis);",null);
        search(c);
        nametxt.setText("");
        weighttxt.setText("");
        namesearch.setText("");
    }
    public void byname(View view){
        String search = namesearch.getText().toString();
        String consulta ="SELECT * FROM fruitis WHERE name = '"+search+"';";
        datalist.setText("");
        db=sql.getReadableDatabase();

        Cursor c=db.rawQuery(consulta,null);
        search(c);
        nametxt.setText("");
        weighttxt.setText("");
        namesearch.setText("");
    }
}