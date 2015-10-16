package com.example.peeyush.myapplication.piadapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by peeyush on 14/10/15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PIImage {

    int id();
    int width();
    int height();
    boolean isSourceFile();

}
