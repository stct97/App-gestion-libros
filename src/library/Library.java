/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.ext.IncompatibleFileFormatException;
import com.db4o.ext.OldFormatException;
import com.db4o.query.Query;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import users.Person;

/**
 * Esta clase representa una libreria de libros
 *
 * @author Tarek
 */
public class Library {

    /**
     * Metodo publico (constructor) de la clase Library
     */
    private Library() {
    }

    /**
     * Atributo privado y estatico de la clase ObjectContainer
     */
    private static final int ITEMS_PER_PAGE = 10;
    private static ObjectContainer db;
    private static boolean tittle_asc;
    private static String author_filter = "";
    private static int current_page = 0;
    private static int last_page;
    private static int firstIndexOfBook;
    private static int nextFirstIndexOfBook;

    /**
     * Metodo publico y estatico que devuelve un boleano y se encarga de
     * comprobar si la pagina actual coincide con la ultima pagina para poder
     * habilitar o desahibiltar los botones correspondientes en el frame
     * principal
     *
     * @return true en cualquier caso, false si son iguales
     */
    public static boolean inLastPage() {
        boolean last = true;
        if (current_page == last_page) {
            last = false;
        }
        return last;
    }

    /**
     * Metodo publico y estatico que devuelve un boleano y se encarga de
     * comprobar si la pagina actual es menor que la ultima pagina para poder
     * habilitar o desahibiltar los botones correspondientes en el frame
     * principal
     *
     * @return true en cualquier caso, false si es menor
     */
    public static boolean inFirstPage() {
        boolean first = true;
        if (current_page == 0) {
            first = false;
        }
        return first;
    }

    /**
     * Metodo publico y privado que obtiene el valor de la variable privada
     *
     * @return el valor obtenido y almacenado en la variable current_page
     */
    public static int getCurrent_page() {
        return current_page;
    }

    /**
     * Metodo publico, estatico y que no devuelve nada, se encarga de cambiar el
     * valor de la pagina principal
     *
     * @param current_page es el nuevo valor recibido por parametro
     */
    public static void setCurrent_page(int current_page) {
        Library.current_page = current_page;
    }

    /**
     * Metodo publico y estatico que devuelve un string conformado por la pagina
     * actual y la ultima pagina para setear dicho String en la etiqueta que
     * esta en el frame
     *
     * @return el string con lo anteriormente mencionado
     */
    public static String getCurrent_pageAsString() {
        String currentPageForMainWindow = "Pag. " + (current_page + 1) + " de " + (last_page + 1);
        return currentPageForMainWindow;
    }

    /**
     * Metodo publico, estatico y que no devuelve nada que se encarga de
     * quitarle uno al valor almacvenado en current_page
     */
    public static void decreasePage() {
        if (current_page > 0) {
            current_page--;
        }
    }

    /**
     * Metodo publico, estatico y que no devuelve nada que se encarga de sumarle
     * uno al valor almacvenado en current_page
     */
    public static void increasePage() {
        if (current_page < last_page) {
            current_page++;
        }
    }

    /**
     * Metodo esatico, publico y que no devuelve nada sino que setea el valor a
     * 0 de la pagina principal
     */
    public static void resetCurrentPage() {
        setCurrent_page(0);
    }

    /**
     * Metodo publico, esatico que devuelve un entero que esta almacenado en la
     * variable last_page de la clase Library
     *
     * @return el valor de la variable alamcenado
     */
    public static int getLast_page() {
        return last_page;
    }

    /**
     * Metodo estatico, privado que no devuelve nada y setea el valor de la
     * variable last_page
     *
     * @param last_page sera el nuevo valor de dicha variable
     */
    private static void setLast_page(int last_page) {
        Library.last_page = last_page;
    }

    /**
     * Metodo publico, esatico que devuelve un string, obtenido de la variable
     * con visibilidad privada de la clase Library
     *
     * @return el valor de dicha variable
     */
    public static String getAuthor_filter() {
        return author_filter;
    }

