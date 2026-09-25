package com.bank.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bank.domain.Customer;

public class CustomerDAOImpl
        implements CustomerDAO {


    // ACCOUNT TABLE
    private static final String CREATE_ACCOUNT_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS account (
                account_id SERIAL PRIMARY KEY,
                pin INTEGER NOT NULL,
                balance NUMERIC(12, 2) NOT NULL DEFAULT 0.00,
                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
            )
            """;


    // ADD ACCOUNT
    private static final String INSERT_SQL = """
            INSERT INTO account (pin, balance)
            VALUES (?, ?)
            RETURNING account_id
            """;


    // FIND ACCOUNT
    private static final String FIND_BY_ID_SQL = """
            SELECT account_id, pin, balance
            FROM account
            WHERE account_id = ?
            """;


    // UPDATE BALANCE
    private static final String UPDATE_BALANCE_SQL = """
            UPDATE account
            SET balance = ?
            WHERE account_id = ?
            """;


    // REMOVE MONEY DURING TRANSFER
    private static final String WITHDRAW_TRANSFER_SQL = """
            UPDATE account
            SET balance = balance - ?
            WHERE account_id = ?
            """;


    // ADD MONEY DURING TRANSFER
    private static final String DEPOSIT_TRANSFER_SQL = """
            UPDATE account
            SET balance = balance + ?
            WHERE account_id = ?
            """;


    public CustomerDAOImpl() {
        initializeSchema();
    }


    // REGISTER
    @Override
    public int addCustomer(Customer customer) {

        try (
            Connection connection =ConnectionFactory.getConnectionFactory().getConnection();

            PreparedStatement statement =connection.prepareStatement(INSERT_SQL)
        ) {

            statement.setInt( 1, customer.getPin()
            );

            statement.setDouble(2,customer.getBalance()
            );


            try (
                ResultSet resultSet =statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return resultSet.getInt(
                            "account_id"
                    );
                }


                throw new IllegalStateException(
                        "Account was created but no Account ID was returned."
                );
            }

        } catch (SQLException e) {

            throw databaseError(
                    "Could not add account",
                    e
            );
        }
    }


    // FIND ACCOUNT BY ID
    @Override
    public Customer findByAccountId(int accountId) {

        try (
            Connection connection =ConnectionFactory.getConnectionFactory().getConnection();

            PreparedStatement statement =connection.prepareStatement(FIND_BY_ID_SQL)
        ) {

            statement.setInt(1,accountId);


            try (
                ResultSet resultSet =statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return new Customer(resultSet.getInt("account_id"),

                            resultSet.getInt("pin"),

                            resultSet.getDouble("balance")
                    );
                }


                return null;
            }

        } catch (SQLException e) {

            throw databaseError("Could not find account",e);
        }
    }


    // UPDATE BALANCE
    @Override
    public void updateBalance(int accountId,double newBalance) {

        try (
            Connection connection =ConnectionFactory.getConnectionFactory().getConnection();

            PreparedStatement statement =connection.prepareStatement(UPDATE_BALANCE_SQL)
        ) {

            statement.setDouble(1,newBalance);

            statement.setInt(2,accountId);


            int rowsUpdated =statement.executeUpdate();


            if (rowsUpdated == 0) {

                throw new IllegalArgumentException("Account not found.");
            }

        } catch (SQLException e) {

            throw databaseError(
                    "Could not update balance",
                    e
            );
        }
    }


    // TRANSFER MONEY
    @Override
    public void transferFunds(int fromAccountId,int toAccountId,double amount) {

        try (
            Connection connection =ConnectionFactory.getConnectionFactory().getConnection()
        ) {

            // ATOMMMOOOCITTTYYYYYYYYYYYYYYYYY
            connection.setAutoCommit(false);


            try (
                PreparedStatement withdrawStatement =connection.prepareStatement(WITHDRAW_TRANSFER_SQL);

                PreparedStatement depositStatement =connection.prepareStatement(DEPOSIT_TRANSFER_SQL)
            ) {

                // Remove money from sender
                withdrawStatement.setDouble(1,amount);

                withdrawStatement.setInt( 2, fromAccountId);


                int senderUpdated =withdrawStatement.executeUpdate();


                if (senderUpdated == 0) {

                    throw new IllegalArgumentException("Sender account not found.");
                }


                // Add money to receiver
                depositStatement.setDouble(1,amount);

                depositStatement.setInt(2,toAccountId);


                int receiverUpdated =depositStatement.executeUpdate();


                if (receiverUpdated == 0) {

                    throw new IllegalArgumentException(
                            "Receiving account not found."
                    );
                }


                // Both worked
                connection.commit();

            } catch (SQLException |RuntimeException e) {

                // Something failed.
                // Undo both changes.
                connection.rollback();

                throw e;
            }

        } catch (SQLException e) {

            throw databaseError("Could not transfer funds",e);
        }
    }


    // CREATE ACCOUNT TABLE
    private void initializeSchema() {

        try (
            Connection connection =ConnectionFactory.getConnectionFactory().getConnection();

            PreparedStatement statement =connection.prepareStatement(CREATE_ACCOUNT_TABLE_SQL)
        ) {

            statement.executeUpdate();

        } catch (SQLException e) {

            throw databaseError(
                    "Could not initialize account table",
                    e
            );
        }
    }


    private IllegalStateException databaseError(String message,SQLException cause) {

        return new IllegalStateException(message,cause);
    }
}