package com.niantic.services;


import com.niantic.models.Category;
import com.niantic.models.Transaction;
import com.niantic.models.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class TransactionDao
{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionDao(){

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/budget");
        dataSource.setUsername("root");
        dataSource.setPassword("P@ssw0rd");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ArrayList<Transaction> getAllTransactions()
        {
            var transactions = new ArrayList<Transaction>();

            String sql =  """
                SELECT transaction_id
                    , user_id
                    , category_id
                    , vendor_id
                    , transaction_date
                    , amount
                    , notes
                FROM transactions;
                """;

            var row = jdbcTemplate.queryForRowSet(sql);

            while(row.next())
            {

                int transactionId = row.getInt("transaction_id");
                int userId = row.getInt("user_id");
                int categoryId = row.getInt("category_id");
                int vendorId = row.getInt("vendor_id");
                LocalDate transactionDate = null;
                BigDecimal amount = row.getBigDecimal("amount");
                String notes = row.getString("notes");

                var convertDate = row.getDate("transaction_date");
                if(convertDate != null)
                {
                    transactionDate = convertDate.toLocalDate();
                }
                var transaction = new Transaction(transactionId, userId, categoryId, vendorId, transactionDate, amount, notes);
                transactions.add(transaction);

            }
            return transactions;
        }

        public ArrayList<Transaction> getTransactionByCategory(int id)
        {
            ArrayList<Transaction> transactions = new ArrayList<>();

            String sql = """
                    SELECT transactions.transaction_id
                        , transactions.user_id
                        , transactions.category_id
                        , transactions.vendor_id
                        , transactions.transaction_date
                        , transactions.amount
                        , transactions.notes
                    FROM transactions
                    INNER JOIN categories ON transactions.category_id = categories.category_id
                    WHERE transactions.category_id = ?;
                    """;

            SqlRowSet row = jdbcTemplate.queryForRowSet(sql, id);

            while (row.next())
            {
                int transactionId = row.getInt("transaction_id");
                int userId = row.getInt("user_id");
                int categoryId = row.getInt("category_id");
                int vendorId = row.getInt("vendor_id");
                LocalDate transactionDate = null;
                BigDecimal amount = row.getBigDecimal("amount");
                String notes = row.getString("notes");

                Date convertDate = row.getDate("transaction_date");

                if (convertDate != null)
                {
                    transactionDate = convertDate.toLocalDate();
                }

                Transaction transaction = new Transaction(transactionId, userId, categoryId, vendorId, transactionDate, amount, notes);

                transactions.add(transaction);
            }

            return transactions;
        }

        public ArrayList<Transaction> getTransactionByUser(int id)
        {
            ArrayList<Transaction> transactions = new ArrayList<>();

            String sql = """
                    SELECT users.user_name
                    	, transactions.vendor_id
                        , transactions.transaction_date
                        , transactions.amount
                        , transactions.notes
                        , transactions.category_id
                        , transactions.transaction_id
                        , users.user_id
                    FROM transactions
                    INNER JOIN users on transactions.user_id = users.user_id
                    WHERE transactions.user_id = ?;
                    """;
            SqlRowSet row = jdbcTemplate.queryForRowSet(sql, id);

            while(row.next())
            {
                String userName = row.getString("user_name");
                int vendorId = row.getInt("vendor_id");
                LocalDate transactionDate = null;
                BigDecimal amount = row.getBigDecimal("amount");
                String notes = row.getString("notes");
                int categoryId = row.getInt("category_id");
                int transactionId = row.getInt("transaction_id");
                int userId = row.getInt("user_id");

                var convertDate = row.getDate("transaction_date");

                if( convertDate != null)
                {
                    transactionDate = convertDate.toLocalDate();
                }

                Transaction transaction = new Transaction(transactionId, userId, categoryId, vendorId, transactionDate, amount, notes);

                transactions.add(transaction);

            }
           return transactions;
        }

        public void addTransaction(Transaction transaction)
        {
            String sql = """
                    INSERT INTO transactions ( user_id
                                            , category_id
                                            , vendor_id
                                            , transaction_date
                                            , amount
                                            , notes)
                    VALUES (?,?,?,?,?,?);
                    """;

            jdbcTemplate.update(sql,
                    transaction.getUserId(),
                    transaction.getCategoryId(),
                    transaction.getVendorId(),
                    transaction.getTransactionDate(),
                    transaction.getAmount(),
                    transaction.getNotes()
            );

        }


    public Transaction getTransactionById(int searchUId)
    {

        String sql = """
                SELECT transaction_id
                    , user_id
                    , category_id
                    , vendor_id
                    , transaction_date
                    , amount
                    , notes
                FROM transactions
                WHERE transaction_id = ?;
                """;
        var row = jdbcTemplate.queryForRowSet(sql, searchUId);

        if(row.next()) {
            int transactionId = row.getInt("transaction_id");
            int userId = row.getInt("user_id");
            int categoryId = row.getInt("category_id");
            int vendorId = row.getInt("vendor_id");
            LocalDate transactionDate = null;
            BigDecimal amount = row.getBigDecimal("amount");
            String notes = row.getString("notes");

            var convertDate = row.getDate("transaction_date");
            if(convertDate != null)
            {
                transactionDate = convertDate.toLocalDate();
            }

            return new Transaction(transactionId, userId, categoryId, vendorId, transactionDate, amount, notes);
        }

     return null;

    }

    public void updateTransaction (Transaction transaction)
    {
        String sql = """
                UPDATE transactions
                SET user_id = ?
                    , category_id = ?
                    , vendor_id = ?
                    , transaction_date = ?
                    , amount = ?
                    , notes = ?
                WHERE transaction_id = ?;
               """;

        jdbcTemplate.update(sql,
                transaction.getUserId(),
                transaction.getCategoryId(),
                transaction.getVendorId(),
                transaction.getTransactionDate(),
                transaction.getAmount(),
                transaction.getNotes(),
                transaction.getTransactionId()
        );

    }

    public void deleteTransaction (int id)
    {
        String sql = """
                DELETE FROM transactions
                WHERE transaction_id = ?;
               """;

        jdbcTemplate.update(sql, id);

    }






}
