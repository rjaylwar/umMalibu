package com.parse.ummalibu.util;

import java.util.ArrayList;

/**
 * Created by rjaylward on 10/18/15
 */
public class Util {

    public static final String COMMA_SEPARATOR = ", ";

    public static String printCommaSeparatedArray(Object[] array) {
        String result = "";
        for(int i = 0; i < array.length; i++) {
            if(i > 0)
                result += COMMA_SEPARATOR;

            result += array[i].toString();
        }

        return result;
    }

    public static String printArrayListAsString(ArrayList arrayList) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < arrayList.size(); i++) {
            if(i > 0)
                builder.append(COMMA_SEPARATOR);

            builder.append(arrayList.get(i));
        }

        return builder.toString();
    }

    public static String[] convertStringToArray(String str){
        return str.split(COMMA_SEPARATOR);
    }

    public static ArrayList<String> convertStringToArrayList(String str){
        String[] arr = str.split(COMMA_SEPARATOR);
        ArrayList<String> list = new ArrayList<>();
        for(String s : arr) {
            list.add(s);
        }

        return list;
    }
}
