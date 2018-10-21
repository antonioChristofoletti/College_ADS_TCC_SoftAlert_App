package softalertv3.softalertv3.softalert.Controller;

import java.util.ArrayList;

import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.Alerta;
import softalertv3.softalertv3.softalert.Model.AlertaPossuiUsuario;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Retrofit.Alerta.AlertaManager;

public class AlertaController {

    public static void retornaAlertas(UsuarioCliente usuarioCliente, InterfaceListenerAPI interfaceListenerAPI) {
        AlertaManager alertaManager = new AlertaManager();
        alertaManager.retornaAlertas(usuarioCliente, interfaceListenerAPI);
    }

    public static void atualizarVinculoAlertaPossuiUsuario(AlertaPossuiUsuario alertaPossuiUsuario, InterfaceListenerAPI interfaceListenerAPI){
        AlertaManager alertaManager = new AlertaManager();
        alertaManager.atualizarAlertaPossuiUsuario(alertaPossuiUsuario, interfaceListenerAPI);
    }

    public static ArrayList<Alerta> retornaAlertaArrayList() {
        return AlertaManager.listaAlertas;
    }
}
