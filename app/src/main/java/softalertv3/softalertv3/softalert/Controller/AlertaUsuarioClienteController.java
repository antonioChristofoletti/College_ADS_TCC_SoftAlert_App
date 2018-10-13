package softalertv3.softalertv3.softalert.Controller;

import java.util.ArrayList;

import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.AlertaUsuarioCliente;

import softalertv3.softalertv3.softalert.Model.ErroValidacaoModel;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Retrofit.AlertaUsuarioCliente.AlertaUsuarioClienteManager;

public class AlertaUsuarioClienteController {

    public static void inserirAPI(AlertaUsuarioCliente alertaUsuarioCliente, InterfaceListenerAPI interfaceListenerAPI) throws Exception {
        AlertaUsuarioClienteManager alertaUsuarioClienteManager = new AlertaUsuarioClienteManager();
        alertaUsuarioClienteManager.inserirAlertaUsuarioCliente(alertaUsuarioCliente, interfaceListenerAPI);
    }

    public static void editarAPI(AlertaUsuarioCliente alertaUsuarioCliente, InterfaceListenerAPI interfaceListenerAPI) throws Exception {
        AlertaUsuarioClienteManager alertaUsuarioClienteManager = new AlertaUsuarioClienteManager();
        alertaUsuarioClienteManager.editarAlertaUsuarioCliente(alertaUsuarioCliente, interfaceListenerAPI);
    }

    public static ErroValidacaoModel validarCampos(AlertaUsuarioCliente alertaUsuarioCliente) {

        if (alertaUsuarioCliente.getDesastreAvistado().contains("*"))
            return new ErroValidacaoModel("O desastre avistado é inválido", "desastreAvistado");

        if (alertaUsuarioCliente.getSituacaoUsuario().contains("*"))
            return new ErroValidacaoModel("A situação é inválido", "situacaoUsuario");

        if (alertaUsuarioCliente.getDescricao().equals(""))
            return new ErroValidacaoModel("A descrição é inválida", "descricao");

        if (alertaUsuarioCliente.getLatitude() == 0 && alertaUsuarioCliente.getLongitude() == 0)
            return new ErroValidacaoModel("A localização é inválida", "localizacao");

        return null;
    }

    public static void retornaAlertas(UsuarioCliente uc, InterfaceListenerAPI interfaceListenerAPI){
        AlertaUsuarioClienteManager alertaUsuarioClienteManager = new AlertaUsuarioClienteManager();
        alertaUsuarioClienteManager.retornaAlertas(uc, interfaceListenerAPI);
    }

    public static ArrayList<AlertaUsuarioCliente> retornaListaAlerta(){
        return AlertaUsuarioClienteManager.listaAlertas;
    }
}