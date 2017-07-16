package com.mucaroo.characterdailyapp.models;

/**
 * Created by Valentine on 10/18/2015.
 */
public class Pillars {
    private Long id;
    private String value;
    private String pillarName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPillarName() {
        return pillarName;
    }

    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
