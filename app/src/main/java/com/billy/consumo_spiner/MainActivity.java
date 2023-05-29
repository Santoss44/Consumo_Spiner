package com.billy.consumo_spiner;

import static java.lang.Character.getName;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.billy.consumo_spiner.entity.UserCharla;
import com.billy.consumo_spiner.service.UserService;
import com.billy.consumo_spiner.util.Connection;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Spinner spnProfesionales;
    ArrayAdapter<String> adapter;
    ArrayList<String> profesionales = new ArrayList<String>();

    Button button;

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnProfesionales = findViewById(R.id.spnProfesionales);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, profesionales);
        spnProfesionales.setAdapter(adapter);

        button = findViewById(R.id.button);

        userService = Connection.getConnecion().create(UserService.class);
        cargaData();
    }

    public void cargaData(){
        Call<List<UserCharla>> call = userService.listaProfesionales();
        call.enqueue(new Callback<List<UserCharla>>() {
            @Override
            public void onResponse(Call<List<UserCharla>> call, Response<List<UserCharla>> response) {
                mensajeToast("Acceso exitoso al servicio REST");
                if (response.isSuccessful()){
                    mensajeToast("Acceso exitoso al servicio REST");
                    List<UserCharla> lstProfesionales = response.body();
                    for (UserCharla userCharla:lstProfesionales){
                        String nombreCompleto = userCharla.getNombres() + " " + userCharla.getApellidos() + " - " + userCharla.getProfesion();
                        profesionales.add(nombreCompleto);
                    }
                    adapter.notifyDataSetChanged();

                }else{
                    mensajeToast("Error de acceso al servicio REST");
                }
            }

            @Override
            public void onFailure(Call<List<UserCharla>> call, Throwable t) {
                mensajeToast("Error de acceso al servicio REST");
            }
        });
    }

    public void mensajeAlert(String titulo, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setTitle(titulo);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    void mensajeToast(String mensaje){
        Toast toast1 = Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

}