package softalertv3.softalertv3.softalert.View.ActPerfilUsuario;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import softalertv3.softalertv3.R;

public class ActPerfilUsuarioAdapter extends BaseAdapter {

    String[] titulos = {"Minhas Informações", "Meu Número", "Minhas Localizações"};

    int[] imagens = {R.drawable.ic_account_circle_grey600_24dp,R.drawable.ic_cellphone_android_grey600_24dp,R.drawable.ic_google_maps_grey600_24dp};

    AppCompatActivity activity;

    public ActPerfilUsuarioAdapter(AppCompatActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return titulos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = this.activity.getLayoutInflater().inflate(R.layout.content_act_perfil_usuario_layout_list_view, null);

        ImageView imagemDescricao = (ImageView) convertView.findViewById(R.id.ImgItemLista_content_act_perfil_usuario_layout_list_view);
        TextView txtDescricaoItemLista = (TextView) convertView.findViewById(R.id.txtDescricaoItemLista_content_act_perfil_usuario_layout_list_view);

        imagemDescricao.setImageResource(imagens[position]);
        txtDescricaoItemLista.setText(titulos[position]);

        return convertView;
    }
}
