package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.zervladpy.data.connection.ConnectionManager;
import com.zervladpy.data.connection.h2.H2Connection;
import com.zervladpy.data.dao.BookDAO;
import com.zervladpy.data.model.Book;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDAOTest {

    private static ConnectionManager connManager;
    private static BookDAO bookDAO;

    @BeforeAll
    public static void init() {
        connManager = H2Connection.getInstance();
        bookDAO = new BookDAO(connManager.getConnection());
    }

    @AfterAll
    public static void tearDown() {
        connManager.closeConnection();
    }

    @Test
    @Order(1)
    public void createBookTest() {

        Book book = new Book(1, "9788424937744", "Tractatus logico-philosophicus-"
                + "investigaciones filosóficas", "Ludwig Wittgenstein", 2017, false, new byte[0]);

        assertNotNull(bookDAO.save(book));

    }

    @Test
    @Order(2)
    public void getBookTest() {

        BookDAO bookDAO = new BookDAO(connManager.getConnection());

        assertNotNull(bookDAO.get(1));

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

        Book book = bookDAO.get(1);

        String authorName = book.getAuthor();
        book.setAuthor(authorTestName);

        bookDAO.update(book);

        assertEquals(authorTestName, bookDAO.get(1).getAuthor());

        book.setAuthor(authorName);

        bookDAO.update(book);

    }

    @Test
    @Order(4)
    public void deleteBookTest() {

        Book book = bookDAO.get(1);

        bookDAO.delete(book);

        assertNull(bookDAO.get(1));

    }

    @Test
    @Order(5)
    @Disabled
    public void deleteAllBooksTest() {
        BookDAO bookDAO = new BookDAO(connManager.getConnection());

        bookDAO.deleteAll();

        assertTrue(bookDAO.getAll().isEmpty());
    }
}
