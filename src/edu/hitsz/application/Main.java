package edu.hitsz.application;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.List;

import edu.hitsz.data.PlayerDaoImpl;
import edu.hitsz.MenuPage;
import edu.hitsz.RankPage;
import edu.hitsz.data.PlayerGrade;
/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static  int MODE=0;
    public static boolean MUSIC_FLAG;
    public static boolean gameOverFlag;
    public static Object object=new Object();
    public static LinkedList<PlayerGrade> playerGradeList;
    private static MusicThread bgmThread;
    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel menuPanel=CreateMenuPanel();
        frame.setContentPane(menuPanel);
        frame.setVisible(true);
        synchronized (object){
            try {
                object.wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        frame.remove(menuPanel);
        Game game = new Game();
        frame.setContentPane(game);
        frame.setVisible(true);
        gameOverFlag=game.getGameOverFlag();
        bgmThread=new MusicThread("src/videos/bgm.wav");
        bgmThread.start();
        game.action();
        synchronized (object){
            try {
                object.wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        frame.remove(game);
        bgmThread.setPauseFlag(true);
        bgmThread.setEndFlag(true);
        new MusicThread("src/videos/game_over.wav").start();
        RankPage rankPage=new RankPage();
        JPanel rankPanel=CreateRankPanel(rankPage);
        frame.setContentPane(rankPanel);
        frame.setVisible(true);
        String userName=JOptionPane.showInputDialog("本次你的得分是"+game.getScore()+"\n请输入你的名字记录得分");
        if(userName.equals("")){
            userName="您";
        }
        PlayerGrade pg =new PlayerGrade(userName,1,game.getScore(),game.getTime());
        playerGradeList=PlayerDaoImpl.readFromFile();
        playerGradeList.add(pg);
        PlayerDaoImpl.writeToFile(playerGradeList);
        rankPage.updateData(playerGradeList);
    }

    public static JPanel CreateMenuPanel(){
        return new MenuPage().getMenuPanel();
    }
    public static JPanel CreateRankPanel(RankPage rankPage){
        return rankPage.getRankPanel();
    }
    public static MusicThread getBgmThread(){
        return bgmThread;
    }
}
