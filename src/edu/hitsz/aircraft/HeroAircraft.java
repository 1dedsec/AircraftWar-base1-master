package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.application.Main;
import edu.hitsz.application.ImageManager;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /** 攻击方式 */
    public int shootNum = 3;     //子弹一次发射数量
    private int power = 30;       //子弹伤害
    private int direction = -1;  //子弹射击方向 (向上发射：1，向下发射：-1)

    public int getDirection() {
        return direction;
    }
    public int getPower() {
        return power;
    }

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private  HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }
    private volatile static HeroAircraft singleton;

    public static HeroAircraft getSingleton() {
        //双重检查锁定，保证线程安全
        if (singleton == null) {
            synchronized (HeroAircraft.class) {
                if (singleton == null) {
                    singleton = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 100);
                }
            }
        }
        //返回单例调用
        return singleton;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }
    public void increaseHp(int increase){
        hp += increase;
        if(hp > maxHp){
            hp=maxHp;
        }
    }
    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction*5;
        BaseBullet baseBullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            baseBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            res.add(baseBullet);
        }
        return res;
    }

}
