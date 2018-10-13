package softalertv3.softalertv3.softalert.View.ActPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Controller.AlertaUsuarioClienteController;
import softalertv3.softalertv3.softalert.Controller.UsuarioClienteController;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.AlertaUsuarioCliente;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.View.ActAlertaUsuario.ActAlertaCadastro;

public class FragActPrincipalAlertar extends Fragment implements InterfaceListenerAPI{

    private View view;
    private FloatingActionMenu floatingActionMenu;
    private ListView listViewAlertas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_act_principal_alertar, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view = getView();
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.FloatingActionMenuAdicionar_content_act_principal_alertar);

        floatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { FloatingActionMenuAdicionarNoticias_Inserir_content_act_localizacoes_usuario_onClick(); }
        });

        listViewAlertas = (ListView) view.findViewById(R.id.frag_act_principa_alertar_listView);
        UsuarioCliente uc = UsuarioClienteController.retornaUsuarioClienteDAOInterno();
        AlertaUsuarioClienteController.retornaAlertas(uc,this);
    }

    @Override
    public void onResume() {
        super.onResume();

        UsuarioCliente uc = UsuarioClienteController.retornaUsuarioClienteDAOInterno();
        AlertaUsuarioClienteController.retornaAlertas(uc,this);
    }

    public void FloatingActionMenuAdicionarNoticias_Inserir_content_act_localizacoes_usuario_onClick() {
        Intent intent = new Intent(this.getContext(), ActAlertaCadastro.class);
        startActivity(intent);
    }

    //region EVENTOS API

    @Override
    public void retornaMensagemSucesso(String mensagem) {
        ArrayList<AlertaUsuarioCliente> lista = AlertaUsuarioClienteController.retornaListaAlerta();

        if (lista == null)
            return;

        FragActPrincipalAlertarAdapter adapter = new FragActPrincipalAlertarAdapter(((ActPrincipal)getActivity()), lista);

        listViewAlertas.setAdapter(adapter);
    }

    @Override
    public void retornaMensagemErro(String mensagem) {
    }

    //endregion
}
