package softalertv3.softalertv3.softalert.View.ActPrincipal.FragNoticias;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Model.Alerta;
import softalertv3.softalertv3.softalert.Uteis.Geral;

public class ActDetalhesInformacoesNoticias extends AppCompatActivity {

    private Alerta alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detalhes_informacoes_noticias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.alerta = (Alerta) this.getIntent().getSerializableExtra("alerta");

        configurarComponentes(alerta);
    }

    public void configurarComponentes(Alerta alerta){

        TextView txtEvento = (TextView) findViewById(R.id.txtEvento_content_act_detalhes_informacoes_noticias);

        Button btnLocaisEvento = (Button) findViewById(R.id.btnLocaisEventos_content_act_detalhes_informacoes_noticias);

        ImageView imageViewIconeAlerta = (ImageView) findViewById(R.id.imageViewAlerta_content_act_detalhes_informacoes_noticias);

        TextView txtStatusAlerta = (TextView) findViewById(R.id.txtStatus_content_act_detalhes_informacoes_noticias);

        TextView txtDataEvento = (TextView) findViewById(R.id.txtData_content_act_detalhes_informacoes_noticias);

        TextView txtSituacaoAtual = (TextView) findViewById(R.id.txtSituacaoAtual_content_act_detalhes_informacoes_noticias);

        TextView txtInformacoes = (TextView) findViewById(R.id.txtInformacoes_content_act_detalhes_informacoes_noticias);

        txtInformacoes.setText(alerta.getDescricao());

        txtEvento.setText(alerta.getEvento());

        txtStatusAlerta.setText(alerta.getNivelAlerta().getDescricao());



        String status = "ATIVO";
        txtSituacaoAtual.setTextColor(ContextCompat.getColor(this, R.color.eventoValidado));

        if(alerta.getStatus().equals("Cancelado")) {
            status = "CANCELADO";
            txtSituacaoAtual.setTextColor(ContextCompat.getColor(this, R.color.eventoCancelado_NaoAprovado));
        }

        if(alerta.getStatus().equals("Desativado")) {
            status = "DESATIVADO";
            txtSituacaoAtual.setTextColor(ContextCompat.getColor(this, R.color.eventoAguardando));
        }

        txtSituacaoAtual.setText("STATUS: "+ status + ".");

        txtDataEvento.setText("Data Evento: " + Geral.formataData("dd/MM/yyyy HH:mm", alerta.getDataInsercao()) + ".");

        if(alerta.getNivelAlerta().getId() == 1)
            imageViewIconeAlerta.setImageResource(R.drawable.alert_circle_green_96dp);

        if(alerta.getNivelAlerta().getId() == 2)
            imageViewIconeAlerta.setImageResource(R.drawable.alert_circle_yellow_96dp);

        if(alerta.getNivelAlerta().getId() == 3)
            imageViewIconeAlerta.setImageResource(R.drawable.alert_circle_orange_96dp);

        if(alerta.getNivelAlerta().getId() == 4)
            imageViewIconeAlerta.setImageResource(R.drawable.alert_circle_red_96dp);
    }

    //region EVENTOS MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (alerta.getStatus().equals("Ativo") && alerta.getListaAlertaPossuiUsuarios().get(0).getDataVisualizou() == null) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.act_detalhes_informacoes_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(ActDetalhesInformacoesNoticias.this, ActCadastroSituacaoNoticiaUsuario.class);

        intent.putExtra("alerta", alerta);

        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    //endregion

    public void btnLocaisEventoOnClick(View view) {
        Intent intent = new Intent(this, ActDetalhesInformacoesNoticiasLocalizacoes.class);
        startActivity(intent);
    }
}