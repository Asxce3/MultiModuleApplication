package org.example.hub;

import jakarta.servlet.http.HttpServletRequest;
import org.example.hub.controllers.RestaurantServiceController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

@SpringBootTest
class HubApplicationTests {
    @Autowired
    RestaurantServiceController restaurantServiceController;

    @Test
    public void restaurantControllerTest() {
        HttpServletRequest e = new MockHttpServletRequest();
    }
}
