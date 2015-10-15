package com.breakout.sprites;

import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {

    protected int x; // Current position X on board of object
    protected int y; // Current position Y on board of object
    protected int width; // Width of object
    protected int heigth; // Height of object
    protected Image image; //Image file


    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return heigth;
    }

    public Image getImage()
    {
      return image;
    }

    public Rectangle getRect()
    {
      return new Rectangle(x, y, 
          image.getWidth(null), image.getHeight(null));
    }
}
