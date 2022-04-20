package edu.hitsz.factory.aircraft_factory;

import edu.hitsz.aircraft.AbstractAircraft;

import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.Main;
import edu.hitsz.application.ImageManager;
public class MobEnemyFactory implements AircraftFactory{
    @Override
    public AbstractAircraft createEnemyAircraft() {
        return new MobEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                0,
                6,
                30
        );
    }
}