    /**
     * Metodo publico, esatico y que no devuelve nada se encarga de setear el
     * valor de de author para posteriormente filtrarlo
     *
     * @param author_filter es el string recibido por parametro
     */
    public static void setAuthor_filter(String author_filter) {
        Library.author_filter = author_filter;
    }

    /**
     * Metodo publico, estatico que devuelve un boleano y que sirve para saber
     * si estamos ordenando ascendentemente
     *
     * @return true en caso de lo estemos haciendo
     */
    public static boolean getTittle_asc() {
        return tittle_asc;
    }

    /**
     * Metodo publico, estatico y que no devuelve nada que se encarga de setear
     * el valor de la variable que alamcena si estamos almacenando o no
     * ascendentemente los libros segun su author
     *
     * @param tittle_asc el nuevo valor de dicha condicion
     */
    public static void setTittle_asc(boolean tittle_asc) {
        Library.tittle_asc = tittle_asc;
    }

    /**
     * Metodo publico que no devuelve nada y que añade al final de la lista un
     * nuevo libro
     *
     * @param newBook es un objeto de la clase Book que contendra los atributos
     * necesarios.
     * @throws Exception se pueden producir excepciones a la hora de ejecutar la
     * query, o que esta misma contenga errores sintacticos
     */
    public static void saveBook(Book newBook) throws Exception {
        db.store(newBook);

    }

    /**
     * Metodo publico que deberia estar en la clase Users pero por
     * incompatibilidades en la ejecucion de la BD debe estar en la clase
     * Library
     *
     * @param user objeto de la clase person
     * @throws Exception posibles al guardar dicho ususario en la BD
     */
    public static void savePerson(Person user) throws Exception {
        db.store(user);
    }

    /**
     * Metodo publico que no devuelve nada, comproueba que el indice pasado por
     * parametro es correcto y procede a borrarlo de la lista
     *
     * @param index representa un indice para seleccionar el objeto de la base
     * de datos orientada a objetos
     * @throws Exception a la hora de ejecutar la query o cualquier otro posible
     * error
     */
    public static void deleteBook(int index) throws Exception {
        Book book = new Book();
        ObjectSet<Book> oset;
        oset = db.queryByExample(book);
        int count = 0;
        while (oset.hasNext()) {
            Book bookDel = oset.next();
            if (count == index) {
                db.delete(bookDel);
            }
            count++;
        }

    }

    /**
     * Metodo publico y privado que no devuelve nada, y se encarga de ejecutar
     * una query para borrar todos los libros de la tabla books
     *
     * @throws Exception esperadas a la hora de ejecutar la query o por
     * cualquier otro error
     */
    public static void deleteAll() throws Exception {
        Book book = new Book();
        ObjectSet<Book> oset;
        oset = db.queryByExample(book);
        while (oset.hasNext()) {
            Book bookDel = oset.next();
            db.delete(bookDel);
        }
        System.out.println("No existen mas registros");
    }

    /**
     * Metodo publico que devuelve un libro en caso de que el libro pedido por
     * parametro cumpla unas condiciones
     *
     * @param index libro que se quiere seleccionar
     * @return null en caso de que el indice este fuera de rango
     * @throws Exception a la hora de retornar un libro de la base de datos.
     */
    public static Book getBook(int index) throws Exception {
        if (index >= 0) {
            return getBooks().get(index);
        } else {
            return null;
        }
    }

    /**
     * Metodo publico que devuelve una lista de libros
     *
     * @return la lista de libros
     * @throws Exception esperadas a la hora por ejemplo de que el while, no
     * haya siguiente registro, haya errores sintacticos o errores de ejecucion
     */
    public static ArrayList<Book> getAllBooks() throws Exception {
        ArrayList<Book> bookList = new ArrayList<>();
        ObjectSet<Book> oset;
        Book book;
        Query query = db.query();
        query.constrain(Book.class);
        oset = query.execute();
        while (oset.hasNext()) {
            book = oset.next();
            bookList.add(book);
        }
        return bookList;
    }

