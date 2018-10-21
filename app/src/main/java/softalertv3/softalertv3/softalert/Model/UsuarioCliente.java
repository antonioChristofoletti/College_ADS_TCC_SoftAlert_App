package softalertv3.softalertv3.softalert.Model;

import java.io.Serializable;

public class UsuarioCliente extends Usuario implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}