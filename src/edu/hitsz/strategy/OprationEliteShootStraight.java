package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class OprationEliteShootStraight implements Strategy{
    @Override
    public List<BaseBullet> doShootOperation(AbstractAircraft aircraft) {
        if(aircraft instanceof EliteEnemy){
            EliteEnemy eliteAircraft=(EliteEnemy)aircraft;
            List<BaseBullet> res = new LinkedList<>();
           int x = eliteAircraft.getLocationX();
           int y = eliteAircraft.getLocationY() + eliteAircraft.getDirection()*2;
           int speedX = 0;
            int speedY = eliteAircraft.getSpeedY() + eliteAircraft.getDirection()*2;
            BaseBullet baseBullet;
            int start=-eliteAircraft.getShootNum()/2;
            for (int i = 0; i < eliteAircraft.getShootNum(); i++) {
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                speedX=start+i;
                baseBullet = new EnemyBullet(x + (i * 2 - eliteAircraft.getShootNum() + 1) * 10, y, speedX, speedY, eliteAircraft.getPower());
                res.add(baseBullet);
            }
            return res;
        }
        else{
            return new LinkedList<>();
        }
    }
}
