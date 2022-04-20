package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.factory.aircraft_factory.MobEnemyFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MobEnemyTest {
    private MobEnemy mobEnemy;
    private int beforeLocationY;
    private int beforeHp;
    @BeforeEach
    void setUp() {
        mobEnemy= new MobEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                0,
                10,
                30);
    }

    @AfterEach
    void tearDown() {
        mobEnemy=null;
    }

    @Test
    void forward() {
        System.out.println("**--test add method executed--**");
        beforeLocationY=mobEnemy.getLocationY();
        mobEnemy.forward();
        System.out.println("aircraft locationY change from "+beforeLocationY+" to "+mobEnemy.getLocationY());
        assertNotEquals(beforeLocationY,mobEnemy.getLocationY());
    }

    @Test
    void decreaseHp() {
        System.out.println("**--test add method executed--**");
        beforeHp=mobEnemy.getHp();
        mobEnemy.decreaseHp(20);
        System.out.println("Hp decrease from "+beforeHp+" to "+mobEnemy.getHp());
        assertNotEquals(beforeHp,mobEnemy.getHp());

    }
}