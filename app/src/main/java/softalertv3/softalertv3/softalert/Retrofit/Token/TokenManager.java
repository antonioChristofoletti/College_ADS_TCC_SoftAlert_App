package softalertv3.softalertv3.softalert.Retrofit.Token;

import com.auth0.android.jwt.JWT;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.UsuarioAdministrador;
import softalertv3.softalertv3.softalert.Retrofit.RetrofitConfig;

public abstract class TokenManager {

    private static String token = "";
    private static final String usuario = "antonio";
    private static final String senha = "202cb962ac59075b964b07152d234b70";

    private static Object respostaErro;
    private static String respostaSucesso;

    public static void retornaToken(final InterfaceListenerAPI interfaceListenerAPI) {

        if (token != "") {
            if (tokenValido(token)) {
                interfaceListenerAPI.retornaMensagemSucesso(token);
                return;
            }
        }

        UsuarioAdministrador ua = new UsuarioAdministrador();
        ua.setUsuario(usuario);
        ua.setSenha(senha);

        Call<Object> call = new RetrofitConfig().getTokenService().autenticarUsuario(ua);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (!response.isSuccessful()) {
                    String error = "Erro ao retornar o token. Código de erro HTTP " + response.code();
                    interfaceListenerAPI.retornaMensagemErro(error);
                    return;
                }

                String tokenAtual = response.body().toString();

                int caracterInicial = tokenAtual.indexOf("=");

                respostaSucesso = tokenAtual.substring(caracterInicial+1, tokenAtual.length() - 1);
                tokenAtual = respostaSucesso;

                interfaceListenerAPI.retornaMensagemSucesso(tokenAtual);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                String error = "Erro ao retornar o token.";

                if (t.getMessage() != null)
                    error += t.getMessage();

                interfaceListenerAPI.retornaMensagemErro(error);
            }
        });
    }

    public static boolean tokenValido(String token) {
        JWT jwt = new JWT(token);

        return new Date().before(jwt.getExpiresAt());
    }
}