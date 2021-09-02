/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.util.ArrayList;

/**
 *
 * @author DAW08-TAREK
 */
public class Users {

    /**
     * Atributos privados y estaticos de la clase USers
     */
    private static ObjectContainer db;
    private static boolean userValid = false;

    /**
     * Metodo publico esatico y que no devuelve nada y recibe un objeto de la
     * clase Persona
     *
     * @param user objeto de la clase Persona
     * @throws Exception en el fallo al guardar el objeto en la base de datos
     */
    public static void savePerson(Person user) throws Exception {
        db.store(user);
    }

    /**
     * Metodo publico y estatico que devuelve una lista con todas las personas
     * que conforman la base de datos
     *
     * @return @throws Exception posible al ejecutar la query, o en el while
     */
    public static ArrayList<Person> getAllPerson() throws Exception {
        ArrayList<Person> userList = new ArrayList<>();
        ObjectSet<Person> oset;
        Person person;
        Query query = db.query();
        query.constrain(Person.class);
        oset = query.execute();
        while (oset.hasNext()) {
            person = (Person) oset.next();
            userList.add(person);
        }
        return userList;
    }

    /**
     * Metodo publico, esatico y que no devuelve nada recibe un booleano
     *
     * @param userValid
     */
    public static void setUserValid(boolean userValid) {
        Users.userValid = userValid;
    }

    /**
     * Metodo publico y esatico que devuelve un boleano en el caso de que el
     * ususario sea valido
     *
     * @return un booleano
     */
    public static boolean isUserValid() {
        return userValid;
    }

}

