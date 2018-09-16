package softalertv3.softalertv3.softalert.View;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.RequisicaoEnvioSMS;
import softalertv3.softalertv3.softalert.Model.RequisicaoEnvioSMSTelefone;

import softalertv3.softalertv3.R;

import java.util.ArrayList;

import softalertv3.softalertv3.softalert.Uteis.CodigoPermissao;
import softalertv3.softalertv3.softalert.Uteis.Geral;
import softalertv3.softalertv3.softalert.Uteis.MaskWatcher;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class ActCadastro_telefone_basico extends AppCompatActivity implements InterfaceListenerAPI {

    public static final String EXTRA_NUMERO_DISPOSITIVO = "EXTRA_NUMERO_DISPOSITIVO";

    public EditText txtNumeroDispositivo;
    public EditText txtCodigoVerificador;

    public TextFieldBoxes txtbNumeroDispositivo;
    public TextFieldBoxes txtbCodigoVerificador;

    public Button btnEnviarCodigo_content_act_cadastro_telefone_basico;
    public Button btnProsseguir_content_act_cadastro_telefone_basico;

    public Toolbar toolbar;

    public String codigo;

    public int contaVezesSMS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_telefone_basico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button b = (Button) findViewById(R.id.button);

       b.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(ActCadastro_telefone_basico.this, ActPrincipal.class);
               startActivity(intent);
           }
       });
        configuraComponentes();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CodigoPermissao.PHONE_READ_STATE: {
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equalsIgnoreCase(Manifest.permission.READ_PHONE_STATE) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        setaNumeroTelefone();
                    }
                }
                break;
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //region METODOS

    public void configuraComponentes() {
        txtNumeroDispositivo = (EditText) findViewById(R.id.txtTelefone_content_act_cadastro_telefone_basico);
        txtbNumeroDispositivo = (TextFieldBoxes) findViewById(R.id.txtbTelefone_content_act_cadastro_telefone_basico);

        txtNumeroDispositivo.addTextChangedListener(new MaskWatcher(MaskWatcher.FORMAT_FONE));

        txtCodigoVerificador = (EditText) findViewById(R.id.txtCodigoVerificador_content_act_cadastro_telefone_basico);
        txtbCodigoVerificador = (TextFieldBoxes) findViewById(R.id.txtbCodigoVerificador_content_act_cadastro_telefone_basico);

        txtbCodigoVerificador.setEnabled(false);

        txtbNumeroDispositivo.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaAlertDialogConfirmacaoBuscarNumeroTelefone();
            }
        });

        txtbCodigoVerificador.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensagem = "";
                mensagem = "O código verificador ajuda na segurança e na eficiência do aplicativo";
                Geral.chamarAlertDialog(ActCadastro_telefone_basico.this, "", mensagem);
            }
        });

        btnEnviarCodigo_content_act_cadastro_telefone_basico = (Button) findViewById(R.id.btnEnviarCodigo_content_act_cadastro_telefone_basico);
        btnProsseguir_content_act_cadastro_telefone_basico = (Button) findViewById(R.id.btnProsseguir_content_act_cadastro_telefone_basico);
    }

    public void chamaAlertDialogConfirmacaoBuscarNumeroTelefone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActCadastro_telefone_basico.this);

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                buscarNumeroTelefone();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        Geral.chamarAlertDialog(builder, "", "Podemos descobrir o seu número automáticamente. Você gostaria?");
    }

    public void buscarNumeroTelefone() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setPositiveButton("Checar Permissão", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(ActCadastro_telefone_basico.this, new String[]{Manifest.permission.READ_PHONE_STATE}, CodigoPermissao.PHONE_READ_STATE);
                    }
                });

                builder.setNegativeButton("Negar Permissão", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                Geral.chamarAlertDialog(builder, "", "A permissão READ_PHONE_STATE será utilizada para ler o número de telefone");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, CodigoPermissao.PHONE_READ_STATE);
            }
        } else {
            setaNumeroTelefone();
        }
    }

    public void setaNumeroTelefone() {
        try {
            TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String numeroDispositivo = tMgr.getLine1Number();

            if (numeroDispositivo == null) {
                txtbNumeroDispositivo.setError("Número não encontrado. Verifique o cartão ou as permissões para o aplicativo", true);
                return;
            }

            numeroDispositivo = numeroDispositivo.replace("+", "");

            numeroDispositivo = "0" + numeroDispositivo.substring(2, numeroDispositivo.length());

            numeroDispositivo = Geral.setaMascara(numeroDispositivo, "(##)####-#####");
            txtNumeroDispositivo.setText(numeroDispositivo);
        } catch (SecurityException ex) {
            txtbNumeroDispositivo.setError("Número não encontrado. Verifique o cartão ou as permissões para o aplicativo", true);
            return;
        }
    }

    public void enviarSMSAutenticacao() {
        try {
            String telefone = Geral.removerMascara(txtNumeroDispositivo.getText().toString());

            if (telefone.length() < 12) {
                txtbNumeroDispositivo.setError("O número informado é inválido", true);
                return;
            }

            if (contaVezesSMS == 0)
                Geral.chamarAlertDialog(this, "", "Um SMS será encaminhado. Caso não receba tente novamente.");
            else if (contaVezesSMS == 1 || contaVezesSMS == 2)
                Geral.chamarAlertDialog(this, "", "Atenção! O limite da validação por SMS é de 3 tentativas.");
            else if (contaVezesSMS > 2) {
                Geral.chamarAlertDialog(this, "", "O limite de tentativas foi alcançado. Tente novamente amanhã.");
                return;
            }

            contaVezesSMS++;

            codigo = Geral.getRandomString(4);
            txtCodigoVerificador.setText(codigo);

            RequisicaoEnvioSMS requisicaoEnvioSMS = new RequisicaoEnvioSMS();

            requisicaoEnvioSMS.setMensagem("O seu código de verificação do SoftAlert é: " + codigo);

            ArrayList<RequisicaoEnvioSMSTelefone> listaTelefones = new ArrayList<RequisicaoEnvioSMSTelefone>();
            RequisicaoEnvioSMSTelefone requisicaoEnvioSMSTelefone = new RequisicaoEnvioSMSTelefone();

            requisicaoEnvioSMSTelefone.setTelefone("+55" + Geral.removerMascara(txtNumeroDispositivo.getText().toString()));
            requisicaoEnvioSMSTelefone.setMensagemProcessada('N');

            listaTelefones.add(requisicaoEnvioSMSTelefone);

            requisicaoEnvioSMS.setTelefones(listaTelefones);

            // UsuarioClienteController.enviarSMS(requisicaoEnvioSMS, this);

            txtbCodigoVerificador.setEnabled(true);
            txtbCodigoVerificador.requestFocus();
        } catch (Exception ex) {
            Geral.chamarAlertDialog(this, "Erro", ex.getMessage());
        }
    }


    //endregion

    public void onClickbtnEnviarCodigo_content_act_cadastro_telefone_basico(View v) {
        enviarSMSAutenticacao();
    }

    public void onClickbtnProsseguir_content_act_cadastro_telefone_basico(View v) {
        String codigoVerificador = txtCodigoVerificador.getText().toString();

        if (codigoVerificador.length() < 4) {
            txtbCodigoVerificador.setError("O código verificador necessita de 4 dígitos", true);
            return;
        }

        if (!codigoVerificador.equals(codigo)) {
            txtbCodigoVerificador.setError("O código informado é inválido", true);
            return;
        }

        txtbCodigoVerificador.setEnabled(false);
        txtbNumeroDispositivo.setEnabled(false);
        btnEnviarCodigo_content_act_cadastro_telefone_basico.setEnabled(false);

        Intent intent = new Intent(this, ActCadastroUsuario.class);
        intent.putExtra(EXTRA_NUMERO_DISPOSITIVO, txtNumeroDispositivo.getText().toString());
        startActivity(intent);
    }

    @Override
    public void retornaMensagemSucesso(String mensagem) {

    }

    @Override
    public void retornaMensagemErro(String mensagem) {
        Geral.chamarAlertDialog(this, "Erro", mensagem);
    }
}