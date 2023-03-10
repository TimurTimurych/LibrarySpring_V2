package springcourse.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import springcourse.models.Book;
import springcourse.services.BooksService;

@Component
public class BookValidator implements Validator {

    private final BooksService booksService;


    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Book book = (Book) target;
        if (booksService.findByTitle(book).isPresent())
            errors.rejectValue("title", "", "Такая книга уже существует");
    }
}
