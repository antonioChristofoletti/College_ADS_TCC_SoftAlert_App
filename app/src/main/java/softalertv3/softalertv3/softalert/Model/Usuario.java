package softalertv3.softalertv3.softalert.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Date;

public abstract class Usuario {

    @JsonIgnore
    private int idUsuario;
    
    private String nome;
    
    private String status;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date dataNascimento;

    private String cpf;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArrayList<Telefone> listaTelefones;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArrayList<Endereco> listaEnderecos;

    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public ArrayList<Telefone> getListaTelefones() {
        return listaTelefones;
    }

    public void setListaTelefones(ArrayList<Telefone> listaTelefones) {
        this.listaTelefones = listaTelefones;
    }

    public ArrayList<Endereco> getListaEnderecos() {
        return listaEnderecos;
    }

    public void setListaEnderecos(ArrayList<Endereco> listaEnderecos) {
        this.listaEnderecos = listaEnderecos;
    }
}