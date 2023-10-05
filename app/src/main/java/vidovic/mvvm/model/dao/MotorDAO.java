package vidovic.mvvm.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import vidovic.mvvm.model.Motor;

import java.util.List;


@Dao
public interface MotorDAO {

    @Query("select * from motor order by proizvodjacMotora")
    LiveData<List<Motor>> dohvatiMotore();

    @Insert
    void dodajNoviMotor(Motor motor);

    @Update
    void promjeniMotor(Motor motor);

    @Delete
    void obrisiMotor(Motor motor);
}

