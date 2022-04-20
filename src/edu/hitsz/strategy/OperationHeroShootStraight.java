package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class OperationHeroShootStraight implements Strategy{
    @Override
    public List<BaseBullet> doShootOperation(AbstractAircraft aircraft) {
        HeroAircraft heroAircraft=(HeroAircraft)aircraft;
        List<BaseBullet> res = new LinkedList<>();
        int x = heroAircraft.getLocationX();
        int y = heroAircraft.getLocationY() +heroAircraft.getDirection()*2;
        int speedX = 0;
        int speedY =heroAircraft.getSpeedY() +heroAircraft.getDirection()*5;
        BaseBullet baseBullet;
        for(int i=0; i<heroAircraft.shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            baseBullet = new HeroBullet(x + (i*2 - heroAircraft.shootNum + 1)*10, y, speedX, speedY,heroAircraft.getPower());
            res.add(baseBullet);
        }
        return res;
    }
}
