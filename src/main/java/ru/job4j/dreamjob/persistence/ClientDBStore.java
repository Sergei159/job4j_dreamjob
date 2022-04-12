package ru.job4j.dreamjob.persistence;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientDBStore {

    private final BasicDataSource pool;

    public ClientDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM CLIENT")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Client client = setPostData(it);
                    clients.add(client);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }


    public Optional<Client> add(Client client) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO CLIENT(name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getEmail());
            ps.setString(3, client.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    client.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            client = null;
        }
        return Optional.ofNullable(client);
    }

    public void update(Client client) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "UPDATE CLIENT SET name = ?, email = ? WHERE id = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getEmail());
            ps.setInt(3, client.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Client findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM CLIENT WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return setPostData(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Client> findUserByEmailAndPwd(String email, String password) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "SELECT * FROM CLIENT WHERE email = ? AND password = ?")
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.ofNullable(setPostData(it));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Client setPostData(ResultSet resultSet) throws SQLException {
        return new Client(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
    }
}
