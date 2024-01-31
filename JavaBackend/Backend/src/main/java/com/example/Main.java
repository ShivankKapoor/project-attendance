package com.example;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import record.Records;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@RestController
@SpringBootApplication
@EnableAsync
public class Main {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/df")
    String df() throws SQLException {

        ResultSet rs = null;
        dbConnection connection = new dbConnection();
        rs = connection.DataBase(new String[]{"This does nothing"});


        return "Hruday";
    }

    @RequestMapping("/checkIn")
    CompletableFuture<ResponseEntity<Map>> checkIn(@RequestBody Records.Checkin checkIn) throws SQLException {
        int isCheckIn;
        dbConnection connection = new dbConnection();
        isCheckIn = connection.addCheckInEntrty(checkIn);
        Map<String, Object> data = new HashMap<>();

        if (isCheckIn != 1) {
            data.put("Error_Message", "Oops guess we messed :(");

            ResponseEntity<Map> resEnt = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .header("DeeZNuts", "suckEm")
                    .body(data);

            return CompletableFuture.completedFuture(resEnt);
        }

        data.put("isCheckIn", isCheckIn);

        ResponseEntity<Map> resEnt = ResponseEntity.status(HttpStatus.CREATED)
                .header("DeeZNuts", "suckEm")
                .body(data);

        return CompletableFuture.completedFuture(resEnt);
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}