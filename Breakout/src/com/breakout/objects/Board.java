package com.breakout.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import com.breakout.interfaces.Commons;
import com.breakout.score.Lives;

@SuppressWarnings("serial")
public class Board extends JPanel implements Commons {

    Image ii;
    Timer timer;
    String message = "Game Over";
    Ball ball;
    Paddle paddle;
    Brick bricks[];
    Lives lives;

    boolean ingame = true;
    int timerId;


    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);

        bricks = new Brick[30];
        setDoubleBuffered(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 5);
    }

    public void addNotify() {
    	
    	super.addNotify();
        gameInit();
    }

    public void gameInit() {
    	
    	ball = new Ball();
        paddle = new Paddle();
        
        // Set Lives to 3
        lives = new Lives();

        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
            	// X Gap between bricks + X gap from left side, Y Gap between bricks + Y gap from top
                bricks[k] = new Brick(j * 40 + 30, i * 20 + 50);
                k++;
            }
        }
    }


    public void paint(Graphics g) {
        super.paint(g);

        if (ingame) {
            g.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                        ball.getWidth(), ball.getHeight(), this);
            g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                        paddle.getWidth(), paddle.getHeight(), this);
            int x = 0;
            for (int i = 0; i < 30; i++) {
                if (!bricks[i].isDestroyed()){
                	x++;
                    g.drawImage(bricks[i].getImage(), bricks[i].getX(),
                                bricks[i].getY(), bricks[i].getWidth(),
                                bricks[i].getHeight(), this);
                }
                	
            }
            // Game info bar ( bottom of application )
            g.drawString("SCORE: " +(30 - x), Commons.SCORE_WIDTH, Commons.SCORE_HEIGHT);
            g.drawString("LIVES: " + ( Lives.NUMBER_OF_LIVES - lives.getRemainingLives() ), Commons.SCORE_WIDTH + 80, Commons.SCORE_HEIGHT);
        } else {

            Font font = new Font("Verdana", Font.BOLD, 18);
            FontMetrics metr = this.getFontMetrics(font);

            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(message,
                         (Commons.WIDTH - metr.stringWidth(message)) / 2,
                         Commons.WIDTH / 2);
        }


        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            paddle.keyPressed(e);
        }
    }


    class ScheduleTask extends TimerTask {

        public void run() {

            ball.move();
            paddle.move();
            checkCollision();
            repaint();

        }
    }
    /**
     *  
     **/
    public void stopGame() {
        ingame = false;
        timer.cancel();
    }
    
    /**
     * 
     **/
    public void pauseGame(){
    	timer.cancel();
    }
    
    /**
     * 
     **/
    public void resumeGame(){
    	timer = new Timer();
    	timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 5);
    }

    /**
     * 
     * */
    public void checkCollision() {
        if (ball.getRect().getMaxY() > Commons.BOTTOM) { 	
        	checkLives();
        }

        for (int i = 0, j = 0; i < 30; i++) {
            if (bricks[i].isDestroyed()) {
                j++;
            }
            if (j == 30) {
                message = "Victory";
                stopGame();
            }
        }

        if ((ball.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int)paddle.getRect().getMinX();
            int ballLPos = (int)ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {
                ball.setXDir(-1);
                ball.setYDir(-1);
            }

            if (ballLPos >= first && ballLPos < second) {
                ball.setXDir(-1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos >= second && ballLPos < third) {
                ball.setXDir(0);
                ball.setYDir(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {
                ball.setXDir(1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos > fourth) {
                ball.setXDir(1);
                ball.setYDir(-1);
            }
        }


        for (int i = 0; i < 30; i++) {
            if ((ball.getRect()).intersects(bricks[i].getRect())) {

                int ballLeft = (int)ball.getRect().getMinX();
                int ballHeight = (int)ball.getRect().getHeight();
                int ballWidth = (int)ball.getRect().getWidth();
                int ballTop = (int)ball.getRect().getMinY();

                Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if (!bricks[i].isDestroyed()) {
                    if (bricks[i].getRect().contains(pointRight)) {
                        ball.setXDir(-1);
                    }
                    else if (bricks[i].getRect().contains(pointLeft)) {
                        ball.setXDir(1);
                    }
                    if (bricks[i].getRect().contains(pointTop)) {
                        ball.setYDir(1);
                    }
                    else if (bricks[i].getRect().contains(pointBottom)) {
                        ball.setYDir(-1);
                    }
                    bricks[i].setDestroyed(true);
                }
            }
        }
    }
    /**
     * Function to check that the player has remaining lives 
     **/
	private void checkLives() {
		if ( lives.getRemainingLives() == Lives.NUMBER_OF_LIVES ){
			stopGame();
		}else{
			lives.setRemainingLives( lives.getRemainingLives() + 1 );
			resetBall();
		}
	}
	
	/**
	 * Function to reset ball location 
	 **/
	private void resetBall(){
		ball.setXDir(1);
		ball.setYDir(-1);
		ball.setX(150);
		ball.setY(240);
		pauseGame();
		resumeGame();
	}
}
