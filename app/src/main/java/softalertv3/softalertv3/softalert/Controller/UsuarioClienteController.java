package softalertv3.softalertv3.softalert.Controller;

import softalertv3.softalertv3.softalert.DAOInterno.DAO.UsuarioClienteDAO;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.ErroValidacaoModel;
import softalertv3.softalertv3.softalert.Model.RequisicaoEnvioSMS;
import softalertv3.softalertv3.softalert.Model.Usuario;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Retrofit.UsuarioCliente.UsuarioClienteManager;

public abstract class UsuarioClienteController extends UsuarioController {

    //region API

    public static void inserirAPI(UsuarioCliente uc, InterfaceListenerAPI interfaceListenerAPI) throws Exception {
        UsuarioClienteManager usuarioClienteManager = new UsuarioClienteManager();
        usuarioClienteManager.inserirUsuario(uc, interfaceListenerAPI);
    }

    public static UsuarioCliente retornaUsuarioClienteInserirAPI(){
        return UsuarioClienteManager.getUsuarioCliente();
    }

    public static void enviarSMS(RequisicaoEnvioSMS requisicaoEnvioSMS, InterfaceListenerAPI interfaceListenerAPI) throws Exception {
        UsuarioClienteManager usuarioClienteManager = new UsuarioClienteManager();
        usuarioClienteManager.enviarSMSAutenticacao(requisicaoEnvioSMS, interfaceListenerAPI);
    }

    //endregion

    //region DAO interno

    public static void inserirDAOInterno(UsuarioCliente uc) throws Exception {
        UsuarioClienteDAO.inserir(uc);
    }

    public static UsuarioCliente retornaUsuarioClienteDAOInterno() {
        return UsuarioClienteDAO.buscarCliente();
    }

    //endregion

    public static ErroValidacaoModel validarCamposUsuarioCliente(UsuarioCliente uc) {
        return validaCamposUsuario(uc);
    }
}