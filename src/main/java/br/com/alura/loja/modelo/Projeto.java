package br.com.alura.loja.modelo;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Projeto {

    long id;
    private String nome;
    int anoDeInicio;

    public Projeto() {
        super();
    }

    public Projeto(long id, String nome, int anoDeInicio) {
        this.id = id;
        this.nome = nome;
        this.anoDeInicio = anoDeInicio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnoDeInicio() {
        return anoDeInicio;
    }

    public void setAnoDeInicio(int anoDeInicio) {
        this.anoDeInicio = anoDeInicio;
    }

    public String toXml() {
        return new XStream().toXML(this);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
