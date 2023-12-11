package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.zervladpy.data.connection.ConnectionManager;
import com.zervladpy.data.connection.h2.H2Connection;
import com.zervladpy.data.dao.BookDAO;
import com.zervladpy.data.model.Book;
import com.zervladpy.utils.constraints.BookTable;
import com.zervladpy.utils.exceptions.SqlExceptionTracer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDAOTest {

    private static ConnectionManager connManager;
    private static BookDAO bookDAO;
    private static Book testBook;
    private static List<Book> testBooks = new ArrayList<>();

    @BeforeAll
    public static void init() {
        connManager = H2Connection.getInstance();
        bookDAO = new BookDAO(connManager.getConnection());
        testBook = new Book(
            1, "0000000000001", "Test Book", "Test Author", 2017, false, new byte[0]
        );

        testBooks.add(
            new Book(
                2, "0000000000002", "Test Book2", "Test Author", 2017, false, new byte[0]
            )
        );

        testBooks.add(
            new Book(
                3, "0000000000003", "Test Book3", "Test Author", 2017, false, new byte[0]
            )
        );

        testBooks.add(
            new Book(
                4, "0000000000004", "Test Book4", "Test Author", 2017, false, new byte[0]
            )
        );

    }

    @AfterAll
    public static void tearDown() {
        connManager.closeConnection();
    }

    @Test
    @Order(1)
    public void createBookTest() {

        bookDAO.save(testBook);
        assertNotNull(bookDAO.get(testBook.getIdBook()));
    }

    @Test
    @Order(2)
    public void getBookTest() {

        assertNotNull(bookDAO.get(testBook.getIdBook()));

    }

    @Test
    @Order(2)
    public void getAllBooksTest() {

        assertNotNull(bookDAO.getAll());

    }

    @Test
    @Order(3)
    public void updateBookTest() {

        String authorTestName = "Auhtor Test Name";

        Book book = bookDAO.get(testBook.getIdBook());

        String authorName = book.getAuthor();
        book.setAuthor(authorTestName);

        bookDAO.update(book);

        assertEquals(authorTestName, bookDAO.get(testBook.getIdBook()).getAuthor());

        book.setAuthor(authorName);

        bookDAO.update(book);

    }

    @Test
    @Order(4)
    public void deleteBookTest() {

        Book book = bookDAO.get(testBook.getIdBook());

        bookDAO.delete(book);

        assertNull(bookDAO.get(testBook.getIdBook()));

    }

    @Test
    @Order(5)
    public void insertBatch() {

        bookDAO.insertMany(testBooks);

        for (Book book : testBooks) {
            System.out.println("Checking book: " + book);
            assertNotNull(bookDAO.get(book.getIdBook()));
            System.out.println("Book checked: " + book);
        }


    }


    @Test
    @Order(6)
    public void deleteAllBooksTest() {
        try (var st = connManager.getConnection().createStatement()) {
            st.executeUpdate("delete from " + BookTable.TABLE_NAME + " where "+ BookTable.TITLE +" like '%Test%'");
        } catch (SQLException e) {
            SqlExceptionTracer.trace(e);
        }

        for (Book book : testBooks) {
            assertNull(bookDAO.get(book.getIdBook()));
        }

    }
}
