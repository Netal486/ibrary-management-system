package com.test.controller;


import com.test.model.Book;
import com.test.sevice.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Book book1 = new Book(1L, "Book 1", "Author 1",111);
        Book book2 = new Book(2L, "Book 2", "Author 2",222);

        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));
    }

    @Test
    public void testGetBookByIdFound() throws Exception {
        Book book = new Book(1L, "Book 1", "Author 1",111);

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book 1"));
    }

    @Test
    public void testGetBookByIdNotFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book(null, "New Book", "New Author", 222);
        Book createdBook = new Book(1L, "New Book", "New Author", 222);

        when(bookService.createBook(any(Book.class))).thenReturn(createdBook);

        mockMvc.perform(post("/api/books/createbook")
                        .contentType("application/json")
                        .content("{\"title\":\"New Book\",\"author\":\"New Author\",\"publicationYear\":222}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.author").value("New Author"))
                .andExpect(jsonPath("$.publicationYear").value(222));
    }


    @Test
    public void testUpdateBookFound() throws Exception {
        Book book = new Book(1L, "Updated Book", "Updated Author", 222);

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/books/{id}", 1L)
                        .contentType("application/json")
                        .content("{\"title\":\"Updated Book\",\"author\":\"Updated Author\",\"publicationYear\":222}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"))
                .andExpect(jsonPath("$.author").value("Updated Author"))
                .andExpect(jsonPath("$.publicationYear").value(222));
    }


    @Test
    public void testUpdateBookNotFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/books/{id}", 1L)
                        .contentType("application/json")
                        .content("{\"title\":\"Updated Book\",\"author\":\"Updated Author\",\"publicationYear\":222}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBookFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(new Book(1L, "Book to Delete", "Author",222)));

        mockMvc.perform(delete("/api/books/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/books/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(bookService, times(0)).deleteBook(1L);
    }
}
