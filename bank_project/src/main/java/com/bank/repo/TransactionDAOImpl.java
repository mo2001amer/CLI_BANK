package com.bank.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

import com.bank.domain.Transaction;

public class TransactionDAOImpl implements TransactionDAO {


    // TRANSACTION TABLE
    private static final String
            CREATE_TRANSACTION_TABLE_SQL = """

            CREATE TABLE IF NOT EXISTS bank_transaction (
                transaction_id SERIAL PRIMARY KEY,

                transaction_type VARCHAR(20) NOT NULL,

                amount NUMERIC(12, 2) NOT NULL,

                from_account_id INTEGER
                    REFERENCES account(account_id),

                to_account_id INTEGER
                    REFERENCES account(account_id),

                created_at TIMESTAMP NOT NULL
                    DEFAULT CURRENT_TIMESTAMP
            )
            """;


    // ADD TRANSACTION
    private static final String
            INSERT_TRANSACTION_SQL = """

            INSERT INTO bank_transaction (
                transaction_type,
                amount,
                from_account_id,
                to_account_id
            )
            VALUES (?, ?, ?, ?)
            """;


    // TRANSACTION HISTORY
    private static final String
            FIND_BY_ACCOUNT_SQL = """

            SELECT
                transaction_id,
                transaction_type,
                amount,
                from_account_id,
                to_account_id,
                created_at
            FROM bank_transaction
            WHERE from_account_id = ?
               OR to_account_id = ?
            ORDER BY created_at DESC
            LIMIT 10
            """;


    public TransactionDAOImpl() {
        initializeSchema();
    }


    // ADD TRANSACTION
    @Override
    public void addTransaction(
            Transaction transaction) {

        try (
            Connection connection =ConnectionFactory.getConnectionFactory().getConnection();

            PreparedStatement statement =connection.prepareStatement(
                            INSERT_TRANSACTION_SQL
                    )
        ) {

            statement.setString(1,transaction.getTransactionType());

            statement.setDouble(2,transaction.getAmount());


            // FROM ACCOUNT
            if (transaction.getFromAccountId()== null) {

                statement.setNull(3,Types.INTEGER);

            } else {

                statement.setInt(3,transaction.getFromAccountId()
                );
            }


            // TO ACCOUNT
            if (transaction.getToAccountId()== null) {

                statement.setNull(4,Types.INTEGER);

            } else {

                statement.setInt(4,transaction.getToAccountId()
                );
            }


            statement.executeUpdate();

        } catch (SQLException e) {

            throw databaseError(
                    "Could not add transaction",
                    e
            );
        }
    }


    // GET TRANSACTION HISTORY
    @Override
    public List<Transaction> findByAccountId(int accountId) {

        List<Transaction> transactions = new ArrayList<>();


        try (
            Connection connection = ConnectionFactory.getConnectionFactory().getConnection();

            PreparedStatement statement =connection.prepareStatement(FIND_BY_ACCOUNT_SQL)
        ) {

            statement.setInt(1,accountId);

            statement.setInt(2,accountId);


            try (
                ResultSet resultSet = statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    Integer fromAccountId =null;

                    Integer toAccountId =null;


                    int from =resultSet.getInt(
                        "from_account_id"
                            );

                    if (!resultSet.wasNull()) {
                        fromAccountId = from;
                    }


                    int to = resultSet.getInt(
                        "to_account_id"
                            );

                    if (!resultSet.wasNull()) {
                        toAccountId = to;
                    }


                    Transaction transaction =new Transaction(resultSet.getInt("transaction_id"),

                                    resultSet.getString("transaction_type"),

                                    resultSet.getDouble("amount"),

                                    fromAccountId,

                                    toAccountId,

                                    resultSet.getTimestamp("created_at")
                            );


                    transactions.add(transaction);
                }
            }


            return transactions;

        } catch (SQLException e) {

            throw databaseError("Could not retrieve transactions",e);
        }
    }


    // CREATE TRANSACTION TABLE
    private void initializeSchema() {

        try (
            Connection connection =ConnectionFactory.getConnectionFactory().getConnection();

            PreparedStatement statement =connection.prepareStatement(CREATE_TRANSACTION_TABLE_SQL)
        ) {

            statement.executeUpdate();

        } catch (SQLException e) {

            throw databaseError("Could not initialize transaction table",e);
        }
    }


    private IllegalStateException databaseError(String message,SQLException cause) {

        return new IllegalStateException( message,cause);
    }
}