package com.stockeate.stockeate.utiles;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class utiles {

    public static String leerJson(Context context, String fileName) throws IOException {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));

        String content = "";
        String line;
        while ((line = reader.readLine()) != null) {
            content = content + line;
        }

        return content;

    }

    public static String escribirJson(Context context, String fileName, String data) throws IOException {
        BufferedWriter writer = null;
        FileOutputStream fileOutputStream = context.openFileOutput(fileName, context.MODE_WORLD_READABLE);

        writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        writer.write(data);
        writer.flush();
        Toast.makeText(context, "Escrito OK", Toast.LENGTH_SHORT).show();
        return fileName;
    }
}