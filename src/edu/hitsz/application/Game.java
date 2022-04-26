package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.data.PlayerDaoImpl;
import edu.hitsz.data.PlayerGrade;
import edu.hitsz.factory.aircraft_factory.AircraftFactory;
import edu.hitsz.factory.aircraft_factory.BossFactory;
import edu.hitsz.factory.aircraft_factory.MobEnemyFactory;
import edu.hitsz.factory.aircraft_factory.EliteEnemyFactory;
import edu.hitsz.factory.prop_factory.BloodPropFactory;
import edu.hitsz.factory.prop_factory.BombPropFactory;
import edu.hitsz.factory.prop_factory.BulletPropFactory;
import edu.hitsz.factory.prop_factory.PropFactory;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.*;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<AbstractAircraft> bossAircraft;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;
    private PlayerDaoImpl playersDao;
    private int enemyMaxNumber = 5;
    private int propsMaxNumber = 5;
    private int bossScoreThreshold=1000;
    private boolean gameOverFlag = false;
    private int score = 0;
    private int time = 0;
    public static Strategy heroStrategy;
    private Strategy eliteStrategy;
    private Strategy bossStrategy;
    private Image backGroundImage;
    private MusicThread bossBgmThread;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 800;
    private int cycleTime = 0;
    private AircraftFactory aircraftFactory;
    private PropFactory propFactory;

    public Game() {
        heroAircraft =HeroAircraft.getSingleton();
        enemyAircrafts = new LinkedList<>();
        bossAircraft = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();
        //Scheduled 线程池，用于定时任务调度
        executorService = new ScheduledThreadPoolExecutor(1);
        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);
        heroStrategy=new OperationHeroShootStraight();
        eliteStrategy=new OprationEliteShootStraight();
        bossStrategy=new OprationBossShootSpread();
        playersDao = new PlayerDaoImpl();
        switch (Main.MODE){
            case 0:
                {backGroundImage=ImageManager.BACKGROUND_IMAGE_EASY;
                    break;
                }
            case 1:
                {backGroundImage=ImageManager.BACKGROUND_IMAGE_NORMAL;
                    break;
                }
            case 2:
                {backGroundImage=ImageManager.BACKGROUND_IMAGE_HARD;
                    break;
                }
            default:backGroundImage=ImageManager.BACKGROUND_IMAGE_NORMAL;
        }
    }
    /**
     * 游戏启动入口，执行游戏逻辑
     * @return
     */
    public boolean action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;
            Random random=new Random();
            int seed=random.nextInt(4);
            // 周期性执行（控制频率）
            if(this.score>=bossScoreThreshold){
                if(bossAircraft.size()<1){
                    aircraftFactory=new BossFactory();
                    bossAircraft.add(aircraftFactory.createEnemyAircraft());
                    setBossBgm();
                }
                bossScoreThreshold+=1000;
            }
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生

                if (enemyAircrafts.size() < enemyMaxNumber) {
                    if(seed==0) {
                        aircraftFactory=new EliteEnemyFactory();
                        enemyAircrafts.add(aircraftFactory.createEnemyAircraft());

                    }
                   else {
                        aircraftFactory=new MobEnemyFactory();
                        enemyAircrafts.add(aircraftFactory.createEnemyAircraft());
                   }
                }
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            //道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                playersDao.show();
                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");
                synchronized (Main.object) {
                    Main.object.notify();
                }
            }
        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
        return true;
    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // TODO 敌机射击

        for(AbstractAircraft enemyAircraft : enemyAircrafts){
          enemyBullets.addAll(enemyAircraft.ShootStrategy(eliteStrategy));}
        for(AbstractAircraft enemyAircraft : bossAircraft){
            enemyBullets.addAll(enemyAircraft.ShootStrategy(bossStrategy));}
        // 英雄射击
        heroBullets.addAll(heroAircraft.ShootStrategy(heroStrategy));
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
        for(AbstractAircraft enemyAircraft : bossAircraft){
            enemyAircraft.forward();
        }
    }
    private void propsMoveAction(){
        for(AbstractProp abstractProp : props){
            abstractProp.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        Random random=new Random();
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet :enemyBullets) {
            if(bullet.notValid()){
                continue;
            }
            if(heroAircraft.crash(bullet)){
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }

        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    new MusicThread("src/videos/bullet_hit.wav").start();
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        if(enemyAircraft instanceof EliteEnemy){
                            score+=30;
                            //添加道具掉落
                            int seed=random.nextInt(10);
                            if(seed>=6) {
                                if (props.size() < propsMaxNumber) {
                                    if (seed >= 9) {
                                        propFactory = new BloodPropFactory();
                                    } else if (seed >= 8) {
                                        propFactory = new BulletPropFactory();
                                    } else {
                                        propFactory = new BombPropFactory();
                                    }
                                    props.add(propFactory.createProp(
                                            enemyAircraft.getLocationX(),
                                            enemyAircraft.getLocationY(),
                                            5,
                                            2));
                                }

                            }
                        }
                        else {
                            score += 10;
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
            for (AbstractAircraft enemyAircraft : bossAircraft) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        removeBossBgm(bossBgmThread);
                        score += 200;
                        //添加道具掉落
                        int seed = random.nextInt(10);
                        if (seed >= 0) {
                            if (props.size() < propsMaxNumber) {
                                if (seed >= 6) {
                                    propFactory = new BloodPropFactory();
                                } else if (seed >= 3) {
                                    propFactory = new BulletPropFactory();
                                } else {
                                    propFactory = new BombPropFactory();
                                }
                                props.add(propFactory.createProp(
                                        enemyAircraft.getLocationX(),
                                        enemyAircraft.getLocationY(),
                                        5,
                                        2));
                            }

                        }
                    }
                }
            }
        }

        // 我方获得道具，道具生效
        for(AbstractProp abstractProp:props){
            if(abstractProp.notValid()){
                continue;
            }
            else if(heroAircraft.crash(abstractProp)){
                if(abstractProp instanceof BulletProp){
                    new BulletThread().start();
                    new MusicThread("src/videos/bullet.wav").start();
                }
                else{
                    abstractProp.PropActive();
                }
                abstractProp.vanish();
            }
        }
    }


    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        bossAircraft.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(backGroundImage, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(backGroundImage, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, bossAircraft);
        paintImageWithPositionRevised(g ,props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    public int getScore() {
        return score;
    }

    public PlayerDaoImpl getPlayersDao() {
        return playersDao;
    }

    public int getTime() {
        return time;
    }
    public boolean getGameOverFlag(){return gameOverFlag;}
    public void setBossBgm(){
        Main.getBgmThread().setPauseFlag(true);
        bossBgmThread=new MusicThread("src/videos/bgm_boss.wav");
        bossBgmThread.start();
    }
    public void removeBossBgm(MusicThread bossBgmThread){
        bossBgmThread.setPauseFlag(true);
        bossBgmThread.setEndFlag(true);
        Main.getBgmThread().setPauseFlag(false);
    }
}
