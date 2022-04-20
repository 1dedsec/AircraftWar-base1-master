package edu.hitsz.factory.aircraft_factory;


import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;

import edu.hitsz.application.Main;
import edu.hitsz.application.ImageManager;

import java.util.Random;

public class EliteEnemyFactory implements AircraftFactory{
    @Override
    public AbstractAircraft createEnemyAircraft() {
        Random random=new Random();
        int seed=random.nextInt(6);
        if(seed>=3){
            return new EliteEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                8,
                6,
                90);
        }
        else{
            return new EliteEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                    -8,
                    6,
                    90);
        }
    }
}
