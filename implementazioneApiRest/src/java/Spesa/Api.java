/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spesa;

import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * REST Web Service
 *
 * @author franc_000
 */
@ApplicationPath("/")
@Path("spesa")
public class Api {

    final private String driver = "com.mysql.jdbc.Driver";
    final private String dbms_url = "jdbc:mysql://localhost/";
    final private String database = "db_spesa";
    final private String user = "root";
    final private String password = "";
    private Connection spesaDatabase;
    private boolean connected;

    @Context
    private UriInfo context;

    public Api() {
        super();
    }

    public void init() {
        String url = dbms_url + database;
        try {
            Class.forName(driver);
            spesaDatabase = DriverManager.getConnection(url, user, password);
            connected = true;
        } catch (SQLException e) {
            connected = false;
        } catch (ClassNotFoundException e) {
            connected = false;
        }
    }

    public void destroy() {
        try {
            spesaDatabase.close();
        } catch (SQLException e) {
        }
    }

    /*
    visualizza i dati di un utente da username fornito nella path in formato xml come definito nella progettazione api
     */
    @GET
    @Path("utenteUsernameXML/{username}")
    @Produces(MediaType.TEXT_XML)
    public String getUtenteXMLDaUsername(@PathParam("username") String user) {
        init();
        String output = "";
        if (!connected) {
            return "<errorMessage>400</errorMessage>";
        } else {
            try {
                Utente utente = new Utente();
                String sql = "SELECT idUtente, nome, cognome, codiceFiscale, regione, via, nCivico FROM utente where username ='" + user + "'";
                System.out.println(user);
                Statement statement = spesaDatabase.createStatement();
                ResultSet result = statement.executeQuery(sql);

                result.next();
                utente.setId(result.getInt(1));
                utente.setNome(result.getString(2));
                utente.setCognome(result.getString(3));
                utente.setCodFiscale(result.getString(4));
                utente.setRegione(result.getString(5));
                utente.setVia(result.getString(6));
                utente.setnCivico(result.getString(7));

                result.close();
                statement.close();

                if (!utente.getNome().equals(null)) {
                    output = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
                    output = output + "<utente>\n";
                    output = output + "<idUtente>" + utente.getId() + "</idUtente>\n";
                    output = output + "<nome>" + utente.getNome() + "</nome>\n";
                    output = output + "<cognome>" + utente.getCognome() + "</cognome>\n";
                    output = output + "<codiceFiscale>" + utente.getCodFiscale() + "</codiceFiscale>\n";
                    output = output + "<regione>" + utente.getRegione() + "</regione>\n";
                    output = output + "<via>" + utente.getVia() + "</via>\n";
                    output = output + "<nCivico>" + utente.getnCivico() + "</nCivico>\n";
                    output = output + "</utente>";

                } else {
                    destroy();
                    return "<errorMessage>404</errorMessage>";
                }

            } catch (SQLException ex) {
                Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
                destroy();
                return "<errorMessage>500</errorMessage>";
            }
            destroy();
            return output;
        }
    }

