package softalertv3.softalertv3.softalert.Retrofit.UsuarioCliente;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.RequisicaoEnvioSMS;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Retrofit.ErrorMessageAPI;
import softalertv3.softalertv3.softalert.Retrofit.JSONManager;
import softalertv3.softalertv3.softalert.Retrofit.RetrofitConfig;
import softalertv3.softalertv3.softalert.Retrofit.Token.TokenManager;

public class UsuarioClienteManager implements InterfaceListenerAPI {

    private static Object respostaErro;
    private static String respostaSucesso;

    private UsuarioCliente usuarioCliente;
    private RequisicaoEnvioSMS requisicaoEnvioSMS;

    private InterfaceListenerAPI interfaceListenerAPI;

    private String metodoCentral = "";


    public void inserirUsuario(UsuarioCliente usuarioCliente, InterfaceListenerAPI interfaceListenerAPI) {

        this.usuarioCliente = usuarioCliente;

        this.interfaceListenerAPI = interfaceListenerAPI;

        metodoCentral = "inserirUsuario";

        TokenManager.retornaToken(this);
    }

    public void inserirUsuarioInterno(String token) {
        Call<UsuarioCliente> call = new RetrofitConfig().getUsuarioClienteService().inserirUsuarioCliente(usuarioCliente, "Bearer " + token);

        String aux = JSONManager.convertJSON(usuarioCliente);

        call.enqueue(new Callback<UsuarioCliente>() {
            @Override
            public void onResponse(Call<UsuarioCliente> call, Response<UsuarioCliente> response) {

                if (!response.isSuccessful()) {

                    ObjectMapper om = new ObjectMapper();

                    ErrorMessageAPI emAPI = null;
                    try {
                        emAPI = JSONManager.convertJsonToErrorMessageAPI(response.errorBody().string());
                    } catch (IOException e) {
                        emAPI = null;
                    }

                    if (emAPI != null) {
                        interfaceListenerAPI.retornaMensagemErro(emAPI.getErrorMessage());
                        return;
                    }

                    String error = "Erro ao inserir. Código de erro HTTP " + response.code();
                    interfaceListenerAPI.retornaMensagemErro(error);
                    return;
                }

                interfaceListenerAPI.retornaMensagemSucesso("Cadastro efetuado com sucesso");
            }

            @Override
            public void onFailure(Call<UsuarioCliente> call, Throwable t) {
                String error = "Erro ao inserir.";

                if (t.getMessage() != null)
                    error += t.getMessage();

                interfaceListenerAPI.retornaMensagemErro(error);
            }
        });
    }

    public void enviarSMSAutenticacao(RequisicaoEnvioSMS requisicaoEnvioSMS, InterfaceListenerAPI interfaceListenerAPI) {

        this.requisicaoEnvioSMS = requisicaoEnvioSMS;

        this.interfaceListenerAPI = interfaceListenerAPI;

        metodoCentral = "enviarSMSAutenticacao";

        TokenManager.retornaToken(this);
    }

    public void enviarSMSAutenticacaoInterno(String token) {
        Call<RequisicaoEnvioSMS> call = new RetrofitConfig().getUsuarioClienteService().enviarSMS(requisicaoEnvioSMS, "Bearer " + token);

        String aux = JSONManager.convertJSON(requisicaoEnvioSMS);

        call.enqueue(new Callback<RequisicaoEnvioSMS>() {
            @Override
            public void onResponse(Call<RequisicaoEnvioSMS> call, Response<RequisicaoEnvioSMS> response) {

                if (!response.isSuccessful()) {

                    ObjectMapper om = new ObjectMapper();

                    ErrorMessageAPI emAPI = null;
                    try {
                        emAPI = JSONManager.convertJsonToErrorMessageAPI(response.errorBody().string());
                    } catch (IOException e) {
                        emAPI = null;
                    }

                    if (emAPI != null) {
                        interfaceListenerAPI.retornaMensagemErro(emAPI.getErrorMessage());
                        return;
                    }

                    String error = "Erro ao inserir. Código de erro HTTP " + response.code();
                    interfaceListenerAPI.retornaMensagemErro(error);
                    return;
                }

                interfaceListenerAPI.retornaMensagemSucesso("SMS enviado com sucesso");
            }

            @Override
            public void onFailure(Call<RequisicaoEnvioSMS> call, Throwable t) {
                String error = "Erro ao enviar SMS";

                if (t.getMessage() != null)
                    error += t.getMessage();

                interfaceListenerAPI.retornaMensagemErro(error);
            }
        });
    }

    @Override
    public void retornaMensagemSucesso(String mensagem) {

        switch (metodoCentral) {
            case "inserirUsuario": {
                inserirUsuarioInterno(mensagem);
                break;
            }
            case "enviarSMSAutenticacao": {
                enviarSMSAutenticacaoInterno(mensagem);
                break;
            }
        }

    }

    @Override
    public void retornaMensagemErro(String mensagem) {
        interfaceListenerAPI.retornaMensagemErro(mensagem);
    }
}