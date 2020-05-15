/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spesa;

/**
 *
 * @author franc_000
 */
public class Utente {
    private int id;
    private String user;
    private String nome;
    private String cognome;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private String codFiscale;
    private String regione;
    private String via;
    private String nCivico;

    public Utente(int id, String user, String nome, String cognome, String codFiscale, String regione, String via, String nCivico) {
        this.id = id;
        this.user = user;
        this.nome = nome;
        this.cognome = cognome;
        this.codFiscale = codFiscale;
        this.regione = regione;
        this.via = via;
        this.nCivico = nCivico;
    }

    public Utente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getnCivico() {
        return nCivico;
    }

    public void setnCivico(String nCivico) {
        this.nCivico = nCivico;
    }
    
    
}
