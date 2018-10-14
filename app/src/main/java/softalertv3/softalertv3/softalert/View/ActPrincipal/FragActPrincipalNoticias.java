package softalertv3.softalertv3.softalert.View.ActPrincipal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Controller.AlertaManagerNotification;

public class FragActPrincipalNoticias extends Fragment {

    private Context context;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_act_principal_noticias, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = getView();

        Button button = view.findViewById(R.id.sendMessage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        AlertaManagerNotification alertaManagerNotification = new AlertaManagerNotification(context);
                        alertaManagerNotification.iniciarGerenciamentoNotificacoes();
                    }
                }).start();
            }
        });
    }
}
