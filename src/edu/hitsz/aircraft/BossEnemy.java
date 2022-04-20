package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends MobEnemy{
    public int shootNum = 5;     //子弹一次发射数量
    private int power = 30;       //子弹伤害
    private int direction = 1;  //子弹射击方向 (向上发射：1，向下发射：-1)


    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public int getShootNum() {
        return shootNum;
    }

    public int getPower() {
        return power;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x =getLocationX();
        int y =getLocationY() +direction*2;
        int speedX;
        int speedY = getSpeedY() + direction*5;
        BaseBullet baseBullet;
        int start=-(shootNum)*2;
        for(int i=0; i<(shootNum); i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹散开分散
            speedX=start+4*i;
            baseBullet = new EnemyBullet(x , y, speedX, speedY,power);
            res.add(baseBullet);
        }
        return res;
    }
}
