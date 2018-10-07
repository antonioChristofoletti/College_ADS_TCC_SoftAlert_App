package softalertv3.softalertv3.softalert.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Endereco {

    @JsonIgnore
    private int id;

    private String pais;
    private String estado;
    private String cidade;
    private String endereco;

    private double latitude;
    private double longitude;

    private String nomeLocal;

    private String status;

    private Usuario usuario;

    public Endereco() {
    }

    public Endereco(int id, String pais, String estado, String cidade, String endereco, double latitude, double longitude, String nomeLocal, String status, Usuario usuario) {
        this.id = id;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.endereco = endereco;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nomeLocal = nomeLocal;
        this.status = status;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}