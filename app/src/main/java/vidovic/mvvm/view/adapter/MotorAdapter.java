package vidovic.mvvm.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import vidovic.mvvm.R;
import vidovic.mvvm.model.Motor;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MotorAdapter extends RecyclerView.Adapter<MotorAdapter.Red> {

    private List<Motor> podaci;
    private MotorClickListener motorClickListener;

    public MotorAdapter(MotorClickListener motorClickListener) {
        this.motorClickListener=motorClickListener;
    }

    @Override
    public Red onCreateViewHolder(ViewGroup roditelj, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(roditelj.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_red_liste, roditelj, false);
        return new Red(view);
    }

    @Override
    public void onBindViewHolder(Red red, int position) {
        Motor m = podaci.get(position);
        red.proizvodjacModel.setText(m.getProizvodjac() + " " + m.getModel());

        red.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                motorClickListener.onItemClick(m); //build.gradle compiler options VERSION_1_8
            }
        });
        if(m.getPutanjaSlike()==null){
            Picasso.get().load(R.drawable.nepoznato).fit().centerCrop().into(red.slika);
        }else{
            Log.d("Slika", m.getPutanjaSlike());
            Picasso.get().load( m.getPutanjaSlike()).fit().centerCrop().into(red.slika);
        }

    }

    @Override
    public int getItemCount() {
        return podaci==null ? 0 : podaci.size();
    }

    public void setPodaci(List<Motor> motori) {
        this.podaci = motori;
    }



    public class Red extends RecyclerView.ViewHolder{
        private ImageView slika;
        private TextView proizvodjacModel;
        Red(View itemView) {
            super(itemView);
            slika=itemView.findViewById(R.id.slika);
            proizvodjacModel = itemView.findViewById(R.id.proizvodjacModel);
        }
    }



}
