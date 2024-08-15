package com.niantic.services;

import com.niantic.models.Category;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;

@Component
public class CategoryDao
{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryDao()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/budget");
        dataSource.setUsername("root");
        dataSource.setPassword("P@ssw0rd");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ArrayList<Category> getAllCategories()
    {
        ArrayList<Category> categories = new ArrayList<>();

        String sql = """
                SELECT category_id
                    , category_name
                    , description
                FROM categories;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql);

        while (row.next())
        {
            int categoryId = row.getInt("category_id");
            String categoryName = row.getString("category_name");
            String description = row.getString("description");

            Category category = new Category(categoryId, categoryName, description);

            categories.add(category);
        }

        return categories;
    }


    public Category getCategoryByName(String name)
    {
        Category category = null;

        String sql = """
                SELECT category_id
                    , category_name
                    , description
                FROM categories
                WHERE category_name = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, name);

        if (row.next())
        {
            int categoryId = row.getInt("category_id");
            String categoryName = row.getString("category_name");
            String description = row.getString("description");

            category = new Category(categoryId, categoryName, description);

        }

        return category;
    }


    public void addCategory(Category category)
    {
        String sql = """
                INSERT INTO categories
                (
                    category_id
                    , category_name
                    , description
                )
                VALUES (?, ?, ?);
                """;

        jdbcTemplate.update(sql,
                category.getCategoryId(),
                category.getCategoryName(),
                category.getDescription());

    }
}
