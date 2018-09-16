package softalertv3.softalertv3.softalert.Retrofit.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import softalertv3.softalertv3.softalert.Model.UsuarioAdministrador;

public interface TokenService {

    @POST("autenticacao/autenticar")
    Call<Object> autenticarUsuario(@Body UsuarioAdministrador usuarioAdministrador);
}