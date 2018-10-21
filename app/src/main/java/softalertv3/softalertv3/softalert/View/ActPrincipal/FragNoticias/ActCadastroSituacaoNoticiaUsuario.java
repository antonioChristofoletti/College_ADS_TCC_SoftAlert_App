package softalertv3.softalertv3.softalert.View.ActPrincipal.FragNoticias;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Controller.AlertaController;
import softalertv3.softalertv3.softalert.Controller.AlertaPossuiClienteController;
import softalertv3.softalertv3.softalert.Controller.UsuarioClienteController;
import softalertv3.softalertv3.softalert.DAOInterno.DAO.UsuarioClienteDAO;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.Alerta;
import softalertv3.softalertv3.softalert.Model.AlertaPossuiUsuario;
import softalertv3.softalertv3.softalert.Model.ErroValidacaoModel;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Uteis.Geral;
import softalertv3.softalertv3.softalert.Uteis.SpinnerControl;
import softalertv3.softalertv3.softalert.View.ActAlertaUsuario.ActAlertaCadastro;
import softalertv3.softalertv3.softalert.View.ActCadastroUsuario;
import softalertv3.softalertv3.softalert.View.ActPrincipal.ActPrincipal;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class ActCadastroSituacaoNoticiaUsuario extends AppCompatActivity implements InterfaceListenerAPI {

    private MaterialSpinner spinnerMinhaSituacao;

    private EditText txtComentario;

    private TextFieldBoxes txtBComentario;

    private Alerta alerta;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_situacao_noticia_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.alerta = (Alerta) this.getIntent().getSerializableExtra("alerta");

        configurarComponentes();

        if(this.alerta.getNivelAlerta().getId() == 1){
            finalizarAutomatico();
        }
    }

    public void configurarComponentes() {
        txtComentario = (EditText) findViewById(R.id.txtComentario_content_act_cadastro_situacao_noticia_usuario);
        txtBComentario = (TextFieldBoxes) findViewById(R.id.txtbComentario_content_act_cadastro_situacao_noticia_usuario);

        configurarSpinnerDesastreAvistado();
    }

    public void configurarSpinnerDesastreAvistado() {
        spinnerMinhaSituacao = (MaterialSpinner) findViewById(R.id.spinnerMinhaSituacao_content_act_cadastro_situacao_noticia_usuario);
        ImageView iv = (ImageView) findViewById(R.id.ImageSpinnerMinhaSituacao_content_act_cadastro_situacao_noticia_usuario);

        List<String> lista = new ArrayList<>();

        lista.add("* Minha Situação Atual");
        lista.add("Estou seguro");
        lista.add("Não estou seguro");
        lista.add("Não tenho certeza");

        String corSelecionada = "#" + Integer.toHexString(ContextCompat.getColor(ActCadastroSituacaoNoticiaUsuario.this, R.color.colorPrimary));
        String corNaoSelecionada = "#" + Integer.toHexString(ContextCompat.getColor(ActCadastroSituacaoNoticiaUsuario.this, R.color.colorBackgroundIconsNaoSelecionadosMaterialDesign));

        SpinnerControl spinnerControl = new SpinnerControl();
        spinnerControl.configurarSpinnerControl(true, spinnerMinhaSituacao, iv, lista, corSelecionada, corNaoSelecionada);
    }

    public void finalizar() {
        try {
            AlertaPossuiUsuario alertaPossuiUsuario = new AlertaPossuiUsuario();

            alertaPossuiUsuario.setIdAlerta(alerta.getId());

            alertaPossuiUsuario.setDataVisualizou(new Date());

            UsuarioCliente usuarioCliente = UsuarioClienteController.retornaUsuarioClienteDAOInterno();

            alertaPossuiUsuario.setIdUsuario(usuarioCliente.getId());

            alertaPossuiUsuario.setSituacaoUsuario(spinnerMinhaSituacao.getItems().get(spinnerMinhaSituacao.getSelectedIndex()).toString());

            ErroValidacaoModel erroValidacaoModel = AlertaPossuiClienteController.validarCampos(alertaPossuiUsuario);

            if (erroValidacaoModel != null) {
                processaErroValidacao(erroValidacaoModel);
                return;
            }

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Finalizando a atualização. Por favor aguarde...");
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();

            AlertaController.atualizarVinculoAlertaPossuiUsuario(alertaPossuiUsuario, this);

        } catch (Exception ex) {
            Geral.chamarAlertDialog(this, "Erro", ex.getMessage());
        }
    }

    public void finalizarAutomatico() {
        try {
            AlertaPossuiUsuario alertaPossuiUsuario = new AlertaPossuiUsuario();

            alertaPossuiUsuario.setIdAlerta(alerta.getId());

            alertaPossuiUsuario.setDataVisualizou(new Date());

            UsuarioCliente usuarioCliente = UsuarioClienteController.retornaUsuarioClienteDAOInterno();

            alertaPossuiUsuario.setIdUsuario(usuarioCliente.getId());

            alertaPossuiUsuario.setSituacaoUsuario("Estou seguro");

            ErroValidacaoModel erroValidacaoModel = AlertaPossuiClienteController.validarCampos(alertaPossuiUsuario);

            if (erroValidacaoModel != null) {
                processaErroValidacao(erroValidacaoModel);
                return;
            }

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Finalizando a atualização. Por favor aguarde...");
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();

            Thread.sleep(3000);

            AlertaController.atualizarVinculoAlertaPossuiUsuario(alertaPossuiUsuario, this);

        } catch (Exception ex) {
            Geral.chamarAlertDialog(this, "Erro", ex.getMessage());
        }
    }

    public void processaErroValidacao(ErroValidacaoModel erroValidacaoModel) {
        if (erroValidacaoModel.getCampoErro().equals("situacaoUsuario")) {
            Toast.makeText(this, erroValidacaoModel.getDescricaoErro(), Toast.LENGTH_LONG).show();
        }
    }

    //region EVENTOS MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.act_autenticacao_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.act_autenticacao_menu_title_cancelar: {
                finish();
                break;
            }

            case R.id.act_autenticacao_menu_title_salvar: {
                finalizar();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region EVENTOS API

    @Override
    public void retornaMensagemSucesso(String mensagem) {
        progressDialog.cancel();
        try {
            finish();
        } catch (Exception ex) {
            Geral.chamarAlertDialog(this, "Erro", "Erro ao atualizar as informações. Erro: " + ex.getMessage());
        }
    }

    @Override
    public void retornaMensagemErro(String mensagem) {
        progressDialog.cancel();
        Geral.chamarAlertDialog(this, "Erro", mensagem);
    }

    //endregion
}