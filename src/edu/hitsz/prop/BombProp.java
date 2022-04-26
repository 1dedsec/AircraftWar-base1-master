package edu.hitsz.prop;

import edu.hitsz.application.MusicThread;

public class BombProp extends AbstractProp{
    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        this.locationX=locationX;
        this.locationY=locationY;
        this.speedX=speedX;
        this.speedY=speedY;
    }
    @Override
    public void PropActive(){
            System.out.println("BombSupplyActive");
        new MusicThread("src/videos/bomb_explosion.wav").start();
        }

}
