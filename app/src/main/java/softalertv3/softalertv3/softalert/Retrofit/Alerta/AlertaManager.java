package softalertv3.softalertv3.softalert.Retrofit.Alerta;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.Alerta;
import softalertv3.softalertv3.softalert.Model.AlertaPossuiUsuario;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Retrofit.ErrorMessageAPI;
import softalertv3.softalertv3.softalert.Retrofit.JSONManager;
import softalertv3.softalertv3.softalert.Retrofit.RetrofitConfig;
import softalertv3.softalertv3.softalert.Retrofit.Token.TokenManager;

public class AlertaManager implements InterfaceListenerAPI {

    public static UsuarioCliente usuarioCliente;
    public static ArrayList<Alerta> listaAlertas;

    private InterfaceListenerAPI interfaceListenerAPI;
    private String metodoCentral = "";

    private AlertaPossuiUsuario alertaPossuiUsuario;

    //region retornaAlertas

    public void retornaAlertas(UsuarioCliente usuarioCliente, InterfaceListenerAPI interfaceListenerAPI) {

        this.usuarioCliente = usuarioCliente;

        this.interfaceListenerAPI = interfaceListenerAPI;

        metodoCentral = "retornaAlertas";

        TokenManager.retornaToken(this);
    }

    public void retornaAlertasInternos(String token) {
        Call<ArrayList<Alerta>> call = new RetrofitConfig().getAlertaService().retornaEnderecos(usuarioCliente.getId(), "Bearer " + token);

        call.enqueue(new Callback<ArrayList<Alerta>>() {
            @Override
            public void onResponse(Call<ArrayList<Alerta>> call, Response<ArrayList<Alerta>> response) {

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
            public void onFailure(Call<ArrayList<Alerta>> call, Throwable t) {
                String error = "Erro ao pesquisar alertas.";

                if (t.getMessage() != null)
                    error += t.getMessage();

                interfaceListenerAPI.retornaMensagemErro(error);
            }
        });
    }

    //endregion

    //region atualizarAlertaPossuiUsuario

    public void atualizarAlertaPossuiUsuario(AlertaPossuiUsuario alertaPossuiUsuario, InterfaceListenerAPI interfaceListenerAPI) {

        this.alertaPossuiUsuario = alertaPossuiUsuario;

        this.interfaceListenerAPI = interfaceListenerAPI;

        metodoCentral = "atualizarAlertaPossuiUsuario";

        TokenManager.retornaToken(this);
    }

    public void atualizarAlertaPossuiUsuarioInternos(String token) {

        Call<AlertaPossuiUsuario> call = new RetrofitConfig().getAlertaService().atualizarAlertaPossuiUsuario(this.alertaPossuiUsuario, "Bearer " + token);

        call.enqueue(new Callback<AlertaPossuiUsuario>() {
            @Override
            public void onResponse(Call<AlertaPossuiUsuario> call, Response<AlertaPossuiUsuario> response) {

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

                    String error = "Erro ao atualizar. Código de erro HTTP " + response.code();
                    interfaceListenerAPI.retornaMensagemErro(error);
                    return;
                }

                interfaceListenerAPI.retornaMensagemSucesso("Atualização efetivada com sucesso");
            }

            @Override
            public void onFailure(Call<AlertaPossuiUsuario> call, Throwable t) {
                String error = "Erro ao atualizar.";

                if (t.getMessage() != null)
                    error += t.getMessage();

                interfaceListenerAPI.retornaMensagemErro(error);
            }
        });
    }

    //endregion

    //region METODOS API

    @Override
    public void retornaMensagemSucesso(String mensagem) {

        switch (metodoCentral) {
            case "retornaAlertas": {
                retornaAlertasInternos(mensagem);
                break;
            }

            case "atualizarAlertaPossuiUsuario": {
                atualizarAlertaPossuiUsuarioInternos(mensagem);
                break;
            }
        }
    }

    @Override
    public void retornaMensagemErro(String mensagem) {
        interfaceListenerAPI.retornaMensagemErro(mensagem);
    }

    //endregion
}