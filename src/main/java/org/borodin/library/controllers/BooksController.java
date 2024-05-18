package org.borodin.library.controllers;

import org.borodin.library.dao.BookDAO;
import org.borodin.library.dao.PersonDAO;
import org.borodin.library.models.Book;
import org.borodin.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.returnAllBooks());
        return "/books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model) {
        Optional<Book> bookToReturn = bookDAO.findBookById(id);
        if (bookToReturn.isPresent()) {
            Book book = bookToReturn.get();
            model.addAttribute("book", book);
            if (book.getPersonId() == null) {
                model.addAttribute("people", personDAO.index());
            } else {
                model.addAttribute("holder", personDAO.show(book.getPersonId()).orElseThrow());
            }
        } else {
            return "redirect:/books";
        }
        return "/books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Book> bookToEdit = bookDAO.findBookById(id);
        if (bookToEdit.isPresent()) {
            model.addAttribute("book", bookToEdit.get());
        } else {
            return "redirect:/books";
        }
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/books/edit";
        }
        bookDAO.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignBookToPerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.updatePersonIdGivenBookId(person.getPersonId(), id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/retract")
    public String retractBookFromPerson(@PathVariable("id") int id) {
        bookDAO.updatePersonIdGivenBookId(null, id);
        return "redirect:/books/" + id;
    }
}
