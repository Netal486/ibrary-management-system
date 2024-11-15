package com.test.serviceImpl;

import com.test.model.Book;
import com.test.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book1;
    private Book book2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        book1 = new Book(1L, "Book 1", "Author 1",2000);
        book2 = new Book(2L, "Book 2", "Author 2",2001);
    }

    @Test
    public void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        var books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Book 2", books.get(1).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookByIdFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertTrue(foundBook.isPresent());
        assertEquals("Book 1", foundBook.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetBookByIdNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertFalse(foundBook.isPresent());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        Book createdBook = bookService.createBook(book1);

        assertNotNull(createdBook);
        assertEquals("Book 1", createdBook.getTitle());
        verify(bookRepository, times(1)).save(book1);
    }

    @Test
    public void testUpdateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        Book updatedBook = bookService.updateBook(1L, book1);

        assertNotNull(updatedBook);
        assertEquals(1L, updatedBook.getId());
        assertEquals("Book 1", updatedBook.getTitle());
        verify(bookRepository, times(1)).save(book1);
    }

    @Test
    public void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}
