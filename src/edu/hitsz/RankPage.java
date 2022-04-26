package edu.hitsz;

import edu.hitsz.application.Main;
import edu.hitsz.data.PlayerDaoImpl;
import edu.hitsz.data.PlayerGrade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class RankPage {
    private JPanel rankPanel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JScrollPane tableScrollPane;
    private JLabel modeName;
    private JLabel 难度;
    private JPanel 排行榜;
    private JPanel topPanel;
    private JPanel modePanel;
    private JPanel deletePanel;
    private List<PlayerGrade> playersGrade;
    private DefaultTableModel model;
    public RankPage() {
        playersGrade=PlayerDaoImpl.readFromFile();
        updateData(playersGrade);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=scoreTable.getSelectedRow();
                System.out.println(row);
                if(row !=1){
                    model.removeRow(row);
                }
                LinkedList<PlayerGrade> playerGradeLinkedList=PlayerDaoImpl.readFromFile();
                playerGradeLinkedList.remove(row);
                updateData(playerGradeLinkedList);
                PlayerDaoImpl.writeToFile(playerGradeLinkedList);
            }
        });
        modeName.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if(Main.MODE==0){
                    modeName.setText("EASY");
                }
                else if(Main.MODE==1){
                    modeName.setText("NORMAL");
                }
                else{
                    modeName.setText("HARD");
                }
            }
        });
    }

    public JPanel getRankPanel() {
        return topPanel;
    }

    public void updateData(List<PlayerGrade> playerGradeList){
        String []columnName = {"名次","玩家名","得分","记录时间"};
        String [][]tableData ={};
        model=new DefaultTableModel(tableData,columnName);
        PlayerDaoImpl.merge(playerGradeList);
        for(PlayerGrade playerGrade:playerGradeList){
            Vector rowData=new Vector();
            rowData.add(playerGrade.getRanking());
            rowData.add(playerGrade.getName());
            rowData.add(playerGrade.getScore());
            rowData.add(playerGrade.getTime());
            model.insertRow(model.getRowCount(),rowData);
        }
        scoreTable.setModel(model);
        tableScrollPane.setViewportView(scoreTable);
    }
}
