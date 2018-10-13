package softalertv3.softalertv3.softalert.Controller;

import softalertv3.softalertv3.softalert.Model.ErroValidacaoModel;
import softalertv3.softalertv3.softalert.Model.Telefone;
import softalertv3.softalertv3.softalert.Model.Usuario;
import softalertv3.softalertv3.softalert.Uteis.Geral;

import java.util.Date;

public abstract class UsuarioController {

    protected static ErroValidacaoModel validaCamposUsuario(Usuario u) {



        int i=0;
        for (Telefone e : u.getListaTelefones()) {
            if (e.getTelefone().length() < 12) {
                if (u.getListaTelefones().size() == 1) {
                    return new ErroValidacaoModel("O Telefone do usuário é inválido","telefone");
                } else {
                    return new ErroValidacaoModel("O Telefone '"+(i+1)+"' do usuário é inválido","telefone");
                }
            }
            i++;
        }

        if(Geral.isCampoVazio(u.getNome())){
            return new ErroValidacaoModel("O nome do usuário é inválido", "nome");
        }

        if (u.getCpf() != null && !u.getCpf().equals("")) {
            if (!Geral.isCPF(u.getCpf()) && !Geral.isCampoVazio(u.getCpf())) {
                return new ErroValidacaoModel("O CPF do usuário é inválido", "cpf");
            }
        }

        if (u.getDataNascimento().after(new Date())) {
            return new ErroValidacaoModel("A data de nascimento é inválida. A mesma é maior que a data atual", "dataNascimento");
        }

        if (u.getDataNascimento().before(Geral.geraData("dd/MM/yyyy", "01/01/1890"))) {
            return new ErroValidacaoModel("A data de nascimento é inválida", "dataNascimento");
        }

        return null;
    }
}