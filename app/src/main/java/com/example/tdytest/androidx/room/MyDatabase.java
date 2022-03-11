package com.example.tdytest.androidx.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Person.class, Animal.class}, version = 1, exportSchema = false)

abstract public class MyDatabase extends RoomDatabase {
    abstract public PersonDao getPersonDao();
    abstract public AnimalDao getAnimalDao();
}
