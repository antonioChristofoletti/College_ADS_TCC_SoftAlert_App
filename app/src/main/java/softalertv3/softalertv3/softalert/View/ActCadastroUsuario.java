package softalertv3.softalertv3.softalert.View;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import softalertv3.softalertv3.softalert.Controller.UsuarioClienteController;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.ErroValidacaoModel;
import softalertv3.softalertv3.softalert.Model.Telefone;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Uteis.Geral;
import softalertv3.softalertv3.softalert.Uteis.MaskWatcher;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import softalertv3.softalertv3.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import softalertv3.softalertv3.softalert.View.ActPrincipal.ActPrincipal;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class ActCadastroUsuario extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, InterfaceListenerAPI {

    private EditText txtCPF;
    private EditText txtDataNascimento;
    private EditText txtTelefone;
    private EditText txtNome;

    private TextFieldBoxes txtbCPF;
    private TextFieldBoxes txtbDataNascimento;
    private TextFieldBoxes txtbTelefone;
    private TextFieldBoxes txtbNome;

    //Prevene que mais de 1 dateTimePicker seja invocado
    boolean dateTimePickerAtivado = false;

    private UsuarioCliente usuarioClienteIncAlt;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configuraComponentes();
    }

    public void configuraComponentes() {
        txtNome = (EditText) findViewById(R.id.txtNome_content_act_cadastro_usuario);

        txtbNome = (TextFieldBoxes) findViewById(R.id.txtbNome_content_act_cadastro_usuario);

        txtCPF = (EditText) findViewById(R.id.txtCPF_content_act_cadastro_usuario);
        txtCPF.addTextChangedListener(new MaskWatcher(MaskWatcher.FORMAT_CPF));

        txtbCPF = (TextFieldBoxes) findViewById(R.id.txtbCPF_content_act_cadastro_usuario);

        txtTelefone = (EditText) findViewById(R.id.txtTelefone_content_act_cadastro_usuario);
        txtTelefone.addTextChangedListener(new MaskWatcher(MaskWatcher.FORMAT_FONE));

        txtTelefone.setText(getIntent().getStringExtra(ActCadastro_telefone_basico.EXTRA_NUMERO_DISPOSITIVO));

        txtbTelefone = (TextFieldBoxes) findViewById(R.id.txtbTelefone_content_act_cadastro_usuario);

        txtDataNascimento = (EditText) findViewById(R.id.txtDataNascimento_content_act_cadastro_usuario);

        txtDataNascimento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    chamaDateTimePicker("DatePickerDataNascimento", txtDataNascimento.getText().toString(), ActCadastroUsuario.this);
            }
        });

        txtDataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaDateTimePicker("DatePickerDataNascimento", txtDataNascimento.getText().toString(), ActCadastroUsuario.this);
            }
        });

        txtbDataNascimento = (TextFieldBoxes) findViewById(R.id.txtbDataNascimento_content_act_cadastro_usuario);

        txtbDataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaDateTimePicker("DatePickerDataNascimento", txtDataNascimento.getText().toString(), ActCadastroUsuario.this);
            }
        });

        txtbDataNascimento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    chamaDateTimePicker("DatePickerDataNascimento", txtDataNascimento.getText().toString(), ActCadastroUsuario.this);
            }
        });
    }

    public void salvarUsuario() {
        try {

            usuarioClienteIncAlt = new UsuarioCliente();

            usuarioClienteIncAlt.setCpf(Geral.removerMascara(txtCPF.getText().toString()));

            Date dataNascimento = Geral.geraData("dd/MM/yyyy", Geral.removerMascara(txtDataNascimento.getText().toString()));

            usuarioClienteIncAlt.setDataNascimento(dataNascimento);

            usuarioClienteIncAlt.setNome(txtNome.getText().toString());

            ArrayList<Telefone> telefones = new ArrayList<Telefone>();

            Telefone telefone = new Telefone();
            telefone.setTelefone(Geral.removerMascara(txtTelefone.getText().toString()));
            telefone.setStatus("A");
            telefones.add(telefone);
            usuarioClienteIncAlt.setListaTelefones(telefones);

            ErroValidacaoModel erroValidacaoModel = UsuarioClienteController.validarCamposUsuarioCliente(usuarioClienteIncAlt);

            if (erroValidacaoModel != null) {
                processaErroValidacao(erroValidacaoModel);
                return;
            }
            progressDialog = new ProgressDialog(ActCadastroUsuario.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Finalizando o cadastro. Por favor aguarde...");
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();

            UsuarioClienteController.inserirAPI(usuarioClienteIncAlt,this);

        } catch (Exception ex) {
            Geral.chamarAlertDialog(this, "Erro", ex.getMessage());
        }
    }

    public void processaErroValidacao(ErroValidacaoModel erroValidacaoModel){

        if(erroValidacaoModel.getCampoErro().equals("cpf"))
        {
            txtbCPF.setError(erroValidacaoModel.getDescricaoErro(),true);
            return;
        }

        if(erroValidacaoModel.getCampoErro().equals("telefone"))
        {
            txtbTelefone.setError(erroValidacaoModel.getDescricaoErro(),true);
            return;
        }

        if(erroValidacaoModel.getCampoErro().equals("nome"))
        {
            txtbNome.setError(erroValidacaoModel.getDescricaoErro(),true);
            return;
        }

        if(erroValidacaoModel.getCampoErro().equals("dataNascimento"))
        {
            txtbDataNascimento.setError(erroValidacaoModel.getDescricaoErro(),true);
            return;
        }

        Geral.chamarAlertDialog(this,"", erroValidacaoModel.getDescricaoErro());
    }

    @Override
    public void retornaMensagemSucesso(String mensagem) {
        progressDialog.cancel();
        try {
            usuarioClienteIncAlt = UsuarioClienteController.retornaUsuarioClienteInserirAPI();
            UsuarioClienteController.inserirDAOInterno(usuarioClienteIncAlt);

            Intent intent = new Intent(this, ActPrincipal.class);
            startActivity(intent);

            finish();
        }catch (Exception ex){
            Geral.chamarAlertDialog(this,"Erro", "Erro ao realizar inserir as informações internamente. Erro: " + ex.getMessage());
        }
    }

    @Override
    public void retornaMensagemErro(String mensagem) {
        progressDialog.cancel();

        if(mensagem.contains("CPF")){
            txtbCPF.setError(mensagem,true);
            return;
        }

        if(mensagem.contains("telefone")){
            txtbTelefone.setError(mensagem,true);
            return;
        }

        if(mensagem.contains("nome")){
            txtbNome.setError(mensagem,true);
            return;
        }

        if(mensagem.contains("dataNascimento")){
            txtbDataNascimento.setError(mensagem,true);
            return;
        }

        Geral.chamarAlertDialog(this, "",mensagem);
    }

    //region EVENTOS e METODOS DATE TIME PICKER

    public void chamaDateTimePicker(String tag, String dateDefault, DatePickerDialog.OnDateSetListener listener) {

        if (dateTimePickerAtivado)
            return;

        Calendar now = Calendar.getInstance();

        int ano = now.get(Calendar.YEAR);
        int mes = now.get(Calendar.MONTH);
        int dia = now.get(Calendar.DAY_OF_MONTH);

        String[] dateDefaultSplit = dateDefault.split("/");

        if (dateDefaultSplit.length == 3) {
            dia = Integer.parseInt(dateDefaultSplit[0]);
            mes = Integer.parseInt(dateDefaultSplit[1])-1;
            ano = Integer.parseInt(dateDefaultSplit[2]);
        }

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(listener, ano, mes, dia);

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dateTimePickerAtivado = false;
            }
        });

        dateTimePickerAtivado = true;
        datePickerDialog.show(getFragmentManager(), tag);
    }

    //Processa a data informada no DatePickerDialog
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String data;

        if (dayOfMonth < 10)
            data = "0" + dayOfMonth + "/";
        else
            data = dayOfMonth + "/";

        monthOfYear += 1;

        if (monthOfYear < 10)
            data += "0" + monthOfYear + "/";
        else
            data += monthOfYear + "/";

        data += year;

        if (view.getTag().toString().equals("DatePickerDataNascimento")) {
            txtDataNascimento.setText(data);
        }

        dateTimePickerAtivado = false;
    }

    //endregion

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
                salvarUsuario();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion
}