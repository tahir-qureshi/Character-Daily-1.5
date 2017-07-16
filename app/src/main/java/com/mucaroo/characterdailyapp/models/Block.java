package com.mucaroo.characterdailyapp.models;

import com.mucaroo.characterdailyapp.enums.BlockType;

/**
 * Created by .Jani on 2/2/2017.
 */

public class Block extends Base {
    public BlockType type;
    public Article article;
    public Lesson lesson;

    public Block() {}

    public Block(BlockType type, Article article) {
        this.type = type;
        this.article = article;

    }

//    public Block(BlockType type, Lesson lesson) {
//        this.type = type;
//        this.lesson = lesson;
//    }
}
