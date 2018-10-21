package softalertv3.softalertv3.softalert.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Alerta implements Serializable {
    private int id;
    private String evento;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm")
    private Date dataInsercao;
    private String descricao;
    private String status;

    private NivelAlerta nivelAlerta;
    private int idNivelAlerta;

    private UsuarioAdministrador administrador;
    private int idUsuarioAdministrador;

    private ArrayList<LocalizacaoAlerta> listaLocalizacoesAlerta;
    private ArrayList<UsuarioCliente> listaUsuarioCliente;
    private ArrayList<AlertaPossuiUsuario> listaAlertaPossuiUsuarios;



    @JsonIgnore
    private int idNotification;

    @JsonIgnore
    private boolean subiuNotificacao;

    public Alerta() {
    }

    public Alerta(int id, String evento, Date dataInsercao, String descricao, String status, NivelAlerta nivelAlerta, int idNivelAlerta, UsuarioAdministrador administrador, int idUsuarioAdministrador, ArrayList<LocalizacaoAlerta> listaLocalizacoesAlerta, ArrayList<UsuarioCliente> listaUsuarioCliente, ArrayList<AlertaPossuiUsuario> listaAlertaPossuiUsuarios, int idNotification, boolean subiuNotificacao) {
        this.id = id;
        this.evento = evento;
        this.dataInsercao = dataInsercao;
        this.descricao = descricao;
        this.status = status;
        this.nivelAlerta = nivelAlerta;
        this.idNivelAlerta = idNivelAlerta;
        this.administrador = administrador;
        this.idUsuarioAdministrador = idUsuarioAdministrador;
        this.listaLocalizacoesAlerta = listaLocalizacoesAlerta;
        this.listaUsuarioCliente = listaUsuarioCliente;
        this.listaAlertaPossuiUsuarios = listaAlertaPossuiUsuarios;
        this.idNotification = idNotification;
        this.subiuNotificacao = subiuNotificacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Date getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(Date dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NivelAlerta getNivelAlerta() {
        return nivelAlerta;
    }

    public void setNivelAlerta(NivelAlerta nivelAlerta) {
        this.nivelAlerta = nivelAlerta;
    }

    public UsuarioAdministrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(UsuarioAdministrador administrador) {
        this.administrador = administrador;
    }

    public ArrayList<LocalizacaoAlerta> getListaLocalizacoesAlerta() {
        return listaLocalizacoesAlerta;
    }

    public void setListaLocalizacoesAlerta(ArrayList<LocalizacaoAlerta> listaLocalizacoesAlerta) {
        this.listaLocalizacoesAlerta = listaLocalizacoesAlerta;
    }

    public ArrayList<UsuarioCliente> getListaUsuarioCliente() {
        return listaUsuarioCliente;
    }

    public void setListaUsuarioCliente(ArrayList<UsuarioCliente> listaUsuarioCliente) {
        this.listaUsuarioCliente = listaUsuarioCliente;
    }

    public int getIdNivelAlerta() {
        return idNivelAlerta;
    }

    public void setIdNivelAlerta(int idNivelAlerta) {
        this.idNivelAlerta = idNivelAlerta;
    }

    public int getIdUsuarioAdministrador() {
        return idUsuarioAdministrador;
    }

    public void setIdUsuarioAdministrador(int idUsuarioAdministrador) {
        this.idUsuarioAdministrador = idUsuarioAdministrador;
    }

    public int getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public boolean isSubiuNotificacao() {
        return subiuNotificacao;
    }

    public void setSubiuNotificacao(boolean subiuNotificacao) {
        this.subiuNotificacao = subiuNotificacao;
    }

    public ArrayList<AlertaPossuiUsuario> getListaAlertaPossuiUsuarios() {
        return listaAlertaPossuiUsuarios;
    }

    public void setListaAlertaPossuiUsuarios(ArrayList<AlertaPossuiUsuario> listaAlertaPossuiUsuarios) {
        this.listaAlertaPossuiUsuarios = listaAlertaPossuiUsuarios;
    }
}