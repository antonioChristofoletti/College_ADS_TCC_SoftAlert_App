package softalertv3.softalertv3.softalert.View.ActPrincipal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import softalertv3.softalertv3.ActDetalhesInformacoesNoticias;
import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Controller.AlertaManagerNotification;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAlertaManagerNotification;
import softalertv3.softalertv3.softalert.Model.Alerta;

public class FragActPrincipalNoticias extends Fragment implements InterfaceListenerAlertaManagerNotification{

    private View view;
    private ListView listViewNoticias;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_act_principal_noticias, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = getView();

        listViewNoticias = (ListView) view.findViewById(R.id.frag_act_principa_noticiar_listView);

        listViewNoticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ActDetalhesInformacoesNoticias.class);
                startActivity(intent);
            }
        });

        iniciarServicoNotificacoes(view.getContext());
    }


    public void iniciarServicoNotificacoes(final Context context) {
        new Thread(new Runnable() {
            public void run() {
                AlertaManagerNotification alertaManagerNotification = new AlertaManagerNotification(context, FragActPrincipalNoticias.this);
                alertaManagerNotification.iniciarGerenciamentoNotificacoes();
            }
        }).start();
    }

    //region


    @Override
    public void atualizarListagemAlerta(ArrayList<Alerta> listaAlerta) {

        if (listaAlerta == null)
            return;

        FragActPrincipalNoticiasAdapter adapter = new FragActPrincipalNoticiasAdapter(((ActPrincipal) getActivity()), this, listaAlerta);

        listViewNoticias.setAdapter(adapter);
    }

    //endregion
}
