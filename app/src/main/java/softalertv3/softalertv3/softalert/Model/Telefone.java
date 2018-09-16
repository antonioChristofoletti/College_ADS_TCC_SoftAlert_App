package softalertv3.softalertv3.softalert.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Telefone {

    @JsonIgnore
    private int id;

    private String telefone;

    @JsonIgnore
    private String status;

    @JsonIgnore
    private Usuario usuario;

    public Telefone() {
    }

    public Telefone(int id, String telefone, String status, Usuario usuario) {
        this.id = id;
        this.telefone = telefone;
        this.status = status;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
