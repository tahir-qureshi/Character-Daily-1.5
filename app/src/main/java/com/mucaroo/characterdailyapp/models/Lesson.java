package com.mucaroo.characterdailyapp.models;

import com.mucaroo.characterdailyapp.annotations.DBInclude;

/**
 * Created by .Jani on 2/8/2017.
 */

public class Lesson extends Article {
    @DBInclude
    public String quote, quote_author;
}
