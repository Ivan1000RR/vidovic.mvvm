package vidovic.mvvm.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import vidovic.mvvm.R;
import vidovic.mvvm.model.Motor;
import vidovic.mvvm.viewmodel.MotorViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CUDFragment extends Fragment {

    static final int SLIKANJE = 1;

    @BindView(R.id.proizvodjac)
    EditText proizvodjac;
    @BindView(R.id.model)
    EditText model;
    @BindView(R.id.snaga)
    EditText snaga;
    @BindView(R.id.zapremnina)
    EditText zapremnina;
    @BindView(R.id.slikaCUD)
    ImageView slikaCUD;

    @BindView(R.id.noviMotor)
    Button noviMotor;
    @BindView(R.id.uslikajMotor)
    Button uslikaj;
    @BindView(R.id.promjeniMotor)
    Button promjeniMotor;
    @BindView(R.id.obrisiMotor)
    Button obrisiMotor;

    MotorViewModel motorViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cud,
                container, false);
        ButterKnife.bind(this,view);
        definirajKomponente();

        if (motorViewModel.getMotor().getId() == 0) {
            definirajNoviMotor();
            return view;
        }

        definirajPromjenaBrisanjeMotora();

        return view;
    }

    private void definirajKomponente() {
        motorViewModel = ((MainActivity)getActivity()).getModel();
        Picasso picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);

        picasso.load(R.drawable.nepoznato).fit().centerCrop().into(slikaCUD);
    }

    private void definirajNoviMotor() {
        promjeniMotor.setVisibility(View.GONE);
        obrisiMotor.setVisibility(View.GONE);
        uslikaj.setVisibility(View.GONE);
        noviMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noviMotor();
            }
        });
    }




    private void noviMotor(){
        motorViewModel.getMotor().setProizvodjac(proizvodjac.getText().toString());
        motorViewModel.getMotor().setModel(model.getText().toString());
        motorViewModel.getMotor().setSnaga(Integer.parseInt(String.valueOf(snaga.getText())));
        motorViewModel.getMotor().setZapremninaMotora(Integer.parseInt(String.valueOf(zapremnina.getText())));
        motorViewModel.dodajNoviMotor();
        nazad();
    }


    private void definirajPromjenaBrisanjeMotora() {
        Motor m = motorViewModel.getMotor();
        noviMotor.setVisibility(View.GONE);
        proizvodjac.setText(m.getProizvodjac());
        model.setText(m.getModel());
        snaga.setText(String.valueOf(m.getSnaga()));
        zapremnina.setText(String.valueOf(m.getZapremninaMotora()));
        Log.d("Putanja slika", "->" + m.getPutanjaSlike());
        if(m.getPutanjaSlike()!=null){
            Picasso.get().load(m.getPutanjaSlike()).fit().centerCrop().into(slikaCUD);
        }

    }

    @OnClick(R.id.uslikajMotor)
    public void uslikajMotor() {
        Intent uslikajIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(uslikajIntent.resolveActivity(getActivity().getPackageManager())==null){
            //poruke korisniku
            Log.d("Gumb uslikaj","uslikajIntent.resolveActivity(getActivity().getPackageManager())==null");
            return;
        }

        File slika= null;
        try{
            slika = kreirajDatotekuSlike();
        }catch (IOException e){
            //poruke korisniku
            return;
        }

        if(slika==null){
            //poruke korisniku
            return;
        }

        Uri slikaUri = FileProvider.getUriForFile(getActivity(),
                "vidovic.mvvm.provider",
                slika);
        uslikajIntent.putExtra(MediaStore.EXTRA_OUTPUT,slikaUri);
        startActivityForResult(uslikajIntent,SLIKANJE);

    }

    private File kreirajDatotekuSlike() throws IOException {
        String naziv = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_motor";
        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File datoteka = File.createTempFile(naziv,".jpg",dir);
        motorViewModel.getMotor().setPutanjaSlike("file:" + datoteka.getAbsolutePath());
        return datoteka;
    }

    @OnClick(R.id.promjeniMotor)
    public void promjeniMotor(){
        motorViewModel.getMotor().setProizvodjac(proizvodjac.getText().toString());
        motorViewModel.getMotor().setModel(model.getText().toString());
        motorViewModel.getMotor().setSnaga(Integer.parseInt(snaga.getText().toString()));
        motorViewModel.getMotor().setZapremninaMotora(Integer.parseInt(zapremnina.getText().toString()));
        motorViewModel.promjeniMotor();
        nazad();
    }


    @OnClick(R.id.obrisiMotor)
    public void obrisiMotor(){
        motorViewModel.obrisiMotor();
        nazad();
    }

    @OnClick(R.id.nazad)
    public void nazad(){
        ((MainActivity)getActivity()).read();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SLIKANJE && resultCode == Activity.RESULT_OK){
            motorViewModel.promjeniMotor();
            Picasso.get().load(motorViewModel.getMotor().getPutanjaSlike()).fit().centerCrop().into(slikaCUD);
        }
    }
}