package softalertv3.softalertv3.softalert.Retrofit.AlertaUsuarioCliente;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.AlertaUsuarioCliente;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Retrofit.ErrorMessageAPI;
import softalertv3.softalertv3.softalert.Retrofit.JSONManager;
import softalertv3.softalertv3.softalert.Retrofit.RetrofitConfig;
import softalertv3.softalertv3.softalert.Retrofit.Token.TokenManager;

public class AlertaUsuarioClienteManager implements InterfaceListenerAPI {

    private static Object respostaErro;
    private static String respostaSucesso;

    public static UsuarioCliente usuarioCliente;
    public static ArrayList<AlertaUsuarioCliente> listaAlertas;

    private InterfaceListenerAPI interfaceListenerAPI;
    private String metodoCentral = "";

    private AlertaUsuarioCliente alertaUsuarioCliente;

    //region inserirAlertaUsuarioCliente

    public void inserirAlertaUsuarioCliente(AlertaUsuarioCliente alertaUsuarioCliente, InterfaceListenerAPI interfaceListenerAPI) {

        this.alertaUsuarioCliente = alertaUsuarioCliente;

        this.interfaceListenerAPI = interfaceListenerAPI;

        metodoCentral = "inserirAlertaUsuarioCliente";

        TokenManager.retornaToken(this);
    }

    public void inserirAlertaUsuarioClienteInterno(String token) {

        Call<AlertaUsuarioCliente> call = new RetrofitConfig().getAlertaUsuarioCliente().inserirAlertaUsuarioCliente(this.alertaUsuarioCliente, "Bearer " + token);


        String aux = JSONManager.convertJSON(alertaUsuarioCliente);

        call.enqueue(new Callback<AlertaUsuarioCliente>() {
            @Override
            public void onResponse(Call<AlertaUsuarioCliente> call, Response<AlertaUsuarioCliente> response) {

                if (!response.isSuccessful()) {

                        if(response.code() == 201)
                        {
                            interfaceListenerAPI.retornaMensagemErro("");
                            return;
                        }

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
            public void onFailure(Call<AlertaUsuarioCliente> call, Throwable t) {
                String error = "Erro ao inserir.";

                if (t.getMessage() != null)
                    error += t.getMessage();

                interfaceListenerAPI.retornaMensagemErro(error);
            }
        });
    }

    //endregion

    //region retornaAlertas

    public void retornaAlertas(UsuarioCliente usuarioCliente, InterfaceListenerAPI interfaceListenerAPI) {

        this.usuarioCliente = usuarioCliente;

        this.interfaceListenerAPI = interfaceListenerAPI;

        metodoCentral = "retornaAlertas";

        TokenManager.retornaToken(this);
    }

    public void retornaAlertasInternos(String token) {
        Call<ArrayList<AlertaUsuarioCliente>> call = new RetrofitConfig().getAlertaUsuarioCliente().retornaEnderecos(usuarioCliente.getId(), "Bearer " + token);

        call.enqueue(new Callback<ArrayList<AlertaUsuarioCliente>>() {
            @Override
            public void onResponse(Call<ArrayList<AlertaUsuarioCliente>> call, Response<ArrayList<AlertaUsuarioCliente>> response) {

                if (!response.isSuccessful()) {

                    if(response.code() == 404)
                    {
                        listaAlertas = new ArrayList<>();
                        interfaceListenerAPI.retornaMensagemSucesso("");
                        return;
                    }

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

                    String error = "Erro ao pesquisar alertas. Código de erro HTTP " + response.code();
                    interfaceListenerAPI.retornaMensagemErro(error);
                    return;
                }

                if(response.code() == 200)
                {
                    listaAlertas = response.body();
                    interfaceListenerAPI.retornaMensagemSucesso("");
                    return;
                }

                interfaceListenerAPI.retornaMensagemSucesso("");
            }

            @Override
            public void onFailure(Call<ArrayList<AlertaUsuarioCliente>> call, Throwable t) {
                String error = "Erro ao pesquisar alertas.";

                if (t.getMessage() != null)
                    error += t.getMessage();

                interfaceListenerAPI.retornaMensagemErro(error);
            }
        });
    }

    //endregion

    //region editarAlertaUsuarioCliente

    public void editarAlertaUsuarioCliente(AlertaUsuarioCliente alertaUsuarioCliente, InterfaceListenerAPI interfaceListenerAPI) {

        this.alertaUsuarioCliente = alertaUsuarioCliente;

        this.interfaceListenerAPI = interfaceListenerAPI;

        metodoCentral = "editarAlertaUsuarioCliente";

        TokenManager.retornaToken(this);
    }

    public void editarAlertaUsuarioClienteInterno(String token) {

        Call<AlertaUsuarioCliente> call = new RetrofitConfig().getAlertaUsuarioCliente().editarEnderecoUsuarioAdministrador(this.alertaUsuarioCliente, "Bearer " + token);


        String aux = JSONManager.convertJSON(alertaUsuarioCliente);

        call.enqueue(new Callback<AlertaUsuarioCliente>() {
            @Override
            public void onResponse(Call<AlertaUsuarioCliente> call, Response<AlertaUsuarioCliente> response) {

                if (!response.isSuccessful()) {

                    if(response.code() == 200)
                    {
                        interfaceListenerAPI.retornaMensagemErro("");
                        return;
                    }

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

                    String error = "Erro ao editar. Código de erro HTTP " + response.code();
                    interfaceListenerAPI.retornaMensagemErro(error);
                    return;
                }

                interfaceListenerAPI.retornaMensagemSucesso("Edição realizada com sucesso");
            }

            @Override
            public void onFailure(Call<AlertaUsuarioCliente> call, Throwable t) {
                String error = "Erro ao editar.";

                if (t.getMessage() != null)
                    error += t.getMessage();

                interfaceListenerAPI.retornaMensagemErro(error);
            }
        });
    }

    //endregion

    @Override
    public void retornaMensagemSucesso(String mensagem) {

        switch (metodoCentral) {
            case "inserirAlertaUsuarioCliente": {
                inserirAlertaUsuarioClienteInterno(mensagem);
                break;
            }

            case "retornaAlertas":{
                retornaAlertasInternos(mensagem);
                break;
            }

            case "editarAlertaUsuarioCliente":{
                editarAlertaUsuarioClienteInterno(mensagem);
                break;
            }
        }

    }

    @Override
    public void retornaMensagemErro(String mensagem) {
        interfaceListenerAPI.retornaMensagemErro(mensagem);
    }
}