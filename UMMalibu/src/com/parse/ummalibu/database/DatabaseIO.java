package com.parse.ummalibu.database;

import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rjaylward on 9/22/15.
 */
public class DatabaseIO {
    public static final String TABLE_COLUMN_SEPARATOR = "#";

    public static void readCursor(Cursor cursor, @NonNull Object outObj) {
        readCursor(cursor, outObj, null);
    }

    public static void readCursor(Cursor cursor, @NonNull Object outObj, String tableAlias) {
        try {
            Uri uri = ((AbsDatabaseObject) outObj).getUri();

            if(uri == null)
                throw new IllegalArgumentException("must have a uri either via param or object");

            String tableName;
            if(tableAlias != null)
                tableName = tableAlias;
            else
                tableName = DatabaseProvider.getTable(uri);

            String[] columns = cursor.getColumnNames();

            ArrayList<String> annotatedColumnNames = new ArrayList<>();
            ArrayList<String> columnNames = new ArrayList<>();
            for(String columnName : columns) {
                if(columnName.contains(TABLE_COLUMN_SEPARATOR)) {
                    String[] split = columnName.split(TABLE_COLUMN_SEPARATOR);
                    if(split[0].equals(tableName)) {
                        annotatedColumnNames.add(split[1]);
                        columnNames.add(columnName);
                    }
                }
                else {
                    annotatedColumnNames.add(columnName);
                    columnNames.add(columnName);
                }
            }

            HashMap<String, Field> fields = new HashMap<>();
            for(Field field : outObj.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                SerializedName annotation = field.getAnnotation(SerializedName.class);
                if(annotation != null)
                    fields.put(annotation.value(), field);
            }

            for(int i = 0; i < columnNames.size(); i++) {
                String columnNameWithTable = columnNames.get(i);
                String annotatedColumnName = annotatedColumnNames.get(i);

                Field field = fields.get(annotatedColumnName);
                if(field == null)
                    continue;

                Class<?> type = field.getType();
                if(type.equals(Integer.class) || type.equals(Integer.TYPE))
                    field.setInt(outObj, cursor.getInt(cursor.getColumnIndexOrThrow(columnNameWithTable)));
                else if(type.equals(Long.class) || type.equals(Long.TYPE))
                    field.setLong(outObj, cursor.getLong(cursor.getColumnIndexOrThrow(columnNameWithTable)));
                else if(type.equals(Double.class) || type.equals(Double.TYPE))
                    field.setDouble(outObj, cursor.getDouble(cursor.getColumnIndexOrThrow(columnNameWithTable)));
                else if(type.equals(Short.class) || type.equals(Short.TYPE))
                    field.setShort(outObj, cursor.getShort(cursor.getColumnIndexOrThrow(columnNameWithTable)));
                else if(type.equals(Float.class) || type.equals(Float.TYPE))
                    field.setFloat(outObj, cursor.getFloat(cursor.getColumnIndexOrThrow(columnNameWithTable)));
                else if(type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
                    //For some reason I haven't figured out, booleans can be stored as strings or integers, here is how we control for both.
                    boolean stringValue = Boolean.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(columnNameWithTable)));
                    boolean intValue = cursor.getInt(cursor.getColumnIndexOrThrow(columnNameWithTable)) == 1;

                    field.setBoolean(outObj, stringValue || intValue);
                }
                else if(type.equals(String.class))
                    field.set(outObj, cursor.getString(cursor.getColumnIndexOrThrow(columnNameWithTable)));
                else
                    throw new IllegalArgumentException("field type is unsupported");
            }

        } catch(Exception e) {
            Log.e("read cursor error", e.getMessage());
        }

    }

}