    /*
    visualizza i dati di un utente da id fornito nella path in formato xml come definito nella progettazione api
     */
    @GET
    @Path("utenteIdXML/{id}")
    @Produces(MediaType.TEXT_XML)
    public String getUtenteXMLDaId(@PathParam("id") String id) {
        init();
        String output = "";
        if (!connected) {
            return "<errorMessage>400</errorMessage>";
        } else {
            try {
                Utente utente = new Utente();
                String sql = "SELECT username, nome, cognome, codiceFiscale, regione, via, nCivico FROM utente where idUtente ='" + id + "'";
                Statement statement = spesaDatabase.createStatement();
                ResultSet result = statement.executeQuery(sql);

                result.next();
                utente.setUser(result.getString(1));
                utente.setNome(result.getString(2));
                utente.setCognome(result.getString(3));
                utente.setCodFiscale(result.getString(4));
                utente.setRegione(result.getString(5));
                utente.setVia(result.getString(6));
                utente.setnCivico(result.getString(7));

                result.close();
                statement.close();

                if (!utente.getNome().equals(null)) {
                    output = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
                    output = output + "<utente>\n";
                    output = output + "<username>" + utente.getUser() + "</username>\n";
                    output = output + "<nome>" + utente.getNome() + "</nome>\n";
                    output = output + "<cognome>" + utente.getCognome() + "</cognome>\n";
                    output = output + "<codiceFiscale>" + utente.getCodFiscale() + "</codiceFiscale>\n";
                    output = output + "<regione>" + utente.getRegione() + "</regione>\n";
                    output = output + "<via>" + utente.getVia() + "</via>\n";
                    output = output + "<nCivico>" + utente.getnCivico() + "</nCivico>\n";
                    output = output + "</utente>";

                } else {
                    destroy();
                    return "<errorMessage>404</errorMessage>";
                }

            } catch (SQLException ex) {
                Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
                destroy();
                return "<errorMessage>500</errorMessage>";
            }
            destroy();
            return output;
        }
    }

    /*
    visualizza i dati di un utente da username fornito nella path in formato json come definito nella progettazione api
     */
    @GET
    @Path("utenteUsernameJSON/{username}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUtenteJSONDaUsername(@PathParam("username") String user) {
        init();
        String output = "";
        if (!connected) {
            return "<errorMessage>400</errorMessage>";
        } else {
            try {
                Utente utente = new Utente();
                String sql = "SELECT idUtente, nome, cognome, codiceFiscale, regione, via, nCivico FROM utente where username ='" + user + "'";
                System.out.println(user);
                Statement statement = spesaDatabase.createStatement();
                ResultSet result = statement.executeQuery(sql);

                result.next();
                utente.setId(result.getInt(1));
                utente.setNome(result.getString(2));
                utente.setCognome(result.getString(3));
                utente.setCodFiscale(result.getString(4));
                utente.setRegione(result.getString(5));
                utente.setVia(result.getString(6));
                utente.setnCivico(result.getString(7));

                result.close();
                statement.close();

                if (!utente.getNome().equals(null)) {
                    output = "{\"utente\":{\n";
                    output = output + "\"idUtente\":\"" + utente.getId() + "\",\n";
                    output = output + "\"nome\":\"" + utente.getNome() + "\",\n";
                    output = output + "\"cognome\":\"" + utente.getCognome() + "\",\n";
                    output = output + "\"codiceFiscale\":\"" + utente.getCodFiscale() + "\",\n";
                    output = output + "\"regione\":\"" + utente.getRegione() + "\",\n";
                    output = output + "\"via\":\"" + utente.getVia() + "\",\n";
                    output = output + "\"nCivico\":\"" + utente.getnCivico() + "\"\n";
                    output = output + "}\n}";

                } else {
                    destroy();
                    return "<errorMessage>404</errorMessage>";
                }

            } catch (SQLException ex) {
                Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
                destroy();
                return "<errorMessage>500</errorMessage>";
            }
            destroy();
            return output;
        }
    }

