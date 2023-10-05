package vidovic.mvvm.model.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import vidovic.mvvm.model.Motor;


@Database(entities = {Motor.class},version = 1,exportSchema = false)
public abstract class MotorBaza extends RoomDatabase {

    public abstract MotorDAO motorDAO();

    private static MotorBaza instance;

    public static MotorBaza getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MotorBaza.class,
                    "motor-baza"
            ).allowMainThreadQueries().build();
        }
        return instance;
    }

}
