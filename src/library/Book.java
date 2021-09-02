/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Esta clase representa un libro
 *
 * @author DAW08-TAREK
 */
public class Book implements Serializable {

    /**
     * Atributos con visibilidad privada de la clase Book, en los que hay un
     * ArrayList de Strings
     */
    private String title, author, editorial, age;
    private ArrayList<String> genres;

    /**
     * Metodo publico denominado constructor vacio que inicializa la lista
     * genres
     */
    public Book() {
        genres = new ArrayList<>();
        title = null;
        author = null;
        editorial = null;
        age = null;
        genres = null;
    }

    /**
     * Metodo publico que obtiene el nombre del atributo title.
     *
     * @return el valor de title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Metodo publico que no devuelve nada y modifica el valor de title.
     *
     * @param title recibido sera el nuevo valor para titulo.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Metodo publico que obtiene el nombre del atributo author.
     *
     * @return el valor de author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Metodo publico que no devuelve nada y modifica el valor de author.
     *
     * @param author sera el nuevo valor para el atributo.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Metodo publico que obtiene el nombre del atributo editorial.
     *
     * @return el valor de editorial.
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * Metodo publico que no devuelve nada y modifica el valor de editorial.
     *
     * @param editorial sera el nuevo valor para el atributo.
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    /**
     * Metodo publico que obtiene el nombre del atributo age.
     *
     * @return el valor del atributo age.
     */
    public String getAge() {
        return age;
    }

    /**
     * Metodo publico que no devuelve nada y modifica el valor de age.
     *
     * @param age sera el nuevo valor del atributo age.
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Metodo publico que obtiene el nombre del atributo genres.
     *
     * @return el valor de genres.
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * Metodo publico que no devuelve nada y modifica el valor de genres.
     *
     * @param genres sera el nuevo valor del atributo.
     */
    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;

    }

    /**
     * Metodo que devuelve un vector de valores correspondientes a los atirbutos
     * de la clase, ademas hace uso del metodo join implmentendado en la clase
     * String para convertir una lista en tantos arrays diferentes como
     * elementos tenga la lista.
     *
     * @return un vector de Strings compuesto por los atributos de la clase
     */
    public String[] toArray() {
        return new String[]{getTitle(), getAuthor()};
    }

    /**
     * Metodo publico que separa un ArrayList en arrays de Strings gracias al
     * metodo ya implmentendado en la clase String join
     *
     * @return un array de String de tamaño uno
     */
    public String getSelectedGenreAsString() {
        return String.join(" - ", genres);
    }

    /**
     * Recibe un String y no devuelve ningún valor. Recibirá un String de la
     * forma “genero1 - genero2”, por lo que deberá partirlo y generar un
     * ArrayList.
     *
     * @param genres parametro en forma de String que se unirá para generar una
     * lista de Strings
     */
    public void setGenres(String genres) {
        String[] ArrayListGenres = genres.split(" - ");
        this.genres.clear();
        for (String element : ArrayListGenres) {
            this.genres.add(element);
        }
    }

    /**
     * Comprueba si los valores introducidos son correctos, es decir, cumplen
     * con las condiciones establecidas tales como que no esten vacios o no
     * superen los 100 caracteres
     *
     * @return un boleano en funcion de si se cumple o no
     */
    public boolean isValid() {
        boolean isValid = true;
        if (getTitle().isEmpty() || getTitle().length() > 100) {
            isValid = false;
        }
        if (getAuthor().isEmpty() || getAuthor().length() > 100) {
            isValid = false;
        }
        if (getEditorial().contentEquals("- Sin seleccionar -")) {
            isValid = false;
        }
        if (getAge().isEmpty()) {
            isValid = false;
        }
        if (getGenres().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }
}


