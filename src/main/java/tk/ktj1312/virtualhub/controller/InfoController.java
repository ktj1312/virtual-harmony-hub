package tk.ktj1312.virtualhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hubs")
public class InfoController {

    @GetMapping("")
    public ResponseEntity<?> getHubs(){

        List<String> hubs = new ArrayList<>();
        hubs.add("v_hub");

        Map<String,Object> response = new HashMap<>();
        response.put("hubs",hubs);

        return new ResponseEntity<>(hubs, HttpStatus.OK);
    }
}
