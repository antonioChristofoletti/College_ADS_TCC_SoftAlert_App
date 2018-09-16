package softalertv3.softalertv3.softalert.Controller;

import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.ErroValidacaoModel;
import softalertv3.softalertv3.softalert.Model.RequisicaoEnvioSMS;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Retrofit.UsuarioCliente.UsuarioClienteManager;

public abstract class UsuarioClienteController extends UsuarioController {

    public static void inserir(UsuarioCliente uc, InterfaceListenerAPI interfaceListenerAPI) throws Exception {
        UsuarioClienteManager usuarioClienteManager = new UsuarioClienteManager();
        usuarioClienteManager.inserirUsuario(uc, interfaceListenerAPI);
    }

    public static void enviarSMS(RequisicaoEnvioSMS requisicaoEnvioSMS, InterfaceListenerAPI interfaceListenerAPI) throws Exception {
        UsuarioClienteManager usuarioClienteManager = new UsuarioClienteManager();
        usuarioClienteManager.enviarSMSAutenticacao(requisicaoEnvioSMS, interfaceListenerAPI);
    }

    public static ErroValidacaoModel validarCamposUsuarioCliente(UsuarioCliente uc)
    {
        return validaCamposUsuario(uc);
    }



    /*
    public static UsuarioCliente editar(UsuarioCliente uc) throws BancoDeDadosException, GenericException, CampoIncorretoException {

        validaCamposUsuario(uc);

        return UsuarioClienteDAO.editar(uc);
    }

    public static ArrayList<UsuarioCliente> retornaTodosUsuariosClientes() throws BancoDeDadosException, GenericException {
        return UsuarioClienteDAO.getTodosUsuariosClientes();
    }

    public static UsuarioCliente retornaUsuarioCliente(int id) throws BancoDeDadosException, GenericException {
        return UsuarioClienteDAO.getUsuarioCliente(id);
    }*/
    
}
