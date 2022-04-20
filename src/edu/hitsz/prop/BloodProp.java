package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class BloodProp extends AbstractProp{
    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        this.locationX=locationX;
        this.locationY=locationY;
        this.speedX=speedX;
        this.speedY=speedY;
    }
    @Override
    public void PropActive(){
        HeroAircraft.getSingleton().increaseHp(30);
    }
}
