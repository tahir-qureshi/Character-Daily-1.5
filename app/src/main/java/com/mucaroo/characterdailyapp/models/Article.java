package com.mucaroo.characterdailyapp.models;

import com.mucaroo.characterdailyapp.annotations.DBInclude;

/**
 * Created by .Jani on 2/2/2017.
 */

public class Article extends Base {
    @DBInclude
    public int pillar, grade;
    @DBInclude
    public String lesson_author;
    @DBInclude
    public String title;
    @DBInclude
    public String body;
    @DBInclude
    public String image;

}