    /*
    visualizza i dati di un utente da id fornito nella path in formato json come definito nella progettazione api
     */
    @GET
    @Path("utenteIdJSON/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUtenteJSONDaId(@PathParam("id") String id) {
        init();
        String output = "";
        if (!connected) {
            return "<errorMessage>400</errorMessage>";
        } else {
            try {
                Utente utente = new Utente();
                String sql = "SELECT username, nome, cognome, codiceFiscale, regione, via, nCivico FROM utente where idUtente ='" + id + "'";
                System.out.println(user);
                Statement statement = spesaDatabase.createStatement();
                ResultSet result = statement.executeQuery(sql);

                result.next();
                utente.setUser(result.getString(1));
                utente.setNome(result.getString(2));
                utente.setCognome(result.getString(3));
                utente.setCodFiscale(result.getString(4));
                utente.setRegione(result.getString(5));
                utente.setVia(result.getString(6));
                utente.setnCivico(result.getString(7));

                result.close();
                statement.close();

                if (!utente.getNome().equals(null)) {
                    output = "{\"utente\":{\n";
                    output = output + "\"username\":\"" + utente.getUser() + "\",\n";
                    output = output + "\"nome\":\"" + utente.getNome() + "\",\n";
                    output = output + "\"cognome\":\"" + utente.getCognome() + "\",\n";
                    output = output + "\"codiceFiscale\":\"" + utente.getCodFiscale() + "\",\n";
                    output = output + "\"regione\":\"" + utente.getRegione() + "\",\n";
                    output = output + "\"via\":\"" + utente.getVia() + "\",\n";
                    output = output + "\"nCivico\":\"" + utente.getnCivico() + "\"\n";
                    output = output + "}\n}";

                } else {
                    destroy();
                    return "<errorMessage>404</errorMessage>";
                }

            } catch (SQLException ex) {
                Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
                destroy();
                return "<errorMessage>500</errorMessage>";
            }
            destroy();
            return output;
        }
    }

    /*
    visualizza i dati di una richiesta da id fornito nella path in formato xml come definito nella progettazione api
     */
    @GET
    @Path("richiestaXML/{id}")
    @Produces(MediaType.TEXT_XML)
    public String getRichiestaXMLDaId(@PathParam("id") String id) {
        init();
        String output = "";
        if (!connected) {
            return "<errorMessage>400</errorMessage>";
        } else {
            try {
                Richiesta richiesta = new Richiesta();
                String sql = "SELECT rifUtente, oraInizioConsegna, oraFineConsegna, durataRichiesta FROM richiesta where idRichiesta ='" + id + "'";
                Statement statement = spesaDatabase.createStatement();
                ResultSet result = statement.executeQuery(sql);

                result.next();
                richiesta.setRifUtente(result.getInt(1));
                richiesta.setOraInizio(result.getString(2));
                richiesta.setOraFine(result.getString(3));
                richiesta.setDurata(result.getString(4));

                result.close();
                statement.close();

                if (richiesta.getRifUtente() != 0) {
                    output = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
                    output = output + "<richiesta>\n";
                    output = output + "<rifUtente>" + richiesta.getRifUtente() + "</rifUtente>\n";
                    output = output + "<oraInizio>" + richiesta.getOraInizio() + "</oraInizio>\n";
                    output = output + "<oraFine>" + richiesta.getOraFine() + "</oraFine>\n";
                    output = output + "<durata>" + richiesta.getDurata() + "</durata>\n";
                    output = output + "</richiesta>";

                } else {
                    destroy();
                    return "<errorMessage>404</errorMessage>";
                }

            } catch (SQLException ex) {
                Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
                destroy();
                return "<errorMessage>500</errorMessage>";
            }
            destroy();
            return output;
        }
    }

    /*
    visualizza i dati di una richiesta da id fornito nella path in formato json come definito nella progettazione api
     */
    @GET
    @Path("richiestaJSON/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRichiestaJSONDaId(@PathParam("id") String id) {
        init();
        String output = "";
        if (!connected) {
            return "<errorMessage>400</errorMessage>";
        } else {
            try {
                Richiesta richiesta = new Richiesta();
                String sql = "SELECT rifUtente, oraInizioConsegna, oraFineConsegna, durataRichiesta FROM richiesta where idRichiesta ='" + id + "'";
                Statement statement = spesaDatabase.createStatement();
                ResultSet result = statement.executeQuery(sql);

                result.next();
                richiesta.setRifUtente(result.getInt(1));
                richiesta.setOraInizio(result.getString(2));
                richiesta.setOraFine(result.getString(3));
                richiesta.setDurata(result.getString(4));

                result.close();
                statement.close();

                if (richiesta.getRifUtente() != -1) {
                    output = "{\"richiesta\":{\n";
                    output = output + "\"rifUtente\":\"" + richiesta.getRifUtente() + "\",\n";
                    output = output + "\"oraInizio\":\"" + richiesta.getOraInizio() + "\",\n";
                    output = output + "\"oraFine\":\"" + richiesta.getOraFine() + "\",\n";
                    output = output + "\"durata\":\"" + richiesta.getDurata() + "\"\n";
                    output = output + "}\n}";

                } else {
                    destroy();
                    return "<errorMessage>404</errorMessage>";
                }

            } catch (SQLException ex) {
                Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
                destroy();
                return "<errorMessage>500</errorMessage>";
            }
            destroy();
            return output;
        }
    }

