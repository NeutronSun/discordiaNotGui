/**
 * Copyright (c) 22 Giugno anno 0, 2021, SafJNest and/or its affiliates. All rights reserved.
 * SAFJNEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 
 * 
 * 
 * 
 */

import java.util.HashMap;

/**
 * Classe che si occupa di smistare le chiavi pubbliche tra l'istanza del {@code client} {@link Client} ed il 
 * {@code ClientThreadReader} {@link ClientThreadReader}
 *  
 * @author Sanseverino Lorenzo
 * @version 1
 * @since 2021-12-2 (aaaa-mm-gg)
 */
public class KeySorter {
    /**
     * mappa contenente le chiavi pubbliche degli utenti
     * @param <String>
     * nome del client
     * @param <String>
     * chiave del client
     */
    private HashMap<String, String> keys;
    /**
     * variabile booleana che diventa true quando } tutte le chiavi saranno pronte
     * <p>Una volta che tutte le chiavi saranno state inviate dal {@link ServerThread server} 
     * il {@link ClientThread client} richiamera' il {@code metodo} {@link KeySorter#isReady isReady} e 
     * setta il flag a {@code true}.
     */
    private boolean ready;


    /**
     * costruttore di defualt
     */
    public KeySorter(){
        keys = new HashMap<String, String>();
        ready = false;
    }

    
    public synchronized void isReady(){
        ready = true;
        notifyAll();
    }

    public synchronized boolean isOkay(){
        if(keys.size() == 0)
            return false;
        else
            return true;
    }

    /**
     * aggiunge la {@code chiave pubblica} nella {@link KeySorter#keys mappa}
     * @param user
     * nome del client
     * @param key
     * chiave del client
     */
    public synchronized void setKey(String user, String key){
        keys.put(user, key);
    }

    


    public String[] getNames(){
        try {
            synchronized(this){
                while(!ready){wait();}
                return keys.keySet().toArray(new String[keys.size()]);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Restituisce la chiave del utente specificato
     * @param name
     * nome del client
     * @return
     * String
     */
    public String getKey(String name) {
        try {
            
            synchronized(this){
                while(!ready){wait();}
                String key = keys.get(name);
                keys.remove(name);
                if(keys.size() == 0){
                    ready = false;
                }
                return key;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
