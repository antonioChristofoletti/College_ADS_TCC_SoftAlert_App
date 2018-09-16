package softalertv3.softalertv3.softalert.Model;

public class ErroValidacaoModel {
    String descricaoErro;
    String campoErro;

    public ErroValidacaoModel() {
    }

    public ErroValidacaoModel(String descricaoErro, String campoErro) {
        this.descricaoErro = descricaoErro;
        this.campoErro = campoErro;
    }

    public String getDescricaoErro() {
        return descricaoErro;
    }

    public void setDescricaoErro(String descricaoErro) {
        this.descricaoErro = descricaoErro;
    }

    public String getCampoErro() {
        return campoErro;
    }

    public void setCampoErro(String campoErro) {
        this.campoErro = campoErro;
    }
}