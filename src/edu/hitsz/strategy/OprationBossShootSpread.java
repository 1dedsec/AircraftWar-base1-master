package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class OprationBossShootSpread implements Strategy{
    @Override
    public List<BaseBullet> doShootOperation(AbstractAircraft aircraft) {
        BossEnemy heroAircraft=(BossEnemy) aircraft;
        List<BaseBullet> res = new LinkedList<>();
        int x = heroAircraft.getLocationX();
        int y = heroAircraft.getLocationY() +heroAircraft.getDirection()*2;
        int speedX;
        int speedY = heroAircraft.getSpeedY() + heroAircraft.getDirection()*5;
        BaseBullet baseBullet;
        int start=-(heroAircraft.shootNum)*2;
        for(int i=0; i<(heroAircraft.shootNum); i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹散开分散
            speedX=start+4*i;
            baseBullet = new EnemyBullet(x , y, speedX, speedY,heroAircraft.getPower());
            res.add(baseBullet);
        }
        return res;
    }
}
