package softalertv3.softalertv3.softalert.View.ActLocalizacoesUsuario;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import softalertv3.softalertv3.R;

public class ActLocalizacoesUsuario extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_localizacoes_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.FrameLayout_content_act_localizacoes_usuario, new FragActLocalizacoesUsuario(), "FragmentLocalizacoesUsuario");

        transaction.commitAllowingStateLoss();
    }
}