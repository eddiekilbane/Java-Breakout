package com.breakout.objects;

import javax.swing.ImageIcon;

import com.breakout.sprites.Sprite;


public class Brick extends Sprite {

    String brickie = "/resources/brick.jpg";

    boolean destroyed;

    public Brick(int x, int y) {
      this.x = x;
      this.y = y;

      ImageIcon ii = new ImageIcon(this.getClass().getResource(brickie));
      image = ii.getImage();

      width = image.getWidth(null);
      heigth = image.getHeight(null);

      destroyed = false;
    }

    public boolean isDestroyed()
    {
      return destroyed;
    }

    public void setDestroyed(boolean destroyed)
    {
      this.destroyed = destroyed;
    }

}