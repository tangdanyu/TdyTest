package com.example.tdytest.androidx.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDao {

    //保存人
    @Insert
    void saveFeatures(Person... feature);

    //获得全部人
    @Query("select * from Person")
    List<Person> queryAll();

    //根据name查询
    @Query("select * from Person where Person.name =:name")
    Person queryPersonByName(String name);

    //删除特征
    @Delete
    void deletePerson(Person faceData);

    //清空
    @Query("delete from Person")
    void deleteAll();

    //更新人脸特征
    @Update
    void updatePerson(Person faceData);

    //获取最近更新的人脸以便增量查询
    @Query("select max(Person.timestamp) from Person")
    long queryMaxTimestamp();

}
