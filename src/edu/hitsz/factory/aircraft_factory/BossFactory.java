package edu.hitsz.factory.aircraft_factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossFactory implements AircraftFactory{
    @Override
    public AbstractAircraft createEnemyAircraft() {
        return new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                5,
                0,
                1000);
    }
}
