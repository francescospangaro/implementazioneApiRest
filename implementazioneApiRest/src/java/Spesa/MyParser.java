package Spesa;

import java.io.IOException;
import java.util.*;
import javax.xml.parsers.*;
import Spesa.Utente;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class MyParser {

    boolean controllo = false;
    private Utente utentet;
    private Richiesta richiestat;

    public MyParser() {
        Utente utentet = new Utente();
        Richiesta richiestat = new Richiesta();
    }

    public Utente parseUtente(String filename) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        Element root, element;
        NodeList nodelist;
        Utente utente;

        // creazione dell’albero DOM dal documento XML
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(filename);
        root = document.getDocumentElement();
        nodelist = root.getElementsByTagName("utente");
        element = (Element) nodelist.item(0);
        utente = getUtente(element);
        utentet = utente;

        return utentet;
    }

    public Richiesta parseRichiesta(String filename) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        Element root, element;
        NodeList nodelist;
        Richiesta richiesta;

        // creazione dell’albero DOM dal documento XML
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(filename);
        root = document.getDocumentElement();
        nodelist = root.getElementsByTagName("richiesta");
        element = (Element) nodelist.item(0);
        element.getNextSibling();
        richiesta = getRichiesta(element);
        richiestat = richiesta;

        return richiestat;
    }

    private Utente getUtente(Element elementUtente) {
        Utente utente;
        utente = new Utente();

        String username = getTextValue(elementUtente, "username");
        utente.setUser(username);

        String nome = getTextValue(elementUtente, "nome");
        utente.setNome(nome);

        String cognome = getTextValue(elementUtente, "cognome");
        utente.setCognome(cognome);

        String password = getTextValue(elementUtente, "password");
        utente.setPassword(password);

        String codFiscale = getTextValue(elementUtente, "codiceFiscale");
        utente.setCodFiscale(codFiscale);

        String regione = getTextValue(elementUtente, "regione");
        utente.setRegione(regione);

        String via = getTextValue(elementUtente, "via");
        utente.setVia(via);

        String nCivico = getTextValue(elementUtente, "nCivico");
        utente.setnCivico(nCivico);
        return utente;
    }

    private Richiesta getRichiesta(Element elementUtente) {
        Richiesta richiesta;
        richiesta = new Richiesta();

        int rifUtente = getIntValue(elementUtente, "rifUtente");
        richiesta.setRifUtente(rifUtente);

        String oraInizio = getTextValue(elementUtente, "oraInizio");
        richiesta.setOraInizio(oraInizio);

        String oraFine = getTextValue(elementUtente, "oraFine");
        richiesta.setOraFine(oraFine);

        String durata = getTextValue(elementUtente, "durata");
        richiesta.setDurata(durata);

        return richiesta;
    }

    // restituisce il valore testuale dell’elemento figlio specificato
    private String getTextValue(Element element, String tag) {
        String value = null;
        NodeList nodelist;
        nodelist = element.getElementsByTagName(tag);
        if (nodelist != null && nodelist.getLength() > 0) {
            try {
                value = nodelist.item(0).getFirstChild().getNodeValue();
            } catch (Exception ex) {
                try {
                    value = nodelist.item(0).getNextSibling().getNodeValue();
                } catch (Exception e) {

                }
            }
            if (value == null) {
                value = "null";
            }
        }

        return value;
    }

    // restituisce il valore intero dell’elemento figlio specificato
    private int getIntValue(Element element, String tag) {
        return Integer.parseInt(getTextValue(element, tag));
    }

    // restituisce il valore numerico dell’elemento figlio specificato
    private float getFloatValue(Element element, String tag) {
        return Float.parseFloat(getTextValue(element, tag));
    }

    //restituisce il valore temporale dell'elemento figlio specificato
    private java.sql.Time getTimeValue(Element element, String tag) {
        String stringa = getTextValue(element, tag);
        DateFormat formato = new SimpleDateFormat("HH:mm:ss");
        java.sql.Time ora = null;
        try {
            ora = new java.sql.Time(formato.parse(stringa).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(MyParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ora;
    }

}
