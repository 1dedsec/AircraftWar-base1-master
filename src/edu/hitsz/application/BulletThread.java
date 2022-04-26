package edu.hitsz.application;

import edu.hitsz.strategy.OperationHeroShootStraight;
import edu.hitsz.strategy.OprationHeroShootSpread;
import edu.hitsz.application.Game.*;

public class BulletThread extends Thread {
    public BulletThread(){}
    @Override
    public void run() {
        Game.heroStrategy=new OprationHeroShootSpread();
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Game.heroStrategy=new OperationHeroShootStraight();
    }
}
