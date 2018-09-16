package softalertv3.softalertv3.softalert.Controller;


public abstract class UsuarioAdministradorController extends UsuarioController {

  /*  public static UsuarioAdministrador inserir(UsuarioAdministrador ua) throws BancoDeDadosException, CampoIncorretoException, GenericException {

        validaCamposUsuarioAdministrador(ua, true);

        return UsuarioAdministradorDAO.inserir(ua);
    }

    public static UsuarioAdministrador editar(UsuarioAdministrador ua) throws BancoDeDadosException, GenericException, CampoIncorretoException {

        validaCamposUsuarioAdministrador(ua, false);

        return UsuarioAdministradorDAO.editar(ua);
    }

    public static ArrayList<UsuarioAdministrador> retornaTodosUsuariosAdministradores() throws BancoDeDadosException, GenericException {
        return UsuarioAdministradorDAO.getTodosUsuariosAdministradores();
    }

    public static UsuarioAdministrador retornaUsuarioAdministrador(int id) throws BancoDeDadosException, GenericException {
        return UsuarioAdministradorDAO.getUsuarioAdministrador(id);
    }

    public static void validaCamposUsuarioAdministrador(UsuarioAdministrador ua, Boolean validaSenha) throws CampoIncorretoException, GenericException, BancoDeDadosException {

        ua.setIdUsuario(UsuarioDAO.getIdUsuario(ua.getId()));

        validaCamposUsuario(ua);

        if (ua.getSenha() == null) {
            ua.setSenha("");
        }

        if (validaSenha || !ua.getSenha().equals("")) {
            if (ua.getSenha().length() < 6) {
                throw new GenericException("A senha deve possuir no mínimo 6 caracteres");
            }
        }

        if (UsuarioAdministradorDAO.nomeUsuarioExiste(ua.getUsuario(), ua.getId())) {
            throw new GenericException("O nome do usuário já existe no sistema");
        }
    }

    public static Boolean autenticarUsuario(UsuarioAdministrador ua) throws BancoDeDadosException, GenericException {
        if (ua.getSenha() == null || ua.getSenha().equals("")) {
            throw new GenericException("A senha informada não é reconhecida como válida para realizar a autenticação");
        }

        if (ua.getUsuario() == null || ua.getUsuario().equals("")) {
            throw new GenericException("O usuário informado não é reconhecido como válido para realizar a autenticação");
        }

        return UsuarioAdministradorDAO.autenticarUsuario(ua);
    }*/
}