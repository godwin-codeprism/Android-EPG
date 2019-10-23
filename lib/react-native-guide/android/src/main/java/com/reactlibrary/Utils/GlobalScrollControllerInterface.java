package com.reactlibrary.Utils;

import javax.annotation.Nullable;

/**
 * Created by Godwin Vinny Carole K on Mon, 14 Oct 2019 at 17:06.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public interface GlobalScrollControllerInterface {
    void syncScrollBy(int x, int y, int currentId, boolean shouldSmoothScroll);
}
