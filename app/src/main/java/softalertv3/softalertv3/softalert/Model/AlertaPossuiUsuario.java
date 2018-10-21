package softalertv3.softalertv3.softalert.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

public class AlertaPossuiUsuario implements Serializable{

    @JsonIgnore
    private int id;
    private String situacaoUsuario;
    private String comentario;
    private Date dataVisualizou;

    @JsonIgnore
    private Usuario usuario;
    private int idUsuario;

    @JsonIgnore
    private Alerta alerta;
    private int idAlerta;

    public AlertaPossuiUsuario() {
    }

    public AlertaPossuiUsuario(int id, String situacaoUsuario, String comentario, Date dataVisualizou, Usuario usuario, int idUsuario, Alerta alerta, int idAlerta) {
        this.id = id;
        this.situacaoUsuario = situacaoUsuario;
        this.comentario = comentario;
        this.dataVisualizou = dataVisualizou;
        this.usuario = usuario;
        this.idUsuario = idUsuario;
        this.alerta = alerta;
        this.idAlerta = idAlerta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSituacaoUsuario() {
        return situacaoUsuario;
    }

    public void setSituacaoUsuario(String situacaoUsuario) {
        this.situacaoUsuario = situacaoUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getDataVisualizou() {
        return dataVisualizou;
    }

    public void setDataVisualizou(Date dataVisualizou) {
        this.dataVisualizou = dataVisualizou;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(int idAlerta) {
        this.idAlerta = idAlerta;
    }
}
