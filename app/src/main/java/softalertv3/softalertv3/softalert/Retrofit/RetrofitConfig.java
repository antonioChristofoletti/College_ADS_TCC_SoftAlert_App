package softalertv3.softalertv3.softalert.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import softalertv3.softalertv3.softalert.Retrofit.Token.TokenService;
import softalertv3.softalertv3.softalert.Retrofit.UsuarioCliente.UsuarioClienteService;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.105:8080/SoftAlertRest/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public UsuarioClienteService getUsuarioClienteService() {
        return this.retrofit.create(UsuarioClienteService.class);
    }

    public TokenService getTokenService() {
        return this.retrofit.create(TokenService.class);
    }
}
