package vidovic.mvvm.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vidovic.mvvm.R;
import vidovic.mvvm.model.Motor;
import vidovic.mvvm.view.adapter.ListAdapterExample;
import vidovic.mvvm.view.adapter.MotorAdapter;
import vidovic.mvvm.view.adapter.MotorClickListener;
import vidovic.mvvm.viewmodel.MotorViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;


public class ReadFragment extends Fragment {

    @BindView(R.id.lista)
    ListView recyclerView;
    //RecyclerView recyclerView;

    MotorViewModel model;
    ListAdapterExample listAdapterExample = new ListAdapterExample();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,
                container, false);
        ButterKnife.bind(this,view);

        model = ((MainActivity)getActivity()).getModel();

        definirajListu();

        osvjeziPodatke();

        return view;
    }

    public void osvjeziPodatke(){
        model.dohvatiMotore().observe(getViewLifecycleOwner(), new Observer<List<Motor>>() {
            @Override
            public void onChanged(List<Motor> motori) {
                //((MotorAdapter)recyclerView.getAdapter()).setPodaci(motori);
                //recyclerView.getAdapter().notifyDataSetChanged();
                listAdapterExample.setMotori(motori);
                listAdapterExample.notifyDataSetChanged();
            }
        });
    }

    /*
    private void definirajListu() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MotorAdapter(new MotorClickListener() {
            @Override
            public void onItemClick(Motor motor) {
                model.setMotor(motor);
                ((MainActivity)getActivity()).cud();
            }
        }));
    }
    */

    private void definirajListu(){
        recyclerView.setAdapter(listAdapterExample);
    }

    @OnClick(R.id.fab)
    public void noviMotor(){
        model.setMotor(new Motor());
        ((MainActivity)getActivity()).cud();
    }

    @OnItemClick(R.id.lista)
    public void onClick(int pos){
        model.setMotor(listAdapterExample.getMotori().get(pos));
        ((MainActivity)getActivity()).cud();
    }



}