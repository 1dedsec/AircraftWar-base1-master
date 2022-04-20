package edu.hitsz.data;

import java.io.File;
import java.util.*;
import java.io.BufferedWriter;

import java.io.FileWriter;

import java.io.IOException;

public class PlayerDaoImpl implements DAO{
    private List<PlayerGrade> playersGrade;
    public PlayerDaoImpl(){
        playersGrade =new LinkedList<PlayerGrade>();
        playersGrade.add(new PlayerGrade("得分王小子",1,30000,135640));
        playersGrade.add(new PlayerGrade("惹晒",2,29999,364300));
        playersGrade.add(new PlayerGrade("菜鸡",3,2400,104000));
        playersGrade.add(new PlayerGrade("369",4,0,664300));
        playersGrade.add(new PlayerGrade("clearlove",7,4396,752350));
        playersGrade.add(new PlayerGrade("xiaohu",5,2200,192387));
        playersGrade.add(new PlayerGrade("喻文波",9,48231,835471));
    }
    @Override
    public List<PlayerGrade> getAll() {
        return playersGrade;
    }

    @Override
    public void getFromName(String name) {
        for(PlayerGrade item:playersGrade){
            if(item.getName()==name){
                System.out.println(name+"'s information is"+item);
                return;
            }
        }
        System.out.println("sorry ! no found");
        return;
    }

    @Override
    public void update(PlayerGrade pg) {
        playersGrade.add(pg);
    }

    @Override
    public void deleteFromName(String name) {
        for(PlayerGrade item:playersGrade){
            if(item.getName()==name){
                playersGrade.remove(item);
                System.out.println(name+"'s information has been deleted");
                return;
            }
        }
        System.out.println("sorry ! no found");
    }

    public static List<PlayerGrade> merge(List<PlayerGrade>playersGrade){
        ArrayList<PlayerGrade> res=new ArrayList<>();
        //排序方式：(根据类中年龄升序排序)
        //解释：对persons(person类)变量进行排序,a与b是两个person类的变量，若a.age-b.age>0则a排在b前，否则b排在a前(升序)
        Collections.sort(playersGrade,(a, b)->b.getScore()-a.getScore());
        for(int i=0;i<playersGrade.size();i++){
            res.add(playersGrade.get(i));
            playersGrade.get(i).setRanking(i+1);
        }
        return res;
    }

    public void show(){
        try {
            File file = new File("src/edu/hitsz/排行榜.txt");
            System.out.println("******************************************************************");
            System.out.println("                             得分排行榜                             ");
            System.out.println("******************************************************************");
            List<PlayerGrade> sortedPlayersGrade=PlayerDaoImpl.merge(playersGrade);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write("******************************************************************");
            bw.newLine();
            bw.write("                             得分排行榜                             ");
            bw.newLine();
            bw.write("******************************************************************");
            bw.newLine();
            for (PlayerGrade item : sortedPlayersGrade) {
                System.out.println(item.toString());
                bw.write(item.toString());
                bw.newLine();
            }
            bw.close();
           /* else{
                FileWriter fileWriter = new FileWriter(file.getName(),true);
                PlayerGrade pg= playersGrade.get(playersGrade.size()-1);
                fileWriter.write(pg.toString()+"\n");
                fileWriter.close();
            }*/
            System.out.println("finish");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
