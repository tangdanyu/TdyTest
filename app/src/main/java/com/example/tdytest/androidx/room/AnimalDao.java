package com.example.tdytest.androidx.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AnimalDao {
    //新增
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAnimal(Animal animal);
    //查询全部
    @Query("select * from Animal")
    List<Animal> queryAll();
    //根据类型查询
    @Query("select * from Animal where Animal.animalType =:type")
    List<Animal> queryByType(Animal.AnimalType type);
    //删除视频
    @Delete
    void delete(Animal animal);
    //查询不同状态
    @Query("select * from Animal where Animal.status =:status")
    List<Animal> queryByStatus(Animal.ActionStatus status);
    //删除全部
    @Query("delete from Animal")
    void deleteAll();
    //更新
    @Update
    void update(Animal animal);
    //根据name
    @Query("select * from Animal where Animal.name =:name")
    Animal queryAnimalByName(String name);
}
