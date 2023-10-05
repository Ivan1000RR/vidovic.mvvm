package vidovic.mvvm.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;




@Entity(tableName = "motor")
public class Motor {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "proizvodjacMotora")
    private String proizvodjac;
    private String model;
    private int snaga;
    private int zapremninaMotora;
    private String putanjaSlike;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSnaga() {
        return snaga;
    }

    public void setSnaga(int snaga) {
        this.snaga = snaga;
    }

    public int getZapremninaMotora() {
        return zapremninaMotora;
    }

    public void setZapremninaMotora(int zapremninaMotora) {
        this.zapremninaMotora = zapremninaMotora;
    }

    public String getPutanjaSlike() {
        return putanjaSlike;
    }

    public void setPutanjaSlike(String putanjaSlike) {
        this.putanjaSlike = putanjaSlike;
    }
}
