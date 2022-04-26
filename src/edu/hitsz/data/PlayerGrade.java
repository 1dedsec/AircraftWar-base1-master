package edu.hitsz.data;

import java.io.Serializable;

public class PlayerGrade implements Serializable{
    private int ranking;
    private String name;
    private int score;
    private int time;
    public  PlayerGrade(String name,int ranking,int score,int time){
        this.name=name;
        this.ranking=ranking;
        this.score=score;
        this.time=time;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getRanking() {
        return ranking;
    }

    public String getTime() {
        float realTime=(float)(time)/1000;
        int hours=(int)(realTime/3600);
        int seconds=(int)(realTime%60);
        int minutes=(int)((realTime-hours*3600)/60);
        return hours+":"+minutes+":"+seconds;
    }

    @Override
    public String toString() {
        float realTime=(float)(time)/1000;
        int hours=(int)(realTime/3600);
        int seconds=(int)(realTime%60);
        int minutes=(int)((realTime-hours*3600)/60);
        return
                "第"+ranking+"名"+name+
                ",得分=" + score +
                ",用时="+hours+":"+minutes+":"+seconds
                ;
    }
}
