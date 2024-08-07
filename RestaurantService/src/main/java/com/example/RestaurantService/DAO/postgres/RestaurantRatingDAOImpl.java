package com.example.RestaurantService.DAO.postgres;

import com.example.RestaurantService.model.RestaurantRating;
import com.example.RestaurantService.DAO.ObjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestaurantRatingDAOImpl implements ObjectDAO<RestaurantRating> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RestaurantRating> getMany() {
        return jdbcTemplate.query("SELECT * FROM restaurant_rating",
                new BeanPropertyRowMapper<>(RestaurantRating.class));
    }

    @Override
    public Optional<RestaurantRating> getOne(UUID id) throws Exception {
        return jdbcTemplate.query("SELECT * FROM restaurant_rating WHERE id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(RestaurantRating.class)
        ).stream().findAny();
    }

    @Override
    public void create(RestaurantRating restaurantRating) throws Exception {
    }

    @Override
    public void update(UUID id, RestaurantRating restaurantRating) throws Exception {
        int i = jdbcTemplate.update
                ("UPDATE restaurant_rating SET rating = ?, count_ratings = ? WHERE id = ?",
                        restaurantRating.getRating(),
                        restaurantRating.getCountRatings(),
                        restaurantRating.getId()
                );

        if (i != 1) {
            throw new Exception("Restaurant rating not found");
        }
    }

    @Override
    public void delete(UUID id) throws Exception {
        int i = jdbcTemplate.update("DELETE FROM restaurant_rating WHERE id = ?", id);
        if (i != 1) {
            throw new Exception("Restaurant rating not found");
        }
    }

    public Optional<RestaurantRating> getRestaurantById(UUID id) throws Exception {
        return jdbcTemplate.query("SELECT * FROM restaurant_rating WHERE restaurant_id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(RestaurantRating.class)
        ).stream().findAny();
    }
}
