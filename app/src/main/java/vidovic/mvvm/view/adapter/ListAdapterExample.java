package vidovic.mvvm.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import vidovic.mvvm.R;
import vidovic.mvvm.model.Motor;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterExample extends BaseAdapter {
    private List<Motor> motori;

    public ListAdapterExample() {
        motori = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return motori.size();
    }

    @Override
    public Object getItem(int position) {
        return motori.get(position);
    }

    @Override
    public long getItemId(int position) {
        return motori.get(position).getId();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_red_liste, parent, false);
        }
        Motor motor = motori.get(position);
        ((TextView) convertView.findViewById(R.id.proizvodjacModel)).setText(motor.getProizvodjac() + " " +  motor.getModel());
        if(motor.getPutanjaSlike()==null){
            Picasso.get().load(R.drawable.nepoznato).fit().centerCrop().into((ImageView) convertView.findViewById(R.id.slika));
        }else{
            Log.d("Slika", motor.getPutanjaSlike());
            Picasso.get().load( motor.getPutanjaSlike()).fit().centerCrop().into((ImageView) convertView.findViewById(R.id.slika));
        }
        return convertView;
    }

    public void setMotori(List<Motor> motori) {
        this.motori = motori;
    }

    public List<Motor> getMotori() {
        return motori;
    }
}
