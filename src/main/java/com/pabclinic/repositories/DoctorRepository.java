package com.pabclinic.repositories;


import com.pabclinic.configurations.DataBase;
import com.pabclinic.model.dtos.DoctorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DoctorRepository {

    private DataBase dataBase;

    @Autowired
    public DoctorRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public List<DoctorDTO> getDoctors() {

        List<DoctorDTO> doctors = new ArrayList<>();

        try {
            dataBase.connectToDb();

            ResultSet rs = dataBase.getStmt().executeQuery("select * from users where role='DOCTOR'");

            while (rs.next()) {
                DoctorDTO doctor = new DoctorDTO(
                        rs.getInt("user_id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("specialisation"));

                doctors.add(doctor);
            }

            dataBase.disconnectDB();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return doctors;
    }

    public void addDoctor(DoctorDTO doctor) {

        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwd = bcryptPasswordEncoder.encode(doctor.getPassword());

        try {
            dataBase.connectToDb();

            String queryCount = "SELECT COUNT(*) from users";

            ResultSet rs = dataBase.getStmt().executeQuery(queryCount);
            rs.next();

            int i = rs.getInt("count");

            String queryInsert = "insert into users (firstName, lastName, username, password, specialisation, role) values (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = dataBase.getConn().prepareStatement(queryInsert);

            preparedStatement.setString(1, doctor.getFirstName());
            preparedStatement.setString(2, doctor.getLastName());
            preparedStatement.setString(3, doctor.getLogin());
            preparedStatement.setString(4, pwd);
            preparedStatement.setString(5, doctor.getSpecialisation());
            preparedStatement.setString(6, "DOCTOR");

            preparedStatement.executeUpdate();

            dataBase.disconnectDB();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeDoctor(String login) {

        try {
            dataBase.connectToDb();

            String queryRemove = "delete from users where username=?";

            PreparedStatement preparedStatement = dataBase.getConn().prepareStatement(queryRemove);

            preparedStatement.setString(1, login);

            preparedStatement.executeUpdate();

            dataBase.disconnectDB();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public DoctorDTO findDoctor(DoctorDTO doctor) {

        try {
            dataBase.connectToDb();

            String queryEdit = "select * from users where user_id=" + doctor.getDoctor_ID();

            ResultSet rs = dataBase.getStmt().executeQuery(queryEdit);

            while (rs.next()) {
                doctor = new DoctorDTO(rs.getInt("user_id"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("username"), rs.getString("password"), rs.getString("specialisation"));
            }

            dataBase.disconnectDB();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return doctor;
    }

    public void updateDoctor(DoctorDTO doctor) {

        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwd = bcryptPasswordEncoder.encode(doctor.getPassword());

        try {
            dataBase.connectToDb();

            String queryUpdate = "update users set firstname=?, lastname=?, username=?, password=?, specialisation=? where user_id=" + doctor.getDoctor_ID();

            PreparedStatement preparedStatement = dataBase.getConn().prepareStatement(queryUpdate);

            preparedStatement.setString(1, doctor.getFirstName());
            preparedStatement.setString(2, doctor.getLastName());
            preparedStatement.setString(3, doctor.getLogin());
            preparedStatement.setString(4, pwd);
            preparedStatement.setString(5, doctor.getSpecialisation());

            preparedStatement.executeUpdate();

            dataBase.disconnectDB();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public DoctorDTO findDoctorByUsernameFromDb(String login) {

        DoctorDTO doctor= null;

        try {
            dataBase.connectToDb();

            String queryEdit = "select * from users where username='" + login + "'";

            ResultSet rs = dataBase.getStmt().executeQuery(queryEdit);

            while (rs.next()) {
                doctor = new DoctorDTO(rs.getInt("user_id"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("username"), rs.getString("password"), rs.getString("specialisation"));
            }

            dataBase.disconnectDB();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return doctor;
    }


}
