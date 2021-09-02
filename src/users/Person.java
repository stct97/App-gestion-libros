/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

import java.io.Serializable;

/**
 *
 * @author DAW08-TAREK
 */
public class Person implements Serializable {

    /**
     * Atributos privados de tipo String de la clase
     */
    private String name;
    private String password;

    /**
     * Constructor que inicializa a null los atributos de la clase
     */
    public Person() {
        name = null;
        password = null;
    }

    /**
     * Metodo publico que se encarga de devolver el valor del atributo de la
     * clase name
     *
     * @return el valor
     */
    public String getName() {
        return name;
    }

    /**
     * Metodo publico que se encarga de setear el valor del atributo de la clase
     * name
     *
     * @param name nuevo valor
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metodo publico que se encarga de devolver el valor del atributo de la
     * clase name
     *
     * @return el valor
     */
    public String getPassword() {
        return password;
    }

    /**
     * Metodo publico que se encarga de setear el valor del atributo de la clase
     * name
     *
     * @param password nuevo valor
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Metodo publico que devuelve un booleano principalmente inicializado a
     * true y que lo pone a false en caso de que el name o password este vacio o
     * sea mayor de 100
     *
     * @return
     */
    public boolean isValid() {
        boolean isValid = true;
        if (getName().isEmpty() || getName().length() > 100) {
            isValid = false;
        }
        if (getPassword().isEmpty() || getPassword().length() > 100) {
            isValid = false;
        }
        return isValid();
    }
}

