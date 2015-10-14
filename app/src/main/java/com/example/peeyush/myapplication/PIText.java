package com.example.peeyush.myapplication;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by peeyush on 14/10/15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PIText {
    int id();
}
