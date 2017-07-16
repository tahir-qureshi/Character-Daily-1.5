package com.mucaroo.characterdailyapp.listeners;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Valentine on 9/4/2015.
 */
public interface OnStartDragListener {
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
