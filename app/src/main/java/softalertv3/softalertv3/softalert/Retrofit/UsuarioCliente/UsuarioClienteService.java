package softalertv3.softalertv3.softalert.Retrofit.UsuarioCliente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import softalertv3.softalertv3.softalert.Model.RequisicaoEnvioSMS;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;

public interface UsuarioClienteService {

    @POST("usuarioCliente/")
    Call<UsuarioCliente> inserirUsuarioCliente(@Body UsuarioCliente usuarioCliente, @Header("Authorization") String authHeader);

    @POST("usuarioCliente/enviarSMS/")
    Call<RequisicaoEnvioSMS> enviarSMS(@Body RequisicaoEnvioSMS requisicaoEnvioSMS, @Header("Authorization") String authHeader);
}