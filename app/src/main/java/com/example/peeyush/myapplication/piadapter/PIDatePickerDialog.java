package com.example.peeyush.myapplication.piadapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by peeyush on 17/10/15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PIDatePickerDialog {
    int id();
    boolean isDeafultDateSet();
    boolean allowFutureDate();
}
