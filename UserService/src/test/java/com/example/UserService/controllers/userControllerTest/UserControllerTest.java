package com.example.UserService.controllers.userControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getOneTest() throws Exception {

        String userId = "72163e82-824e-41e7-9457-e1ae3b625ddf";

        mockMvc.perform(get("/user/get-one").param("id", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createTest() throws Exception {
        Map<String, String> userMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        userMap.put("username", "Nikolay");
        userMap.put("password", "NikolayPass1234");
        userMap.put("email", "Nikolay@gmail.com");
        userMap.put("telephone", "+77083723603");
        String user = mapper.writeValueAsString(userMap);

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user))
                .andExpect(status().is(201));
    }

    @Test
    public void updateTest() throws Exception {
        Map<String, String > map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        map.put("email", "SomeEmail@example.com");
        map.put("telephone", "+35 134 35 35");

        String json = objectMapper.writeValueAsString(map);

        String userId = "001798aa-ceca-484e-8b90-81808c7650bd";
        mockMvc.perform(put("/user/update").param("id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(200));
    }

    @Test
    public void deleteTest() throws Exception {
        String id = "001798aa-ceca-484e-8b90-81808c7650bd";
        mockMvc.perform(delete("/user/delete").param("id", id))
                .andExpect(status().isOk());
    }





}
