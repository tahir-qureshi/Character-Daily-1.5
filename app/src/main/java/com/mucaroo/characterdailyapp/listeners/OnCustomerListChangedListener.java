package com.mucaroo.characterdailyapp.listeners;

import com.mucaroo.characterdailyapp.models.Pillars;

import java.util.List;

/**
 * Created by Valentine on 9/5/2015.
 */
public interface OnCustomerListChangedListener {
    void onNoteListChanged(List<Pillars> customers);
}
