package com.breakout.main;

import javax.swing.JFrame;

import com.breakout.interfaces.Commons;
import com.breakout.objects.Board;

@SuppressWarnings("serial")
public class Breakout extends JFrame {

    public Breakout()
    {
        add(new Board());
        setTitle("Breakout");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Commons.WIDTH, Commons.HEIGTH);
        setLocationRelativeTo(null);
        setIgnoreRepaint(true);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Breakout();
    }
}