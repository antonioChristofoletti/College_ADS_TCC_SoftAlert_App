package softalertv3.softalertv3.softalert.Controller;

import softalertv3.softalertv3.softalert.Model.AlertaPossuiUsuario;
import softalertv3.softalertv3.softalert.Model.ErroValidacaoModel;

public abstract class AlertaPossuiClienteController {

    public static ErroValidacaoModel validarCampos(AlertaPossuiUsuario alertaPossuiUsuario) {

        if(alertaPossuiUsuario.getSituacaoUsuario().contains("*"))
                return new ErroValidacaoModel("A situação do usuário é inválido", "situacaoUsuario");

        return null;
    }
}