package com.niantic.services;


import com.niantic.models.Transaction;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;
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








}