    /**
     * Metodo publico que devuelve una lista de libros
     *
     * @return dichamente la propia lista antes mencionada
     * @throws Exception esperadas a la hora por ejemplo de que el while, no
     * haya siguiente registro, haya errores sintacticos o errores de ejecucion
     */
    public static ArrayList<Book> getBooks() throws Exception {
        int count = 0;
        Book bookAux;
        ArrayList<Book> bookList = new ArrayList<>();
        Query query = db.query();
        query.constrain(Book.class);
        if (getTittle_asc() == true) {
            query.descend("title").orderAscending();
        } else {
            query.descend("title").orderDescending();
        }
        if (!author_filter.isEmpty()) {
            query.descend("author").constrain(getAuthor_filter()).like();
            resetCurrentPage();
        }
        ObjectSet oset = query.execute();
        while (oset.hasNext()) {
            bookAux = (Book) oset.next();
            if (count >= (ITEMS_PER_PAGE * current_page) && count < (ITEMS_PER_PAGE * (current_page + 1))) {
                bookList.add(bookAux);
            }
            count++;

        }

        setLast_page((count - 1) / ITEMS_PER_PAGE);
        return bookList;
    }

    /**
     * Metodo publico, estatico que devuelve un array de enteros de tamaño 4,
     * recorre una lista de todos los libros y si el getter de la edad es igual
     * al String que comparo suma 1 en la respectiva posicion donde se asigna
     *
     * @return @throws no se preveen errores
     */
    public static int[] countByAge() throws Exception {
        int num[] = {0, 0, 0, 0};
        for (Book book : getAllBooks()) {
            if (book.getAge().equals("Infantil")) {
                num[0]++;
            }
            if (book.getAge().equals("Mayores de 7 años")) {
                num[1]++;
            }
            if (book.getAge().equals("Mayores de 12")) {
                num[2]++;
            }
            if (book.getAge().equals("Adultos")) {
                num[3]++;
            }
        }
        return num;
    }

    /**
     * Metodo publico y statico que no devuelve nada y se encarga de hacer la
     * conexion a la base de datos
     *
     * @throws Exception esperadas a la hora de conectarse a la base de datos.
     */
    public static void connect() throws Exception {
        try {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
                    "library.db4o");
        } catch (DatabaseFileLockedException | DatabaseReadOnlyException | Db4oIOException | IncompatibleFileFormatException | OldFormatException e) {
            System.out.println(e);
        }
    }

    /**
     * Metodo publico y estatico que no devuelve nada que se encarga de cerrar
     * la conexion de la base de datos
     *
     * @throws Exception esperadas a la hora de cerrar la conexion de la base de
     * datos.
     */
    public static void disconnet() throws Exception {
        try {
            db.close();
        } catch (Db4oIOException e) {
            System.out.println(e);
        }
    }

    /**
     * Metodo de visibilidad publica que no devuelve nada y que escribe en
     * binario los objetos pertenecientes a la lista
     *
     * @param path representa la ruta absoluta
     * @throws java.io.IOException en caso de error, excepcion recogida y
     * propagada
     */
    public static void writeFile(String path) throws Exception {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);

            oos.writeInt(getBooks().size());
            for (Book writingBook : getBooks()) {
                oos.writeObject(writingBook);
            }
            System.out.println("Se ha escrito el fichero");

        } finally {
            if (oos != null) {
                oos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * Metodo publico que nop devuelve nada y que se encarga de leer los objetos
     * de una liosta escrita en binario
     *
     * @param path representa la ruta absoluta
     * @throws java.io.FileNotFoundException en caso de error
     * @throws java.lang.ClassNotFoundException en caso de error
     */
    public static void readFile(String path) throws Exception, ClassNotFoundException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            int size = ois.readInt();
            deleteAll();
            Book readingBook;
            for (int i = 0; i < size; i++) {
                readingBook = (Book) ois.readObject();
                Library.saveBook(readingBook);
            }
        } finally {
            if (ois != null) {
                ois.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * Metodo publico y estatico que no recibe nada y devueolve una lista de la
     * clase Person
     *
     * @return @throws Exception posibles al ejecutar la query, o en el while
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

}


