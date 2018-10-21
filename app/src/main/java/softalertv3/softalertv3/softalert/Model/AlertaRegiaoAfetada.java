package softalertv3.softalertv3.softalert.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class AlertaRegiaoAfetada implements Serializable{

    @JsonIgnore
    private int id;

    private double latitude;
    private double longitude;
    private String titulo;
    private String subTitulo;
    private double abrangencia;
    private String status;
    private String corCirculo;
    private String corBordaCirculo;

    @JsonIgnore
    private Alerta alerta;
    private int idAlerta;

    public AlertaRegiaoAfetada() {
    }

    public AlertaRegiaoAfetada(int id, double latitude, double longitude, String titulo, String subTitulo, double abrangencia, String status, String corCirculo, String corBordaCirculo, Alerta alerta, int idAlerta) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.abrangencia = abrangencia;
        this.status = status;
        this.corCirculo = corCirculo;
        this.corBordaCirculo = corBordaCirculo;
        this.alerta = alerta;
        this.idAlerta = idAlerta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    public double getAbrangencia() {
        return abrangencia;
    }

    public void setAbrangencia(double abrangencia) {
        this.abrangencia = abrangencia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }

    public int getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(int idAlerta) {
        this.idAlerta = idAlerta;
    }

    public String getCorCirculo() {
        return corCirculo;
    }

    public void setCorCirculo(String corCirculo) {
        this.corCirculo = corCirculo;
    }

    public String getCorBordaCirculo() {
        return corBordaCirculo;
    }

    public void setCorBordaCirculo(String corBordaCirculo) {
        this.corBordaCirculo = corBordaCirculo;
    }
}
