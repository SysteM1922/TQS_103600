package io.cucumber.skeleton;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSearchSteps {
	Library library = new Library();
	List<Book> result = new ArrayList<>();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@Given("a book with the title {string}, written by {string}, published in {date}")
	public void addNewBook(final String title, final String author, final Date date) {
		Book book = new Book(title, author, date);
		library.addBook(book);
	}

	@And("another book with the title {string}, written by {string}, published in {date}")
	public void addAnotherBook(final String title, final String author, final Date date) {
		Book book = new Book(title, author, date);
		library.addBook(book);
	}

	@When("the customer searches for books published between {date} and {date}")
	public void setSearchParameters(final Date fromDate, final Date toDate) {
		result = library.findBooks(fromDate, toDate);
	}

	@Then("{int} books should have been found")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertThat(result.size(), equalTo(booksFound));
	}

	@And("Book {int} should have the title {string}")
	public void verifyBookAtPosition(final int position, final String title) {
		assertThat(result.get(position - 1).getTitle(), equalTo(title));
	}

	@ParameterType("\\d{4}-\\d{2}-\\d{2}")
	public Date date(String dateString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(dateString);
	}
}