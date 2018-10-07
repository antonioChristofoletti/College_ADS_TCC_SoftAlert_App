package softalertv3.softalertv3.softalert.Retrofit.Endereco;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import softalertv3.softalertv3.softalert.Model.Endereco;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;

public interface EnderecoService {

    @POST("enderecoUsuario/enderecoUsuarioCliente/")
    Call<UsuarioCliente> inseriEndereco(@Body UsuarioCliente usuarioCliente, @Header("Authorization") String authHeader);

    @GET("enderecoUsuario/enderecoUsuarioCliente/{idUsuarioCliente}/")
    Call<ArrayList<Endereco>> retornaEnderecos(@Path("idUsuarioCliente") int idUsuarioCliente, @Header("Authorization") String authHeader);
}