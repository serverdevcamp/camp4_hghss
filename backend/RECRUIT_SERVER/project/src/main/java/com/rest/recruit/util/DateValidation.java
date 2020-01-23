package com.rest.recruit.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidation {


    public static boolean validationDate(String checkDate) {

        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            dateFormat.setLenient(false);
            dateFormat.parse(checkDate);
            return  true;

        }catch (ParseException e){

            return  false;
        }

    }

}
