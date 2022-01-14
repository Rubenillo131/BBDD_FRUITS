package com.example.bbdd_fruits;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {

    EditText txtdelete;
    Button btndelete;
    TextView datalist2;
    SQLiteHelper sql;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_activity);
        txtdelete = findViewById(R.id.etxt_delete);
        btndelete = findViewById(R.id.btndelete);
        datalist2 = findViewById(R.id.showdb2);

        sql = new SQLiteHelper(this);
        db = sql.getWritableDatabase();



        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(v);
                Toast.makeText(v.getContext(),"Deleted",Toast.LENGTH_SHORT).show();
                all(v);
            }
        });
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
            datalist2.append(row);
            cursor.moveToNext();
        }
    }
    public void delete(View view){
        String search = txtdelete.getText().toString();
        String consulta ="DELETE FROM fruitis WHERE name = '"+search+"';";
        datalist2.setText("");
        db=sql.getReadableDatabase();
        Cursor c=db.rawQuery(consulta,null);
        search(c);
        txtdelete.setText("");
    }
    public void all(View view) {
        datalist2.setText("");
        db=sql.getReadableDatabase();
        Cursor c=db.query("fruitis", null, null, null, null, null,null);
        search(c);
        txtdelete.setText("");
    }
}