    /*
    inserisce un utente nel database, dati forniti in formato xml
     */
    @POST
    @Path("utenteXML")
    @Consumes(MediaType.TEXT_XML)
    public String postUtenteXML(String content) {
        init();
        try {
            BufferedWriter writer;
            writer = new BufferedWriter(new FileWriter("entry.xml"));
            writer.write(content);
            writer.flush();
            writer.close();
            Utente utente = new Utente();

            /*try {
                MyValidator.validate("entry.xml", xsdFile);
            } catch (SAXException ex) {
                Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
                return "<errorMessage>400 Malformed XML</errorMessage>";
            }*/
            MyParser parse = new MyParser();
            utente = parse.parseUtente("entry.xml");
            if (!connected) {
                return "<errorMessage>400</errorMessage>";
            }
            String sql = "INSERT INTO utente(username, nome, cognome, password, codiceFiscale, regione, via, nCivico) VALUES('" + utente.getUser() + "', '" + utente.getNome() + "', '" + utente.getCognome() + "', '" + utente.getPassword() + "', '" + utente.getCodFiscale() + "', '" + utente.getRegione() + "', '" + utente.getVia() + "', '" + utente.getnCivico() + "')";
            Statement statement = spesaDatabase.createStatement();

            if (statement.executeUpdate(sql) <= 0) {
                statement.close();
                return "<errorMessage>403</errorMessage>";
            }

            statement.close();
            destroy();
            return "<message>Inserimento avvenuto correttamente</message>";

        } catch (IOException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "<errorMessage>400</errorMessage>";
    }

    /*
    inserisce un utente nel database, dati forniti in formato json
     */
    @POST
    @Path("utenteJSON")
    @Consumes(MediaType.APPLICATION_JSON)
    public String postUtenteJSON(String content) {
        init();
        try {
            JSONObject obj = new JSONObject(content);
            Utente utente = new Utente();
            utente.setUser(obj.getJSONObject("utente").getString("username"));
            utente.setNome(obj.getJSONObject("utente").getString("nome"));
            utente.setCognome(obj.getJSONObject("utente").getString("cognome"));
            utente.setPassword(obj.getJSONObject("utente").getString("password"));
            utente.setCodFiscale(obj.getJSONObject("utente").getString("codiceFiscale"));
            utente.setRegione(obj.getJSONObject("utente").getString("regione"));
            utente.setVia(obj.getJSONObject("utente").getString("via"));
            utente.setnCivico(obj.getJSONObject("utente").getString("nCivico"));

            if (!connected) {
                return "<errorMessage>400</errorMessage>";
            }
            String sql = "INSERT INTO utente(username, nome, cognome, password, codiceFiscale, regione, via, nCivico) VALUES('" + utente.getUser() + "', '" + utente.getNome() + "', '" + utente.getCognome() + "', '" + utente.getPassword() + "', '" + utente.getCodFiscale() + "', '" + utente.getRegione() + "', '" + utente.getVia() + "', '" + utente.getnCivico() + "')";
            Statement statement = spesaDatabase.createStatement();

            if (statement.executeUpdate(sql) <= 0) {
                statement.close();
                return "<errorMessage>403</errorMessage>";
            }

            statement.close();
            destroy();
            return "<message>Inserimento avvenuto correttamente</message>";

        } catch (SQLException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "<errorMessage>400</errorMessage>";
    }

    /*
    inserisce una richiesta nel database, dati forniti in formato xml
     */
    @POST
    @Path("richiestaXML")
    @Consumes(MediaType.TEXT_XML)
    public String postRichiestaXML(String content) {
        init();
        try {
            String xsdFile = "\\xml\\richiesta.xsd";
            BufferedWriter writer;
            writer = new BufferedWriter(new FileWriter("entry.xml"));
            writer.write(content);
            writer.flush();
            writer.close();
            Richiesta richiesta = new Richiesta();

            /*try {
                MyValidator.validate("entry.xml", xsdFile);
            } catch (SAXException ex) {
                Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
                return "<errorMessage>400 Malformed XML</errorMessage>";
            }*/
            MyParser parse = new MyParser();
            richiesta = parse.parseRichiesta("entry.xml");
            if (!connected) {
                return "<errorMessage>400</errorMessage>";
            }
            String sql = "INSERT INTO richiesta(rifUtente, oraInizioConsegna, oraFineConsegna, durataRichiesta) VALUES(" + richiesta.getRifUtente() + ", '" + richiesta.getOraInizio() + "', '" + richiesta.getOraFine() + "', '" + richiesta.getDurata() + "')";
            Statement statement = spesaDatabase.createStatement();

            if (statement.executeUpdate(sql) <= 0) {
                statement.close();
                return "<errorMessage>403</errorMessage>";
            }

            statement.close();
            destroy();
            return "<message>Inserimento avvenuto correttamente</message>";

        } catch (IOException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "<errorMessage>400</errorMessage>";
    }

    /*
    inserisce una richiesta nel database, dati forniti in formato json
     */
    @POST
    @Path("richiestaJSON")
    @Consumes(MediaType.APPLICATION_JSON)
    public String postRichiestaJSON(String content) {
        init();
        try {
            JSONObject obj = new JSONObject(content);
            Richiesta richiesta = new Richiesta();
            richiesta.setRifUtente(obj.getJSONObject("richiesta").getInt("rifUtente"));
            richiesta.setOraInizio(obj.getJSONObject("richiesta").getString("oraInizio"));
            richiesta.setOraFine((obj.getJSONObject("richiesta").getString("oraFine")));
            richiesta.setDurata((obj.getJSONObject("richiesta").getString("durata")));

            if (!connected) {
                return "<errorMessage>400</errorMessage>";
            }
            String sql = "INSERT INTO richiesta(rifUtente, oraInizioConsegna, oraFineConsegna, durataRichiesta) VALUES(" + richiesta.getRifUtente() + ", '" + richiesta.getOraInizio() + "', '" + richiesta.getOraFine() + "', '" + richiesta.getDurata() + "')";
            Statement statement = spesaDatabase.createStatement();

            if (statement.executeUpdate(sql) <= 0) {
                statement.close();
                return "<errorMessage>403</errorMessage>";
            }

            statement.close();
            destroy();
            return "<message>Inserimento avvenuto correttamente</message>";

        } catch (SQLException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "<errorMessage>400</errorMessage>";
    }

    /*
    cancella una lista dal database, basandosi sull'id fornito nella api
     */
    @DELETE
    @Path("lista")
    public String deleteProdotto(@QueryParam("id") int id) {
        init();

        if (!connected) {
            return "<errorMessage>400</errorMessage>";
        }
        try {
            String sql = "DELETE FROM lista WHERE idLista='" + id + "'";
            Statement statement = spesaDatabase.createStatement();

            if (statement.executeUpdate(sql) <= 0) {
                statement.close();
                return "<errorMessage>403</errorMessage>";
            }

            statement.close();
            destroy();
            return "<message>Eliminazione avvenuta correttamente</message>";
        } catch (SQLException ex) {
            destroy();
            return "<errorMessage>500</errorMessage>";
        }
    }

    public java.sql.Time getTime(String stringa) {
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
