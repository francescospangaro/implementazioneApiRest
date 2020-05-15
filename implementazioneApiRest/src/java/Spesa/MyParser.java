package Spesa;

import java.io.IOException;
import java.util.*;
import javax.xml.parsers.*;
import Spesa.Utente;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class MyParser {

    boolean controllo = false;
    private Utente utentet;

    public MyParser() {
        Utente utentet = new Utente();
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
        if (nodelist != null) {
            element = (Element) nodelist.item(0);
            element.getNextSibling();
            utente = getUtente(element);
            utentet = utente;

        }
        return utentet;
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

}
