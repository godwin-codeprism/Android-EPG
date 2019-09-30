package com.grv_app.utils;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Godwin Vinny Carole K on Wed, 25 Sep 2019 at 11:40.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public interface ChildFocusListener {
    void onChildScrolled(int amountOfScroll, RecyclerView childRecyclerView);

}
