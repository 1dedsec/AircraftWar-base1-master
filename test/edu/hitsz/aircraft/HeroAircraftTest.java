package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.prop_factory.BombPropFactory;
import edu.hitsz.prop.BombProp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    private HeroAircraft heroAircraft;
    private List<BaseBullet> list;
    private BombProp bombProp;
    @BeforeEach
    void setUp() {
        heroAircraft=HeroAircraft.getSingleton();
    }

    @AfterEach
    void tearDown() {
        heroAircraft=null;
        list=null;
    }

    @Test
    void crash() {
        System.out.println("**--test add method executed--**");
        bombProp=new BombProp(heroAircraft.getLocationX(),heroAircraft.getLocationY(),0,heroAircraft.getSpeedY());
        assertTrue(heroAircraft.crash(bombProp));
    }

    @Test
    void shoot() {
        System.out.println("**--test add method executed--**");
        list=heroAircraft.shoot();
        System.out.println("heroAircraft has shoot "+list.size()+" bullets");
        assertEquals(list.size(),heroAircraft.shootNum);
    }
}