package net.thumbtack.school.hospital.mybatis.mappers;

import net.thumbtack.school.hospital.model.Patient;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface PatientMapper {

    @Insert({"INSERT INTO patient (email, address, phone, userId) VALUES "
            + "( #{patient.email}, #{patient.address}, #{patient.phone}, #{patient.id})"})
    @Results({
            @Result(property = "id", column = "userId"),
            @Result(property = "appointments", column = "userId", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.AppointmentMapper.getByPatientId", fetchType = FetchType.LAZY)),
            @Result(property = "comissions", column = "userId", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.ComissionMapper.getByPatientId", fetchType = FetchType.LAZY))
    })
    void insertPatient(@Param("patient") Patient patient);

    @Update({"UPDATE patient SET email = #{patient.email}, address = #{patient.address}, phone = #{patient.phone} WHERE userId = #{patient.id}"})
    void updatePatient(@Param("patient") Patient patient);

    @Select("SELECT patient.userId AS userId, user.login AS login, user.password AS password, user.firstName AS firstName, " +
            "user.lastName AS lastName, user.patronymic AS patronymic, patient.email AS email, patient.address AS address, " +
            "patient.phone AS phone, user.type AS type FROM patient LEFT JOIN user ON patient.userId = user.id WHERE patient.userId = #{userId}")
    @Results({
            @Result(property = "id", column = "userId"),
            @Result(property = "appointments", column = "userId", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.AppointmentMapper.getByPatientId", fetchType = FetchType.LAZY)),
            @Result(property = "comissions", column = "userId", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.ComissionMapper.getByPatientId", fetchType = FetchType.LAZY))
    })
    Patient getByUserId(@Param("userId") int userId);

    @Delete("DELETE FROM patient")
    Integer clear();
}
