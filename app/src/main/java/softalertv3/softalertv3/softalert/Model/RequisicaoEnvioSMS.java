package softalertv3.softalertv3.softalert.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class RequisicaoEnvioSMS {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date horaRequisicao;

    private String mensagem;

    private ArrayList<RequisicaoEnvioSMSTelefone> telefones;

    public RequisicaoEnvioSMS() {
        telefones = new ArrayList<>();
    }

    public RequisicaoEnvioSMS(int id, Date horaRequisicao, String mensagem, ArrayList<RequisicaoEnvioSMSTelefone> telefones) {
        this.id = id;
        this.horaRequisicao = horaRequisicao;
        this.mensagem = mensagem;
        this.telefones = telefones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getHoraRequisicao() {
        return horaRequisicao;
    }

    public void setHoraRequisicao(Date horaRequisicao) {
        this.horaRequisicao = horaRequisicao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public ArrayList<RequisicaoEnvioSMSTelefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(ArrayList<RequisicaoEnvioSMSTelefone> telefones) {
        this.telefones = telefones;
    }
}

