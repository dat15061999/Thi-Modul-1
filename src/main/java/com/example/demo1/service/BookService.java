package com.example.demo1.service;

import com.example.demo1.model.Author;
import com.example.demo1.model.Book;
import com.example.demo1.model.BookAuthor;
import com.example.demo1.model.Category;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookService {
    private static List<Book> books;

    private AuthorService authorService;
    private CategoryService categoryService;

    private BookAuthorService bookAuthorService;

    public BookService() {
        authorService = new AuthorService();
        categoryService = new CategoryService();
        bookAuthorService = new BookAuthorService();
    }

    private static int idCurrent;

    static {
        books = new ArrayList<>();
    }

    public void create(HttpServletRequest req){
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String price = req.getParameter("price");
        String publishDate = req.getParameter("publishDate");
        String categoryId = req.getParameter("category");
        List<Author> authors = Arrays.stream(req.getParameterValues("author"))
                                    .map(Integer::parseInt)
                .map(authorId -> authorService.findById(authorId))
                .toList();
        Category category = categoryService.getCategory(Integer.parseInt(categoryId));
        Book book = new Book();
        book.setId(++idCurrent);
        book.setTitle(title);
        book.setDescription(description);
        book.setCategory(category);
        book.setPrice(new BigDecimal(price));
        book.setPublishDate(Date.valueOf(publishDate));
        List<BookAuthor> bookAuthors = new ArrayList<>();
        for(var author : authors){
            var bookAuthor = bookAuthorService.create(new BookAuthor(book, author));
            bookAuthors.add(bookAuthor);
        }
        book.setBookAuthors(bookAuthors);

        books.add(book);


//        String[] authorIds = req.getParameter("author").split(",");
//        List<Author> authors1 = new ArrayList<>();
//        for (var authorId :authorIds) {
//            Author author = authorService.findById(Integer.parseInt(authorId));
//            authors1.add(author);
//        }



    }


    public List<Book> getBooks(){
        return books;
    }


}