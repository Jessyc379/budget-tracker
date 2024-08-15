package com.niantic.services;

import com.niantic.models.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;

@Component
public class UserDao
{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao()
    {
        String databaseUrl = "jdbc:mysql://localhost:3306/budget";
        String userName = "root";
        String passWord = "P@ssw0rd";
        String driver = "com.mysql.cj.jdbc.Driver";

        DataSource dataSource = new BasicDataSource()
        {{
            setUrl(databaseUrl);
            setUsername(userName);
            setPassword(passWord);
            setDriverClassName(driver);
        }};

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ArrayList<User> getAllUsers()
    {
        ArrayList<User> users = new ArrayList<>();

        String sql = """
                SELECT user_id
                    , user_name
                    , first_name
                    , last_name
                    , phone
                    , email
                FROM users;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql);

        while (row.next())
        {
            int userId = row.getInt("user_id");
            String userName = row.getString("user_name");
            String firstName = row.getString("first_name");
            String lastName = row.getString("last_name");
            String phone = row.getString("phone");
            String email = row.getString("email");

            User user = new User(userId, userName, firstName, lastName, phone, email);

            users.add(user);
        }

        return users;
    }


    public User getUserById(int id)
    {
        User user = null;

        String sql = """
                SELECT user_id
                    , user_name
                    , first_name
                    , last_name
                    , phone
                    , email
                FROM users
                WHERE user_id = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, id);

        if (row.next())
        {
            int userId = row.getInt("user_id");
            String userName = row.getString("user_name");
            String firstName = row.getString("first_name");
            String lastName = row.getString("last_name");
            String phone = row.getString("phone");
            String email = row.getString("email");

            user = new User(userId, userName, firstName, lastName, phone, email);
        }

        return user;

    }


    public User getUserByName(String name)
    {
        User byName = null;

        String sql = """
                SELECT user_id
                    , user_name
                    , first_name
                    , last_name
                    , phone
                    , email
                FROM users
                WHERE user_name = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, name);

        if (row.next())
        {
            int userId = row.getInt("user_id");
            String userName = row.getString("user_name");
            String firstName = row.getString("first_name");
            String lastName = row.getString("last_name");
            String phone = row.getString("phone");
            String email = row.getString("email");

            byName = new User(userId, userName, firstName, lastName, phone, email);
        }

        return byName;
    }

    public void addUser(User user)
    {
        String sql = """
                INSERT INTO users
                (
                    user_id
                    , user_name
                    , first_name
                    , last_name
                    , phone
                    , email
                )
                VALUES (?, ?, ?, ?, ?, ?);
                """;

        jdbcTemplate.update(sql,
                user.getUserId(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getEmail());
    }

    public void updateUser(User user)
    {
        String sql = """
                UPDATE users
                SET user_name = ?
                    , first_name = ?
                    , last_name = ?
                    , phone = ?
                    , email = ?
                WHERE user_id = ?;
                """;

        jdbcTemplate.update(sql,
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getEmail(),
                user.getUserId());
    }

    public void deleteUser(int id)
    {
        String sql = """
                DELETE FROM users
                WHERE user_id = ?;
                """;

        jdbcTemplate.update(sql, id);
    }
}
