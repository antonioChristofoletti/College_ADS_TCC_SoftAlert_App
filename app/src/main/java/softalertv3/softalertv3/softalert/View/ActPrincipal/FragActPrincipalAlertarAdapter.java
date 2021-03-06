package softalertv3.softalertv3.softalert.View.ActPrincipal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import softalertv3.softalertv3.softalert.Model.AlertaUsuarioCliente;
import softalertv3.softalertv3.softalert.Uteis.Geral;
import softalertv3.softalertv3.softalert.View.ActAlertaUsuario.ActAlertaCadastro;

public class FragActPrincipalAlertarAdapter extends BaseAdapter implements InterfaceListenerAPI{

    ArrayList<AlertaUsuarioCliente> lista;
    AppCompatActivity activity;
    FragActPrincipalAlertar fragActPrincipalAlertar;

    //region METODOS DEFAULT

    public FragActPrincipalAlertarAdapter(AppCompatActivity activity, FragActPrincipalAlertar fragActPrincipalAlertar, ArrayList<AlertaUsuarioCliente> lista) {
        this.activity = activity;
        this.fragActPrincipalAlertar = fragActPrincipalAlertar;
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

        convertView = this.activity.getLayoutInflater().inflate(R.layout.frag_act_principal_alertar_list_view, null);

        TextView txtEvento = (TextView) convertView.findViewById(R.id.txtEvento_DataItemLista_content_act_perfil_usuario_layout_list_view);
        TextView txtAprovado = (TextView) convertView.findViewById(R.id.txtAprovadoLista_content_act_perfil_usuario_layout_list_view);

        final AlertaUsuarioCliente auc = lista.get(position);

        String evento = auc.getDesastreAvistado() + " - " + Geral.formataData("dd/MM/yyyy HH:mm", auc.getDataInsercao());
        txtEvento.setText(evento);


        String aprovado = "";
        if (!auc.getStatus().equals("Cancelado")) {
            if (auc.getVeracidade() == null || auc.getVeracidade().equals("")) {
                aprovado = "NÃO APROVADO ATÉ O MOMENTO";
                txtAprovado.setTextColor(ContextCompat.getColor(activity, R.color.eventoAguardando));
            }
            else {
                if (auc.getVeracidade().equals("S")) {
                    aprovado = "EVENTO VALIDADO";
                    txtAprovado.setTextColor(ContextCompat.getColor(activity, R.color.eventoValidado));
                }
                    else {
                    aprovado = "EVENTO REPROVADO";
                    txtAprovado.setTextColor(ContextCompat.getColor(activity, R.color.eventoCancelado_NaoAprovado));
                }
            }
        } else {
            aprovado = "EVENTO CANCELADO";
            txtAprovado.setTextColor(ContextCompat.getColor(activity, R.color.eventoCancelado_NaoAprovado));
        }
        txtAprovado.setText(aprovado);

        configuraMenu(convertView, auc);

        return convertView;
    }

    //endregion

    //region METODOS

    public void configuraMenu(View convertView, final AlertaUsuarioCliente auc) {
        final ImageView iv = (ImageView) convertView.findViewById(R.id.ImageView_frag_act_principal_alertar_list_view);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(activity, iv);
                popupMenu.getMenuInflater().inflate(R.menu.frag_act_principal_alertar_list_view_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.frag_act_principa_alertar_listView_menu_title_cancelar: {
                                if (auc.getVeracidade() != null) {
                                    Geral.chamarAlertDialog(activity, "Aviso", "O Alerta já foi análisado. Não é possível cancelar.");
                                    return false;
                                }

                                if (auc.getStatus().equals("Cancelado")) {
                                    Geral.chamarAlertDialog(activity, "Aviso", "O Alerta já foi cancelado.");
                                    return false;
                                }

                                cancelarAlerta(activity, FragActPrincipalAlertarAdapter.this, auc);
                                break;
                            }

                            case R.id.frag_act_principa_alertar_listView_menu_title_ver_detalhes:  {

                                Intent intent = new Intent(activity, ActAlertaCadastro.class);
                                intent.putExtra("visualizacaoAlertaApenas","true");
                                intent.putExtra("alertaUsuarioCliente",auc);

                                activity.startActivity(intent);
                                break;
                            }
                        }

                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }

    public void cancelarAlerta(final AppCompatActivity activity, final InterfaceListenerAPI interfaceListenerAPI, final AlertaUsuarioCliente auc){

        auc.setStatus("Cancelado");

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    AlertaUsuarioClienteController.editarAPI(auc, interfaceListenerAPI);
                } catch (Exception e) {
                    Geral.chamarAlertDialog(activity, "Erro", "Erro ao editar. Erro: " + e.getMessage());
                }
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        Geral.chamarAlertDialog(builder, "", "Deseja realmente cancelar?");
    }

    //enderegion

    //region EVENTOS API

    @Override
    public void retornaMensagemSucesso(String mensagem) {
        fragActPrincipalAlertar.atualizarListViewAlertasUsuario();
    }

    @Override
    public void retornaMensagemErro(String mensagem) {
        Geral.chamarAlertDialog(activity, "Erro", mensagem);
    }

    //endregion
}