package com.stunapps.fearlessjumper.component.collider;

import android.graphics.Rect;

import lombok.Builder;

/**
 * Created by anand.verma on 12/01/18.
 */

public class RectCollider extends Collider
{
    public Rect rect;

    public void init(int left, int top, int right, int bottom)
    {
        rect = new Rect(left, top, right, bottom);
    }
}
