package softalertv3.softalertv3.softalert.View;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Uteis.SpinnerControl;

public class ActAlertaCadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_alerta_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);

        ImageView iv = (ImageView) findViewById(R.id.ImageSpinnerEvento_content_act_alerta_cadastro);

        List<String> lista= new ArrayList<>();

        lista.add("Desastre Avistado");
        lista.add("Enchente");
        lista.add("Forte Ventania");
        lista.add("Queimada");
        lista.add("Tornado");

        String corSelecionada = "#" + Integer.toHexString(ContextCompat.getColor(ActAlertaCadastro.this, R.color.colorPrimary));
        String corNaoSelecionada = "#" + Integer.toHexString(ContextCompat.getColor(ActAlertaCadastro.this, R.color.colorBackgroundIconsNaoSelecionadosMaterialDesign));

        SpinnerControl spinnerControl = new SpinnerControl();
        spinnerControl.configurarSpinnerControl(true, spinner,iv, lista, corSelecionada, corNaoSelecionada);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.act_autenticacao_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.act_autenticacao_menu_title_cancelar: {
                finish();
                break;
            }

            case R.id.act_autenticacao_menu_title_salvar: {
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
