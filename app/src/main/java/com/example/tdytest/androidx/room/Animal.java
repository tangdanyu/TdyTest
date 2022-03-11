package com.example.tdytest.androidx.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Animal {
    @NonNull
    @PrimaryKey
    private String name;
    private String type;
    private AnimalType animalType;
    private ActionStatus status;
    public enum AnimalType{
        dag,
        cat,
        fish;
    }
    public enum ActionStatus{
        run,
        walk;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public ActionStatus getStatus() {
        return status;
    }

    public void setStatus(ActionStatus status) {
        this.status = status;
    }


}
