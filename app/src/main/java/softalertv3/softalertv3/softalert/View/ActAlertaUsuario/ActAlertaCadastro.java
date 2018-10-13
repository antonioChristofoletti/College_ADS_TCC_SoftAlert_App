package softalertv3.softalertv3.softalert.View.ActAlertaUsuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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
import java.util.List;
import java.util.Locale;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Controller.AlertaUsuarioClienteController;
import softalertv3.softalertv3.softalert.Controller.UsuarioClienteController;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.AlertaUsuarioCliente;
import softalertv3.softalertv3.softalert.Model.Endereco;
import softalertv3.softalertv3.softalert.Model.ErroValidacaoModel;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Uteis.Geral;
import softalertv3.softalertv3.softalert.Uteis.SpinnerControl;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class ActAlertaCadastro extends AppCompatActivity implements InterfaceListenerAPI {

    private MaterialSpinner spinnerDesastreAvistado;
    private MaterialSpinner spinnerMinhaSituacao;

    private EditText txtDescricao;
    private EditText txtLocal;
    private EditText txtDescricaoVeracidade;

    private TextFieldBoxes txtBDescricao;
    private TextFieldBoxes txtBLocal;
    private TextFieldBoxes txtBDescricaoVeracidade;

    private Endereco enderecoSelecionado;

    private ProgressDialog progressDialog;

    private Boolean visualizacaoAlertaApenas;
    private AlertaUsuarioCliente alertaUsuarioCliente;

    private Boolean primeiroFocoLocalDOEvento = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_alerta_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configurarComponentes();

        configurarComponentesVisualizarApenas();

    }

    //region METODOS

    public void configurarComponentes() {
        configurarSpinnerDesastreAvistado();

        configurarSpinnerMinhaSituacao();

        txtDescricao = (EditText) findViewById(R.id.txtDescricao_content_act_alerta_cadastro);
        txtBDescricao = (TextFieldBoxes) findViewById(R.id.txtbDescricao_content_act_alerta_cadastro);

        txtDescricaoVeracidade = (EditText) findViewById(R.id.txtDescricaoVeracidade_content_act_alerta_cadastro);
        txtBDescricaoVeracidade = (TextFieldBoxes) findViewById(R.id.txtbDescricaoVeracidade_content_act_alerta_cadastro);

        txtDescricaoVeracidade.setEnabled(false);
        txtBDescricaoVeracidade.setEnabled(false);

        txtLocal = (EditText) findViewById(R.id.txtLocal_content_act_alerta_cadastro);
        txtBLocal = (TextFieldBoxes) findViewById(R.id.txtbLocal_content_act_alerta_cadastro);

        txtBLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaActivityPesquisarEndereco();
            }
        });

        txtBLocal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    chamaActivityPesquisarEndereco();
            }
        });

        txtLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaActivityPesquisarEndereco();
            }
        });

        txtLocal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    chamaActivityPesquisarEndereco();
            }
        });
    }

    public void configurarComponentesVisualizarApenas() {

        visualizacaoAlertaApenas = false;

        String visualizacaoAlertaApenasAux = getIntent().getStringExtra("visualizacaoAlertaApenas");

        if (visualizacaoAlertaApenasAux != null)
            visualizacaoAlertaApenas = Boolean.parseBoolean(visualizacaoAlertaApenasAux);
        else
            visualizacaoAlertaApenas = false;

        if (visualizacaoAlertaApenas) {
            spinnerMinhaSituacao.setEnabled(false);
            spinnerDesastreAvistado.setEnabled(false);

            txtBDescricao.setEnabled(false);
            txtDescricao.setEnabled(false);

            this.setTitle("Detalhes Alerta");

            alertaUsuarioCliente = (AlertaUsuarioCliente) getIntent().getSerializableExtra("alertaUsuarioCliente");

            int i = 0;
            for (Object item : spinnerDesastreAvistado.getItems()) {
                if (item.equals(alertaUsuarioCliente.getDesastreAvistado()))
                    spinnerDesastreAvistado.setSelectedIndex(i);
                i++;
            }

            i = 0;
            for (Object item : spinnerMinhaSituacao.getItems()) {
                if (item.equals(alertaUsuarioCliente.getSituacaoUsuario()))
                    spinnerMinhaSituacao.setSelectedIndex(i);
                i++;
            }

            txtDescricao.setText(alertaUsuarioCliente.getDescricao());

            if (alertaUsuarioCliente.getVeracidade() != null) {
                txtDescricaoVeracidade.setText(alertaUsuarioCliente.getDescricaoVeracidade());
            }

            encontrarEndereco(alertaUsuarioCliente.getLatitude(), alertaUsuarioCliente.getLongitude());

            txtLocal.setText(enderecoSelecionado.getEndereco());
            ActLocalizacaoAlerta.enderecoSelecionado = enderecoSelecionado;
        } else {
            txtDescricaoVeracidade.setVisibility(View.INVISIBLE);
            txtBDescricaoVeracidade.setVisibility(View.INVISIBLE);
        }
    }

    public void configurarSpinnerDesastreAvistado() {
        spinnerDesastreAvistado = (MaterialSpinner) findViewById(R.id.spinnerDesastreAvistado_content_act_alerta_cadastro);
        ImageView iv = (ImageView) findViewById(R.id.ImageSpinnerDesastreAvistado_content_act_alerta_cadastro);
        List<String> lista = new ArrayList<>();

        lista.add("* Desastre Avistado");
        lista.add("Enchente");
        lista.add("Forte Ventania");
        lista.add("Queimada");
        lista.add("Produtos químicos");

        String corSelecionada = "#" + Integer.toHexString(ContextCompat.getColor(ActAlertaCadastro.this, R.color.colorPrimary));
        String corNaoSelecionada = "#" + Integer.toHexString(ContextCompat.getColor(ActAlertaCadastro.this, R.color.colorBackgroundIconsNaoSelecionadosMaterialDesign));

        SpinnerControl spinnerControl = new SpinnerControl();
        spinnerControl.configurarSpinnerControl(true, spinnerDesastreAvistado, iv, lista, corSelecionada, corNaoSelecionada);
    }

    public void configurarSpinnerMinhaSituacao() {
        spinnerMinhaSituacao = (MaterialSpinner) findViewById(R.id.spinnerMinhaSituacao_content_act_alerta_cadastro);
        ImageView iv = (ImageView) findViewById(R.id.ImageSpinnerMinhaSituacao_content_act_alerta_cadastro);
        List<String> lista = new ArrayList<>();

        lista.add("* Minha Situação Atual");
        lista.add("Estou seguro");
        lista.add("Não estou seguro");
        lista.add("Não tenho certeza");

        String corSelecionada = "#" + Integer.toHexString(ContextCompat.getColor(ActAlertaCadastro.this, R.color.colorPrimary));
        String corNaoSelecionada = "#" + Integer.toHexString(ContextCompat.getColor(ActAlertaCadastro.this, R.color.colorBackgroundIconsNaoSelecionadosMaterialDesign));

        SpinnerControl spinnerControl = new SpinnerControl();
        spinnerControl.configurarSpinnerControl(true, spinnerMinhaSituacao, iv, lista, corSelecionada, corNaoSelecionada);
    }

    public void chamaActivityPesquisarEndereco() {

        if (!primeiroFocoLocalDOEvento && visualizacaoAlertaApenas) {
            primeiroFocoLocalDOEvento = true;
            return;
        }

        Intent intent = new Intent(ActAlertaCadastro.this, ActLocalizacaoAlerta.class);

        intent.putExtra("enderecoSelecionado", enderecoSelecionado);
        intent.putExtra("visualizacaoAlertaApenas", visualizacaoAlertaApenas.toString());

        startActivity(intent);
    }

    public void processaErroValidacao(ErroValidacaoModel erroValidacaoModel){

        if(erroValidacaoModel.getCampoErro().equals("desastreAvistado"))
        {
            Geral.chamarAlertDialog(this, "Erro", erroValidacaoModel.getDescricaoErro());
            return;
        }

        if(erroValidacaoModel.getCampoErro().equals("situacaoUsuario"))
        {
            Geral.chamarAlertDialog(this, "Erro", erroValidacaoModel.getDescricaoErro());
            return;
        }

        if(erroValidacaoModel.getCampoErro().equals("descricao"))
        {
            txtBDescricao.setError(erroValidacaoModel.getDescricaoErro(),true);
            return;
        }

        if(erroValidacaoModel.getCampoErro().equals("localizacao"))
        {
            txtBLocal.setError(erroValidacaoModel.getDescricaoErro(),true);
            return;
        }

        Geral.chamarAlertDialog(this,"", erroValidacaoModel.getDescricaoErro());
    }

    public void finalizar() {
        try {
            AlertaUsuarioCliente alertaUsuarioCliente = new AlertaUsuarioCliente();

            alertaUsuarioCliente.setDesastreAvistado(spinnerDesastreAvistado.getItems().get(spinnerDesastreAvistado.getSelectedIndex()).toString());

            alertaUsuarioCliente.setSituacaoUsuario(spinnerMinhaSituacao.getItems().get(spinnerMinhaSituacao.getSelectedIndex()).toString());

            alertaUsuarioCliente.setDescricao(txtDescricao.getText().toString());

            if (enderecoSelecionado != null)
                alertaUsuarioCliente.setLatitude(enderecoSelecionado.getLatitude());

            if (enderecoSelecionado != null)
                alertaUsuarioCliente.setLongitude(enderecoSelecionado.getLongitude());

            ErroValidacaoModel erroValidacaoModel = AlertaUsuarioClienteController.validarCampos(alertaUsuarioCliente);

            if (erroValidacaoModel != null) {
                processaErroValidacao(erroValidacaoModel);
                return;
            }

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Finalizando. Por favor aguarde...");
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();

            UsuarioCliente uc = UsuarioClienteController.retornaUsuarioClienteDAOInterno();

            alertaUsuarioCliente.setIdUsuarioCliente(uc.getId());

            AlertaUsuarioClienteController.inserirAPI(alertaUsuarioCliente, this);
        } catch (Exception ex) {
            Geral.chamarAlertDialog(this, "Erro", ex.getMessage());
        }
    }

    public boolean encontrarEndereco(double latitude, double longitude) {
        try {
            Geocoder geo = new Geocoder(ActAlertaCadastro.this.getApplicationContext(), Locale.getDefault());

            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                Geral.chamarAlertDialog(this, "Mensagem", "Nenhum endereço válido foi encontrado para esse local");

                return false;
            } else {
                if (addresses.size() <= 0) {
                    Geral.chamarAlertDialog(this, "Mensagem", "Nenhum endereço válido foi encontrado para esse local");
                    return false;
                }

                Address a = addresses.get(0);

                enderecoSelecionado = new Endereco();
                enderecoSelecionado.setCidade(a.getSubAdminArea());
                enderecoSelecionado.setEndereco(a.getAddressLine(0));
                enderecoSelecionado.setEstado(a.getAdminArea());
                enderecoSelecionado.setLatitude(latitude);
                enderecoSelecionado.setLongitude(longitude);
                enderecoSelecionado.setPais(a.getCountryName());
                enderecoSelecionado.setStatus("A");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao encontrar o endereço de tal local. Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    //endregion

    //region EVENTOS

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!visualizacaoAlertaApenas) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.act_autenticacao_menu, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.act_autenticacao_menu_title_salvar: {
                finalizar();
                break;
            }

            case R.id.act_autenticacao_menu_title_cancelar: {
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        enderecoSelecionado = ActLocalizacaoAlerta.enderecoSelecionado;
        boolean selecionou = ActLocalizacaoAlerta.selecionou;

        if (enderecoSelecionado != null && selecionou)
            txtLocal.setText(enderecoSelecionado.getEndereco());

        ActLocalizacaoAlerta.enderecoSelecionado = null;
        ActLocalizacaoAlerta.selecionou = false;
    }

    //endregion

    //region METODOS API

    @Override
    public void retornaMensagemSucesso(String mensagem) {
        finish();
    }

    @Override
    public void retornaMensagemErro(String mensagem) {
        progressDialog.cancel();

        if(mensagem.contains("desastreAvistado")){
            Geral.chamarAlertDialog(this, "Erro", mensagem);
            return;
        }

        if(mensagem.contains("situacaoUsuario")){
            Geral.chamarAlertDialog(this, "Erro", mensagem);
            return;
        }

        if(mensagem.contains("descricao")){
            txtBDescricao.setError(mensagem,true);
            return;
        }

        if(mensagem.contains("localizacao")){
            txtBLocal.setError(mensagem,true);
            return;
        }

        Geral.chamarAlertDialog(this, "",mensagem);
    }

    //endregion
}