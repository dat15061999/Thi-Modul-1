package com.example.demo1.service;

import com.example.demo1.model.Author;
import com.example.demo1.model.Book;
import com.example.demo1.model.BookAuthor;
import com.example.demo1.model.Category;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

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
    }
    public void update(HttpServletRequest req){
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
        for (var book : books) {
            if (book.getId() == Integer.parseInt(req.getParameter("id"))) {
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
                break;
            }
        }

    }


    public List<Book> getBooks(boolean delete){
        return books.stream()
                .filter(book -> book.isDelete() == delete)
                .collect(Collectors.toList());
    }


    public void delete(HttpServletRequest req) {
        String[] selected = req.getParameter("id").split(",");
        for (var id : selected) {
            for (var item: books) {
                if (item.getId() == Integer.parseInt(id)) {
                    item.setDelete(true);
                    break;
                }
            }
        }

    }
    public void restore(HttpServletRequest req) {
        String[] selected = req.getParameter("id").split(",");
        for (var id : selected) {
            for (var item: books) {
                if (item.getId() == Integer.parseInt(id)) {
                    item.setDelete(false);
                    break;
                }
            }
        }
    }

    public Book findID(int id) {
        return books.stream().filter(e->e.getId()==id).findFirst().get();
    }

    public void deleteALl(HttpServletRequest req) {
        String[] selected = req.getParameter("id").split(",");
        List<Book> bookList = new ArrayList<>();
        for (var id : selected) {
            for (var item: books) {
                if (item.getId() == Integer.parseInt(id)) {
                    bookList.add(item);
                }
            }
        }
        books.removeAll(bookList);
    }

    public List<Book> search(HttpServletRequest req) {
        String search = req.getParameter("search");
        if (search == null) {
            return Collections.emptyList(); // Trả về danh sách rỗng nếu search là null
        }

        search = search.trim(); // Loại bỏ khoảng trắng thừa

        String finalSearch = search;
        return books.stream()
                .filter(book -> {
                    String title = book.getTitle();
                    String description = book.getDescription();
                    String category = book.getCategory().getName();
                    String authors = book.getAuthors();

                    return title != null && title.equalsIgnoreCase(finalSearch)
                            || description != null && description.contains(finalSearch)
                            || category != null && category.equalsIgnoreCase(finalSearch)
                            || authors != null && authors.contains(finalSearch);
                })
                .collect(Collectors.toList());
    }
}