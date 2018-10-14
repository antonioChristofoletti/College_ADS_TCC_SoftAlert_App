package softalertv3.softalertv3.softalert.Retrofit.Alerta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import softalertv3.softalertv3.softalert.Model.Alerta;

public interface AlertaService {

    @GET("alerta/{idUsuarioCliente}/")
    Call<ArrayList<Alerta>> retornaEnderecos(@Path("idUsuarioCliente") int idUsuarioCliente, @Header("Authorization") String authHeader);
}