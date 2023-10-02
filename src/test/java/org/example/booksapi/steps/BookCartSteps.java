package org.example.booksapi.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.booksapi.services.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.http.client.utils.URLEncodedUtils.CONTENT_TYPE;

public class BookCartSteps {
    Services services;
    List<Double> bookPrices;
    List<String> authors;
    List<String> categories;

    List<String> bookIds;
    List<String> bookTitles;

    public BookCartSteps() {
        this.services = new Services("https://bookcart.azurewebsites.net/api/book/");
    }

    @When("the user searches for the book {string}")
    public void theUserSearchesForABook(String bookName) {
        int id = bookTitles.indexOf(bookName);
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, "application/json; charset=utf-8");
        services.setHTTPMethod("POST");
        services.setEndpoint("/api/Book/GetSimilarBooks/"+id);
        services.build();
        services.send();
    }

    @Given("the user has all books")
    public void theUserHasAllBooks(String bookNameToSearch) {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, "application/json; charset=utf-8");
        services.setHTTPMethod("GET");
        services.setEndpoint("/api/Book");
        services.build();
        Response response = services.send();

        JsonPath jsonPath = response.jsonPath();
        bookIds = jsonPath.getList("id");
        bookPrices = jsonPath.getList("price");
        authors = jsonPath.getList("author");
        categories = jsonPath.getList("category");
        bookTitles = jsonPath.getList("title");
    }

    @Then("the book price is {string}")
    public void theBookPriceIs(String arg0) {

    }

    @Then("the author is {string}")
    public void theAuthorIs(String arg0) {
    }

    @Then("the category is {string}")
    public void theCategoryIs(String arg0) {

    }
}