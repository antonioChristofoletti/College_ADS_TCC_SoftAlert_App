package softalertv3.softalertv3.softalert.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NivelAlerta {

    private int id;
    private String descricao;
    private String cor;
    private String status;

    public NivelAlerta() {
    }

    public NivelAlerta(int id, String descricao, String cor, String status) {
        this.id = id;
        this.descricao = descricao;
        this.cor = cor;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}