package za.co.hotelrunner.services;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

import static io.restassured.RestAssured.given;

public class Services {

    private RequestSpecification requestSpec;
    private final String baseURL;
    private String endPoint = "";
    private Map<String, String> headers;
    private String body;
    private Map<String, String> requestParam;
    private Map<String, String> formParams;
    private String methods;
    private final String[] acceptableMethods = new String[]{"GET", "POST", "DELETE", "PUT"};
    private Cookie cookie;

    private ArrayList<Object> media;

    public Services(String baseURL) {
        this.baseURL = baseURL;
    }

  /**
   * This function sets the endpoint.
   *
   * @param endpoint The endpoint is a string parameter that represents the URL or address of a web
   * service or API that the code is connecting to. The setEndpoint method is used to set the value of
   * the endpoint variable in the class.
   */
    public void setEndpoint(String endpoint) {
        this.endPoint = endpoint;
    }

  /**
   * The function sets the headers of an object using a provided map.
   *
   * @param headers The "headers" parameter is a Map object that contains key-value pairs of HTTP
   * headers to be added to a request. The keys represent the header names and the values represent the
   * header values. This method sets the "headers" instance variable of the current object to the
   * provided Map.
   */
    public void addHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

  /**
   * This function sets the value of the "body" variable to the input parameter "body".
   *
   * @param body The parameter "body" is a string that represents the content or message body of an
   * email or any other type of document. The method "addBody" sets the value of the instance variable
   * "body" to the value of the parameter "body".
   */
    public void addBody(String body) {
        this.body = body;
    }

  /**
   * The function sets the request parameters.
   *
   * @param requestParam A Map object that contains key-value pairs of request parameters to be added.
   * This method sets the instance variable "requestParam" to the provided Map object.
   */
    public void addRequestParam(Map<String, String> requestParam) {
        this.requestParam = requestParam;
    }

  /**
   * The function adds a key and a file location to an ArrayList.
   *
   * @param key The key is a unique identifier for the file being added. It is used to retrieve the
   * file later on when needed.
   * @param absoluteFileLocation The absolute file location is the complete path of a file on a
   * computer's file system, including the drive letter (if applicable), all directories, and the file
   * name with its extension. For example, "C:\Users\JohnDoe\Documents\example.txt" is an absolute file
   * location on
   */
    public void addFile(String key, String absoluteFileLocation){
        this.media = new ArrayList<>();
        this.media.add(key);
        this.media.add(new File(absoluteFileLocation));
    }

  /**
   * The function builds a request specification with the given base URL.
   */
    public void build() {
        requestSpec = new RequestSpecBuilder().setBaseUri(baseURL).build();
    }

  /**
   * This function returns a modified request specification based on various input parameters.
   *
   * @return The method `request()` returns a `RequestSpecification` object.
   */
    private RequestSpecification request(){
        RequestSpecification requestModifier = given().spec(requestSpec);
        if(headers!=null){
            requestModifier.headers(headers);
        }

        if (formParams !=null){
            requestModifier.formParams(formParams);
        }

        if (formParams!=null){
            requestModifier.formParams(formParams);
        }


        if(requestParam!=null){
            requestModifier.params(requestParam);
        }

        if(body!=null){
            requestModifier.body(body);
        }

        if(cookie!=null){
            requestModifier.cookie(cookie);
        }

        if(media!=null){
            requestModifier.multiPart((String) media.get(0),(File) media.get(1));
        }
        return requestModifier;
    }

  /**
   * The function sets the formParams variable to the provided Map of String keys and values.
   *
   * @param formParams A Map object that contains key-value pairs of form parameters to be added. The
   * keys represent the names of the form fields, while the values represent the values to be submitted
   * for those fields.
   */
    public void addFormParams(Map<String, String> formParams){
        this.formParams = formParams;
    }

/**
 * This function sets the HTTP method to be used in a request and throws an exception if the method is
 * not valid.
 *
 * @param method A string representing an HTTP method (e.g. GET, POST, PUT, DELETE).
 */
    public void setHTTPMethod(String method) {
        if(Arrays.stream(acceptableMethods).anyMatch(Predicate.isEqual(method.toUpperCase()))) {
            this.methods = method.toUpperCase();
        }
        else{
            throw new IllegalArgumentException("Invalid method: " + method
            +"\nValid methods: " + Arrays.toString(acceptableMethods));
        }
    }

    /**
   * This function returns the value of the "Cookie" header.
   *
   * @return The method `getCookie()` is returning the value of the "Cookie" header from the `headers`
   * object.
   */
    public String getCookie(){
        return headers.get("Cookie");
    }

    /**
    * The function sends an HTTP request.
    *
    * @return The method `send()` returns a `Response` object. The specific `Response` object returned
    * depends on the HTTP method specified in the `methods` variable. If `methods` is "POST", a
    * `Response` object is returned after making a POST request to the `endPoint`. If `methods` is
    * "PUT", a `Response` object is returned after making a PUT request to the
    */
    public Response send() {

        switch(methods) {
            case "POST":
                return request().when().redirects().follow(true).post(endPoint);
            case "PUT":
                return request().put(endPoint);
            case "DELETE":
                return request().delete(endPoint);
            default:
                return request().get(endPoint);
        }
    }

  /**
   * This function sets a cookie object.
   *
   * @param cookie The parameter "cookie" is an object of the class "Cookie". The method "setCookie"
   * sets the value of the instance variable "cookie" to the value of the parameter "cookie".
   */
    public void setCookie(Cookie cookie) {
        this.cookie=cookie;
    }

}
