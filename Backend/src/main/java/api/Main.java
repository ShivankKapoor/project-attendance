package api;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import record.Records;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableAsync
public class Main {

    @RequestMapping("/")
    String home() {
        return "Test to see if the server is up and running";
    }
    @CrossOrigin  // to enable cors
    @RequestMapping("/checkIn")  //this is the url that for our api
    // This is an async response for a checkIn
    CompletableFuture<ResponseEntity<Map>> checkIn(@RequestBody Records.Checkin checkIn) throws SQLException {
        boolean isCheckIn;
        dbConnection connection = new dbConnection();
        isCheckIn = connection.addCheckInEntrty(checkIn);

        Map<String, Object> data = new HashMap<>();

        // if check in failed give an error message
        if (!isCheckIn) {
            data.put("Error_Message", "Oops guess we messed :(");

            // header for the error message
            ResponseEntity<Map> resEnt = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .header("Test", "Value")
                    .body(data);

            return CompletableFuture.completedFuture(resEnt);
        }

        data.put("isCheckIn", isCheckIn);


        ResponseEntity<Map> resEnt = ResponseEntity.status(HttpStatus.CREATED)
                .header("Test", "Value")
                .body(data);

        return CompletableFuture.completedFuture(resEnt);
    }

    @CrossOrigin  // to enable cors
    @RequestMapping("/register")  //this is the url that for our api
        // This is an async response for a checkIn
    CompletableFuture<ResponseEntity<Map>> register(@RequestBody Records.register register) throws SQLException {
        boolean isRegister;
        register Register = new register();
        isRegister = Register.addNewUser(register);
        Map<String, Object> data = new HashMap<>();

        // if check in failed give an error message
        if (!isRegister) {
            data.put("Error_Message", "Oops guess we messed :(");

            // header for the error message
            ResponseEntity<Map> resEnt = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .header("Test", "Value")
                    .body(data);

            return CompletableFuture.completedFuture(resEnt);
        }

        data.put("register", isRegister);

        ResponseEntity<Map> resEnt = ResponseEntity.status(HttpStatus.CREATED)
                .header("Test", "Value")
                .body(data);

        return CompletableFuture.completedFuture(resEnt);
    }

    @CrossOrigin  // to enable cors
    @RequestMapping("/login")  //this is the url that for our api
        // This is an async response for a checkIn
    CompletableFuture<ResponseEntity<Map>> login(@RequestBody Records.login login) throws SQLException {
        boolean isLogin;
        register Register = new register();
        isLogin = Register.login(login);
        Map<String, Object> data = new HashMap<>();

        // if check in failed give an error message
        if (!isLogin) {
            data.put("Error_Message", "Incorrect userID or Password ");

            // header for the error message
            ResponseEntity<Map> resEnt = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .header("Test", "Value")
                    .body(data);

            return CompletableFuture.completedFuture(resEnt);
        }

        data.put("login", isLogin);

        ResponseEntity<Map> resEnt = ResponseEntity.status(HttpStatus.ACCEPTED)
                .header("Test", "Value")
                .body(data);

        return CompletableFuture.completedFuture(resEnt);
    }

    @CrossOrigin
    @RequestMapping("/getAllClassesStudent")

    CompletableFuture<ResponseEntity<Map<String, Object>>> getAllClassesStudent(@RequestBody Records.getAllClassesStudent request) throws SQLException {
        dbConnection connection = new dbConnection();
        List<Records.ClassInfo> classes = connection.getClassesForStudent(request.getStudentId());

        Map<String, Object> data = new HashMap<>();
        if (classes.isEmpty()) {
            data.put("Error_Message", "No classes found for this student.");
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).body(data));
        } else {
            data.put("classes", classes);
            return CompletableFuture.completedFuture(ResponseEntity.ok(data));
        }
    }

    @CrossOrigin  // to enable cors
    @RequestMapping("/login")  //this is the url that for our api

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}