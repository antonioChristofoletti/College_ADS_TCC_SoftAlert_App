package softalertv3.softalertv3.softalert.View.ActPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.View.ActAlertaCadastro;

public class FragActPrincipalAlertar extends Fragment {

    View view;
    FloatingActionMenu floatingActionMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { return inflater.inflate(R.layout.frag_act_principal_alertar, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view = getView();
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.FloatingActionMenuAdicionar_content_act_principal_alertar);

        floatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() {@Override public void onClick(View v) { FloatingActionMenuAdicionarNoticias_Inserir_content_act_localizacoes_usuario_onClick(); }
        });
    }

    public void FloatingActionMenuAdicionarNoticias_Inserir_content_act_localizacoes_usuario_onClick() {
        Intent intent = new Intent(this.getContext(), ActAlertaCadastro.class);
        startActivity(intent);
    }
}
