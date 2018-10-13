package softalertv3.softalertv3.softalert.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import softalertv3.softalertv3.softalert.Model.AlertaUsuarioCliente;
import softalertv3.softalertv3.softalert.Retrofit.AlertaUsuarioCliente.AlertaUsuarioClienteService;
import softalertv3.softalertv3.softalert.Retrofit.Endereco.EnderecoService;
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

    public EnderecoService getEnderecoUsuarioClienteService() {
        return this.retrofit.create(EnderecoService.class);
    }

    public AlertaUsuarioClienteService getAlertaUsuarioCliente() {
        return this.retrofit.create(AlertaUsuarioClienteService.class);
    }

    public TokenService getTokenService() {
        return this.retrofit.create(TokenService.class);
    }
}
