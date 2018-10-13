package softalertv3.softalertv3.softalert.Retrofit.AlertaUsuarioCliente;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import softalertv3.softalertv3.softalert.Model.AlertaUsuarioCliente;

public interface AlertaUsuarioClienteService {

    @POST("alertaUsuarioCliente/")
    Call<AlertaUsuarioCliente> inserirAlertaUsuarioCliente(@Body AlertaUsuarioCliente alertaUsuarioCliente, @Header("Authorization") String authHeader);

    @PUT("alertaUsuarioCliente/")
    Call<AlertaUsuarioCliente> editarEnderecoUsuarioAdministrador(@Body AlertaUsuarioCliente alertaUsuarioCliente, @Header("Authorization") String authHeader);

    @GET("alertaUsuarioCliente/{idUsuarioCliente}/")
    Call<ArrayList<AlertaUsuarioCliente>> retornaEnderecos(@Path("idUsuarioCliente") int idUsuarioCliente, @Header("Authorization") String authHeader);
}