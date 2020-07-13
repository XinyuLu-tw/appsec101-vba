package com.tw.appsec101.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {

    private final Connection connection;

    public UserRepo(Connection connection) {
        this.connection = connection;
    }

    public void addName(String username, String password) throws Exception {
        final String query = "insert into users values (?, ?)";
        final PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.execute();
    }

    public LoginResult login(String username, String password) throws SQLException {
        final String query = "select * from users where vendor_name = '" + username + "' and password = '" + password + "'";

        final ResultSet resultSet = connection.createStatement().executeQuery(query);

        if (resultSet.next()) {
            return LoginResult.success(resultSet.getString(1));
        } else {
            return LoginResult.failed();
        }
    }

    public static class LoginResult {

        public final Boolean success;
        public final String username;

        private LoginResult(Boolean success, String username) {
            this.success = success;
            this.username = username;
        }

        public static LoginResult success(String vendorName) {
            return new LoginResult(true, vendorName);
        }

        public static LoginResult failed() {
            return new LoginResult(false, null);
        }
    }
}
