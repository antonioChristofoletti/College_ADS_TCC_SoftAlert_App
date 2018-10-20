package softalertv3.softalertv3.softalert.View.ActPrincipal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Controller.AlertaUsuarioClienteController;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.Alerta;
import softalertv3.softalertv3.softalert.Model.AlertaUsuarioCliente;
import softalertv3.softalertv3.softalert.Uteis.Geral;
import softalertv3.softalertv3.softalert.View.ActAlertaUsuario.ActAlertaCadastro;

public class FragActPrincipalNoticiasAdapter extends BaseAdapter{

    ArrayList<Alerta> lista;
    AppCompatActivity activity;
    FragActPrincipalNoticias fragActPrincipalNoticias;

    //region METODOS DEFAULT

    public FragActPrincipalNoticiasAdapter(AppCompatActivity activity, FragActPrincipalNoticias fragActPrincipalNoticias, ArrayList<Alerta> lista) {
        this.activity = activity;
        this.fragActPrincipalNoticias = fragActPrincipalNoticias;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Alerta a = lista.get(position);

        convertView = this.activity.getLayoutInflater().inflate(R.layout.frag_act_principal_noticias_list_view, null);

        TextView txtEvento = (TextView) convertView.findViewById(R.id.txtEvento_frag_act_principal_noticias_list_view);

        TextView txtData_mais_status = (TextView) convertView.findViewById(R.id.txtData_mais_Status_frag_act_principal_noticias_list_view);

        TextView txtDescricao = (TextView) convertView.findViewById(R.id.txtDescricao_frag_act_principal_noticias_list_view);

        ImageView imageViewOpcoes = (ImageView) convertView.findViewById(R.id.ImageViewOpcoes_frag_act_principal_alertar_list_view);

        ImageView imageViewAlerta = (ImageView) convertView.findViewById(R.id.ImageViewAlerta_frag_act_principal_alertar_list_view);

        txtEvento.setText(a.getEvento());

        String status = "Ativo";
        if(a.getStatus().equals("C")) {
            status = "Cancelado";
            txtData_mais_status.setTextColor(Color.RED);
        }

        txtData_mais_status.setText(Geral.formataData("dd/MM/yyyy HH:mm", a.getDataInsercao()) + " - " + status);

        txtDescricao.setText(a.getDescricao());

        if(a.getNivelAlerta().getId() == 1)
            imageViewAlerta.setImageResource(R.drawable.alert_circle_green_96dp);

        if(a.getNivelAlerta().getId() == 2)
            imageViewAlerta.setImageResource(R.drawable.alert_circle_yellow_96dp);

        if(a.getNivelAlerta().getId() == 3)
            imageViewAlerta.setImageResource(R.drawable.alert_circle_orange_96dp);

        if(a.getNivelAlerta().getId() == 4)
            imageViewAlerta.setImageResource(R.drawable.alert_circle_red_96dp);

        return convertView;
    }

    //endregion
}