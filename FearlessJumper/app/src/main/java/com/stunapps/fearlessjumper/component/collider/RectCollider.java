package com.stunapps.fearlessjumper.component.collider;

import android.graphics.Rect;

/**
 * Created by anand.verma on 12/01/18.
 */

public class RectCollider extends Collider
{
    public Rect rectCollider;

    public RectCollider(int left, int top, int right, int bottom)
    {
        rectCollider = new Rect(left, top, right, bottom);
    }
}
