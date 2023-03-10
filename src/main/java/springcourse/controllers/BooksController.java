package springcourse.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springcourse.models.Book;
import springcourse.models.Person;
import springcourse.services.BooksService;
import springcourse.services.PeopleService;
import springcourse.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }


    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer perPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sort) {
        if (page == null || perPage == null)
            model.addAttribute("books", booksService.findAll(sort));
        else
            model.addAttribute("books", booksService.findAllWithParam(page, perPage, sort));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {
        Optional<Person> bookOwner = booksService.getOwner(id);

        model.addAttribute("book", booksService.findOne(id));

        if (bookOwner.isPresent())
            model.addAttribute("bookOwner", bookOwner.get());
        else
            model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/delete")
    public String deleteOwner(@PathVariable("id") int id) {
        booksService.deleteOwner(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/add_owner")
    public String addOwner(@PathVariable("id") int bookId,
                           @ModelAttribute("person") Person person) {
        booksService.addOwner(bookId, person);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/search")
    public String searchBook(Model model, @RequestParam(value = "request", required = false) String request) {

        model.addAttribute("books", booksService.search(request));
        for (Book book : booksService.search(request)) {
            System.out.println(book.getTitle());
        }
        return "books/search";
    }
}
