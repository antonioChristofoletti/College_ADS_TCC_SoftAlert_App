package softalertv3.softalertv3.softalert.View.ActPerfilUsuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.View.ActLocalizacoesUsuario.ActLocalizacoesUsuario;

public class ActPerfilUsuarioAdapter extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_perfil_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configuraComponentes();
    }

    //region METODOS

    public void configuraComponentes() {
        listView = (ListView) findViewById(R.id.content_act_perfil_usuario_listView);
        ActPerfilUsuario actPerfilUsuarioAdapter = new ActPerfilUsuario(this);

        listView.setAdapter(actPerfilUsuarioAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {chamaActivityActLocalizacoesUsuario(position);}
        });
    }


    //endregion

    //region EVENTOS

    public void chamaActivityActLocalizacoesUsuario(int position){

        if(position != 2)
            return;

        Intent intent = new Intent(ActPerfilUsuarioAdapter.this, ActLocalizacoesUsuario.class);
        startActivity(intent);
    }

    //endregion
}