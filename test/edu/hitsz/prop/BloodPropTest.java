package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloodPropTest {
    private BloodProp bloodProp;
    private HeroAircraft heroAircraft;
    private int beforeLocationY;
    private int beforeLocationX;
    private int beforeHp;
    @BeforeEach
    void setUp() {
        heroAircraft=HeroAircraft.getSingleton();
        heroAircraft.decreaseHp(80);
        bloodProp =new BloodProp(0,0,2,3);
    }

    @AfterEach
    void tearDown() {
        heroAircraft=null;
        bloodProp=null;
    }

    @Test
    void forward() {
        System.out.println("**--test add method executed--**");
        beforeLocationY=bloodProp.getLocationY();
        beforeLocationX=bloodProp.getLocationX();
        bloodProp.forward();
        System.out.println("aircraft location change from ("+beforeLocationX+","+beforeLocationY+") to ("+bloodProp.getLocationX()+","+bloodProp.getLocationY()+")");
        assertNotEquals(beforeLocationY,bloodProp.getLocationY());
        assertNotEquals(beforeLocationX,bloodProp.getLocationX());
    }

    @Test
    void propActive() {
        System.out.println("**--test add method executed--**");
        beforeHp=heroAircraft.getHp();
        bloodProp.PropActive();
        System.out.println("heroAircraft Hp has increase from "+beforeHp+" to "+heroAircraft.getHp());
        assertNotEquals(beforeHp,heroAircraft.getHp());
    }
}