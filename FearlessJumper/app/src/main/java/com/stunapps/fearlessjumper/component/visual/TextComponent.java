package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Paint;

/**
 * Created by sunny.s on 11/01/18.
 */

public class TextComponent extends RenderableComponent<TextComponent.Text>
{
    public String text;
    public Paint paint;

    public TextComponent(String text, Paint paint)
    {
        super(RenderType.TEXT);
        this.text = text;
        this.paint = paint;
    }

    @Override
    public Text getRenderable()
    {
        return null;
    }

    public class Text
    {
        public String text;
        public Paint paint;
    }
}
