package softalertv3.softalertv3.softalert.Controller;

import java.util.ArrayList;

import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.Endereco;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Retrofit.Endereco.EnderecoManager;

public abstract class EnderecoController {
    public static void inserirAPI(UsuarioCliente uc, InterfaceListenerAPI interfaceListenerAPI) throws Exception {
        EnderecoManager enderecoManager = new EnderecoManager();
        enderecoManager.inserirEnderecos(uc, interfaceListenerAPI);
    }

    public static void pesquisarEnderecosAPI(UsuarioCliente uc, InterfaceListenerAPI interfaceListenerAPI)
    {
        EnderecoManager enderecoManager = new EnderecoManager();
        enderecoManager.retornaEnderecos(uc, interfaceListenerAPI);
    }

    public static ArrayList<Endereco> retornaEnderecosPesquisados()
    {
        return EnderecoManager.listaEnderecos;
    }
}