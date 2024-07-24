package com.example.RestaurantService.repository.DAO.postgres;

import com.example.RestaurantService.model.Restaurant;
import com.example.RestaurantService.repository.DAO.ObjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestaurantDAOImpl implements ObjectDAO<Restaurant> {
    @Autowired
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public List<Restaurant> getMany() {
        String sql = "SELECT restaurant.id, restaurant.name, " +
                        "restaurant_rating.rating, restaurant_rating.count_ratings " +
                "FROM restaurant " +
                "INNER JOIN restaurant_rating " +
                "ON restaurant.id = restaurant_rating.restaurant_id";

        List<Restaurant> restaurants = jdbcTemplate.query
                (sql, new BeanPropertyRowMapper<>(Restaurant.class));

        return restaurants;
    }


    @Override
    public Optional<Restaurant> getOne(UUID id) throws Exception {
        String sql = "SELECT restaurant.id, restaurant.name, " +
                "restaurant_rating.rating, restaurant_rating.count_ratings " +
                "FROM restaurant " +
                "INNER JOIN restaurant_rating " +
                "ON restaurant.id = restaurant_rating.restaurant_id " +
                "WHERE restaurant.id = ?";

        try {
            return jdbcTemplate.query
                    (sql,
                            new Object[]{id},
                            new BeanPropertyRowMapper<>(Restaurant.class))
            .stream().findFirst();


        }   catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public void create(Restaurant restaurant) throws Exception {
        jdbcTemplate.update("INSERT INTO restaurant VALUES(gen_random_uuid(), ?)",
                restaurant.getName());
    }

    @Override
    public void update(UUID id, Restaurant restaurant) throws Exception {
        int i = jdbcTemplate.update("UPDATE restaurant SET name = ? WHERE id = ?",
                restaurant.getName(),
                id
        );

        if (i != 1) {
            throw new Exception("Restaurant not found");
        }
    }

    @Override
    public void delete(UUID id) throws Exception {
        int i = jdbcTemplate.update("DELETE FROM restaurant WHERE id = ?", id);

        if (i != 1) {
            throw new Exception("Restaurant not found");
        }
    }
}
