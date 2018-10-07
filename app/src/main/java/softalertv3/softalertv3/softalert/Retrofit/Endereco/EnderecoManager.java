package softalertv3.softalertv3.softalert.Retrofit.Endereco;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.Endereco;
import softalertv3.softalertv3.softalert.Model.RequisicaoEnvioSMS;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Retrofit.ErrorMessageAPI;
import softalertv3.softalertv3.softalert.Retrofit.JSONManager;
import softalertv3.softalertv3.softalert.Retrofit.RetrofitConfig;
import softalertv3.softalertv3.softalert.Retrofit.Token.TokenManager;

public class EnderecoManager implements InterfaceListenerAPI {

    private static Object respostaErro;
    private static String respostaSucesso;

    private static UsuarioCliente usuarioCliente;

    private InterfaceListenerAPI interfaceListenerAPI;

    private String metodoCentral = "";

    public static ArrayList<Endereco> listaEnderecos;

    public void inserirEnderecos(UsuarioCliente usuarioCliente, InterfaceListenerAPI interfaceListenerAPI) {

        this.usuarioCliente = usuarioCliente;

        this.interfaceListenerAPI = interfaceListenerAPI;

        metodoCentral = "inserirEnderecos";

        TokenManager.retornaToken(this);
    }

    public void inserirEnderecosInternos(String token) {
        Call<UsuarioCliente> call = new RetrofitConfig().getEnderecoUsuarioClienteService().inseriEndereco(usuarioCliente, "Bearer " + token);

        call.enqueue(new Callback<UsuarioCliente>() {
            @Override
            public void onResponse(Call<UsuarioCliente> call, Response<UsuarioCliente> response) {

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
            public void onFailure(Call<UsuarioCliente> call, Throwable t) {
                String error = "Erro ao inserir.";

                if (t.getMessage() != null)
                    error += t.getMessage();

                interfaceListenerAPI.retornaMensagemErro(error);
            }
        });
    }

    public void retornaEnderecos(UsuarioCliente usuarioCliente, InterfaceListenerAPI interfaceListenerAPI) {

        this.usuarioCliente = usuarioCliente;

        this.interfaceListenerAPI = interfaceListenerAPI;

        metodoCentral = "retornaEnderecos";

        TokenManager.retornaToken(this);
    }

    public void retornaEnderecosInternos(String token) {
        Call<ArrayList<Endereco>> call = new RetrofitConfig().getEnderecoUsuarioClienteService().retornaEnderecos(usuarioCliente.getId(), "Bearer " + token);

        call.enqueue(new Callback<ArrayList<Endereco>>() {
            @Override
            public void onResponse(Call<ArrayList<Endereco>> call, Response<ArrayList<Endereco>> response) {

                if (!response.isSuccessful()) {

                    if(response.code() == 404)
                    {
                        listaEnderecos = new ArrayList<>();
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

                    String error = "Erro ao inserir. Código de erro HTTP " + response.code();
                    interfaceListenerAPI.retornaMensagemErro(error);
                    return;
                }

                if(response.code() == 200)
                {
                    listaEnderecos = response.body();
                    interfaceListenerAPI.retornaMensagemSucesso("");
                    return;
                }

                interfaceListenerAPI.retornaMensagemSucesso("Cadastro efetuado com sucesso");
            }

            @Override
            public void onFailure(Call<ArrayList<Endereco>> call, Throwable t) {
                String error = "Erro ao inserir.";

                if (t.getMessage() != null)
                    error += t.getMessage();

                interfaceListenerAPI.retornaMensagemErro(error);
            }
        });
    }

    @Override
    public void retornaMensagemSucesso(String mensagem) {

        switch (metodoCentral) {
            case "inserirEnderecos": {
                inserirEnderecosInternos(mensagem);
                break;
            }

            case "retornaEnderecos" :{
                retornaEnderecosInternos(mensagem);
                break;
            }
        }

    }

    @Override
    public void retornaMensagemErro(String mensagem) {
        interfaceListenerAPI.retornaMensagemErro(mensagem);
    }
}