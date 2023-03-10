package springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcourse.models.Book;
import springcourse.models.Person;
import springcourse.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Book findOne(int id) {
        Optional<Book> foundOptional = booksRepository.findById(id);
        return foundOptional.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updateBook) {
        updateBook.setId(id);
        booksRepository.save(updateBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }


    public Optional<Book> findByTitle(Book book) {
        return booksRepository.findByTitle(book.getTitle());
    }

    public Optional<Person> getOwner(int bookId) {
        Book book = booksRepository.findById(bookId).get();
        return Optional.ofNullable(book.getPerson());
    }


    @Transactional
    public void deleteOwner(int id) {
        Book book = booksRepository.findById(id).orElse(null);
        if (book != null) {
            book.setPerson(null);
            booksRepository.save(book);
        }
    }

    @Transactional
    public void addOwner(int bookId, Person person) {
        Book book = booksRepository.findById(bookId).orElse(null);
        assert book != null;
        book.setPerson(person);
        book.setTakenDate(new Date());
        booksRepository.save(book);
    }

    public List<Book> findAll(boolean sort) {
        if (sort)
            return booksRepository.findAll(Sort.by("year"));
        else
            return booksRepository.findAll();

    }

    public List<Book> findAllWithParam(Integer page, Integer perPage, boolean sort) {
        if (sort)
            return booksRepository.findAll(PageRequest.of(page, perPage,
                    Sort.by("year"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, perPage)).getContent();

    }

    public List<Book> search(String request) {
        return booksRepository.searchBooksByTitleStartingWith(request);
    }

}
