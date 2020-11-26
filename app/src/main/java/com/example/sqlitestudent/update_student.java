package com.example.sqlitestudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class update_student extends AppCompatActivity {

    EditText update_idno;
    EditText update_name;
    EditText update_address;

    String st_idno;
    String st_Name;
    String st_Address;

    List<POJO> studentDetails;
    OpenHelper openHelper;
    SQLiteDatabase sqLiteDatabase;

    int rowId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
        openHelper = new OpenHelper(this);
        sqLiteDatabase = openHelper.getWritableDatabase();

        update_idno = findViewById(R.id.updateidno);
        update_name = findViewById(R.id.updatename);
        update_address = findViewById(R.id.updateaddress);

        rowId = getIntent().getIntExtra("stuId", -1);

        Cursor cursor = sqLiteDatabase.query(DatabaseInfo.TABLE_NAME, null, DatabaseInfo._ID + " = " + rowId, null, null,null, null);
        studentDetails = new ArrayList<POJO>();
        studentDetails.clear();

        if(cursor != null && cursor.getCount() != 0) {
            while(cursor.moveToNext()) {
                update_idno.setText(cursor.getString(cursor.getColumnIndex(DatabaseInfo.IDNO)));
                update_name.setText(cursor.getString(cursor.getColumnIndex(DatabaseInfo.StudentName)));
                update_address.setText(cursor.getString(cursor.getColumnIndex(DatabaseInfo.StudentAddress)));
            }
        }
        else {
            Toast.makeText(this, "No Data Found!", Toast.LENGTH_SHORT).show();
        }

    }
    public void clickUpdate(View view) {
        st_idno = update_idno.getText().toString();
        st_Name = update_name.getText().toString();
        st_Address = update_address.getText().toString();

        if (TextUtils.isEmpty(st_idno) || TextUtils.isEmpty(st_Name) || TextUtils.isEmpty(st_Address)) {
            Toast.makeText(this, "Check the Empty Fields", Toast.LENGTH_LONG).show();
        }
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseInfo.IDNO, st_idno);
            contentValues.put(DatabaseInfo.StudentName, st_Name);
            contentValues.put(DatabaseInfo.StudentAddress, st_Address);

            int updateId = sqLiteDatabase.update(DatabaseInfo.TABLE_NAME, contentValues, DatabaseInfo._ID + " = " + rowId, null);
            if(updateId != -1) {
                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            else {
                Toast.makeText(this, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        sqLiteDatabase.close();
        super.onDestroy();
    }
}