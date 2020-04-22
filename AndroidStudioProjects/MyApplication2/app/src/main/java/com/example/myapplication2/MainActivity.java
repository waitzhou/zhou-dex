package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        double a = 1.0d;
        byte[] bytes = new byte[]{};
        bytes.toString();
        String strings = new String(bytes);
        String[] strings1 = strings.split("|");
        Arrays.binarySearch(strings1,"");
    }

    public int test() {
        InputStream is = null;

        try {
            is =this.openFileInput("");
            is.available();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }
}
