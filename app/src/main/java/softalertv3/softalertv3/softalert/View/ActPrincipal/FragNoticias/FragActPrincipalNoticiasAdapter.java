package softalertv3.softalertv3.softalert.View.ActPrincipal.FragNoticias;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Model.Alerta;
import softalertv3.softalertv3.softalert.Uteis.Geral;

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

        TextView txtData = (TextView) convertView.findViewById(R.id.txtData_frag_act_principal_noticias_list_view);

        TextView txtStatus = (TextView) convertView.findViewById(R.id.txtStatus_frag_act_principal_noticias_list_view);

        TextView txtDescricao = (TextView) convertView.findViewById(R.id.txtDescricao_frag_act_principal_noticias_list_view);

        ImageView imageViewAlerta = (ImageView) convertView.findViewById(R.id.ImageViewAlerta_frag_act_principal_alertar_list_view);

        ImageView imageViewUsuarioVisualizou = (ImageView) convertView.findViewById(R.id.ImageViewUsuarioVisualizou_frag_act_principal_alertar_list_view);

        if(a.getListaAlertaPossuiUsuarios().get(0).getDataVisualizou() != null)
            imageViewUsuarioVisualizou.setImageResource(R.drawable.baseline_visibility_white_24);

        txtEvento.setText(a.getEvento());

        String status = "ATIVO";
        txtStatus.setTextColor(ContextCompat.getColor(activity, R.color.eventoValidado));

        if(a.getStatus().equals("Cancelado")) {
            status = "CANCELADO";
            txtStatus.setTextColor(ContextCompat.getColor(activity, R.color.eventoCancelado_NaoAprovado));
        }

        if(a.getStatus().equals("Desativado")) {
            status = "DESATIVADO";
            txtStatus.setTextColor(ContextCompat.getColor(activity, R.color.eventoAguardando));
        }

        txtStatus.setText(status);

        txtData.setText("Data: " + Geral.formataData("dd/MM/yyyy HH:mm", a.getDataInsercao()));



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