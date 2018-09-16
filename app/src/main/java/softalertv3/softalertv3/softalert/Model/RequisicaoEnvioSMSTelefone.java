package softalertv3.softalertv3.softalert.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequisicaoEnvioSMSTelefone {

    @JsonIgnore
    private int id;

    private String telefone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String mensagemErro;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private char mensagemProcessada;

    @JsonIgnore
    private RequisicaoEnvioSMS requisicaoEnvioSMS;

    public RequisicaoEnvioSMSTelefone() {
    }

    public RequisicaoEnvioSMSTelefone(int id, String telefone, String mensagemErro, char mensagemProcessada, RequisicaoEnvioSMS requisicaoEnvioSMS) {
        this.id = id;
        this.telefone = telefone;
        this.mensagemErro = mensagemErro;
        this.mensagemProcessada = mensagemProcessada;
        this.requisicaoEnvioSMS = requisicaoEnvioSMS;
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

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public char getMensagemProcessada() {
        return mensagemProcessada;
    }

    public void setMensagemProcessada(char mensagemProcessada) {
        this.mensagemProcessada = mensagemProcessada;
    }

    public RequisicaoEnvioSMS getRequisicaoEnvioSMS() {
        return requisicaoEnvioSMS;
    }

    public void setRequisicaoEnvioSMS(RequisicaoEnvioSMS requisicaoEnvioSMS) {
        this.requisicaoEnvioSMS = requisicaoEnvioSMS;
    }
}