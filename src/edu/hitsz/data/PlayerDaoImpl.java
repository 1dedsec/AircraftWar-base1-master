package edu.hitsz.data;

import edu.hitsz.application.Main;

import java.io.*;
import java.util.*;

public class PlayerDaoImpl implements DAO{
    private LinkedList<PlayerGrade> playersGrade;
    public PlayerDaoImpl(){
        playersGrade =new LinkedList<PlayerGrade>();
        playersGrade=readFromFile();
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
    public static void writeToFile(LinkedList<PlayerGrade> playersGrade){
        File file;
        if(Main.MODE==0) {
            file = new File("src/edu/hitsz/easy.txt");
        }
        else if (Main.MODE==1){
            file = new File("src/edu/hitsz/normal.txt");
        }
        else {
            file = new File("src/edu/hitsz/hard.txt");
        }
        int n=0;
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            PlayerGrade[] obj=new PlayerGrade[playersGrade.size()];
            for(PlayerGrade playerGrade:playersGrade){
                obj[n++]=playerGrade;
            }
            out.writeObject(obj);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static LinkedList<PlayerGrade> readFromFile(){
        File file;
        if(Main.MODE==0) {
            file = new File("src/edu/hitsz/easy.txt");
        }
        else if (Main.MODE==1){
            file = new File("src/edu/hitsz/normal.txt");
        }
        else {
            file = new File("src/edu/hitsz/hard.txt");
        }
        LinkedList<PlayerGrade>playersGrade =new LinkedList<>();
        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(file)))
        {
            PlayerGrade[] obj = (PlayerGrade[])out.readObject();
            for(PlayerGrade playerGrade:obj) {
                playersGrade.add(playerGrade);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return playersGrade;
    }
}

