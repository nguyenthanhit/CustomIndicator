package com.example.customindicator;

/**
 * Created by Admin on 8/4/2017.
 */

public class PagesLessException extends Exception {

    @Override
    public String getMessage() {
        return "Pages must equal or larger than 2";
    }
}
