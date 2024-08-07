package com.example.RestaurantService.DAO.postgres;

import com.example.RestaurantService.exceptions.DaoException;
import com.example.RestaurantService.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class RestaurantDAOImpl {

    private final DaoUtils utils = new DaoUtils();
    // Локализация
    private final String errorMessage = "Something went wrong on server";

    @Autowired
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public List<Restaurant> getMany() {

        String sql = "SELECT restaurant.id, restaurant.name, " +
                        "restaurant_rating.rating, restaurant_rating.count_ratings " +
                "FROM restaurant " +
                "INNER JOIN restaurant_rating " +
                "ON restaurant.id = restaurant_rating.restaurant_id";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Restaurant.class));

        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }
    }


    public Optional<Restaurant> getOne(UUID id)  {
        String sql = "SELECT restaurant.id, restaurant.name, " +
                "restaurant_rating.rating, restaurant_rating.count_ratings " +
                "FROM restaurant " +
                "INNER JOIN restaurant_rating " +
                "ON restaurant.id = restaurant_rating.restaurant_id " +
                "WHERE restaurant.id = ?";

        try {
            return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Restaurant.class))
            .stream().findFirst();

        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }
    }

    public void create(Restaurant restaurant) {
        try {
            jdbcTemplate.update("INSERT INTO restaurant VALUES(gen_random_uuid(), ?)",
                    restaurant.getName());

        }   catch (DuplicateKeyException e) {
                Optional<String> key = utils.getKeyName(e.getMessage());
                if (key.isPresent()) {
                    throw new DuplicateKeyException("Current " + key.get() + " already exists");
                }

        }   catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("Some fields is empty");

        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }

    }

    public void update(UUID id, Restaurant restaurant) {
        try {
            jdbcTemplate.update("UPDATE restaurant SET name = ? WHERE id = ?",
                    restaurant.getName(), id);

        }   catch (DuplicateKeyException e) {
                Optional<String> key = utils.getKeyName(e.getMessage());
                if (key.isPresent()) {
                    throw new DuplicateKeyException("Current " + key.get() + " already exists");
                }

        }   catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("Сan only change your ......");

        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }
    }

    public void delete(UUID id) {
        try {
            jdbcTemplate.update("DELETE FROM restaurant WHERE id = ?", id);

        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }

    }
}

class DaoUtils {

    public Optional<String> getKeyName(String message){
        String keyPattern = "Key \\(([^)]+)\\)=\\(([^)]+)\\)";
        Pattern pattern = Pattern.compile(keyPattern);
        Matcher matcher = pattern.matcher(message);

        if(matcher.find()){
            return Optional.of(matcher.group(1));
        }
        return Optional.empty();

    }

}
