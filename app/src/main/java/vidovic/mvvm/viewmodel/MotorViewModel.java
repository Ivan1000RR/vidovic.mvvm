package vidovic.mvvm.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import vidovic.mvvm.model.Motor;
import vidovic.mvvm.model.dao.MotorBaza;
import vidovic.mvvm.model.dao.MotorDAO;

import java.util.List;


public class MotorViewModel extends AndroidViewModel {

    MotorDAO motorDAO;
    private Motor motor;

    private LiveData<List<Motor>> motori;

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public MotorViewModel(Application application){
        super(application);
        motorDAO = MotorBaza.getInstance(application.getApplicationContext()).motorDAO();
    }

    public LiveData<List<Motor>> dohvatiMotore(){
        motori = motorDAO.dohvatiMotore();
        return motori;
    }

    public void dodajNoviMotor(){
        motorDAO.dodajNoviMotor(motor);
    }

    public void promjeniMotor(){
        motorDAO.promjeniMotor(motor);
    }

    public void obrisiMotor(){motorDAO.obrisiMotor(motor);
    }


}
