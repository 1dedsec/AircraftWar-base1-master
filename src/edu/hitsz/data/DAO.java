package edu.hitsz.data;

import java.util.List;

public interface DAO {
    List<PlayerGrade> getAll();
    void getFromName(String name);
    void update(PlayerGrade pg);
    void deleteFromName(String name);

}
