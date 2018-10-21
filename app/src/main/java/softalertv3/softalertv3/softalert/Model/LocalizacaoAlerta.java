package softalertv3.softalertv3.softalert.Model;

import java.io.Serializable;

public class LocalizacaoAlerta implements Serializable {
    private int id;
    private String tipo;
    private String descricao;

    public LocalizacaoAlerta() {
    }

    public LocalizacaoAlerta(int id, String tipo, String descricao) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}