package net.thumbtack.school.hospital.mybatis.mappers;

import net.thumbtack.school.hospital.model.Comission;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.model.Patient;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ComissionMapper {
    //    @Insert(("INSERT INTO comission (ticket, timeStart, timeEnd, patientId, date, roomId) SELECT " +
//            "#{comission.ticket}, #{comission.date}, #{comission.timeStart}, #{comission.timeEnd}, #{patientId}, (SELECT id FROM room " +
//            "WHERE name = #{comission.room}) FROM comission WHERE #{patientId} NOT IN (SELECT patientId FROM comission WHERE date = #{comission.date}" +
//            " AND GREATEST(comission.timeStart, #{comission.timeStart}) < LEAST(comission.timeEnd, #{comission.timeEnd}) LIMIT 1)"))
    @Insert("INSERT INTO comission (ticket, date, timeStart, timeEnd, patientId, roomId) VALUES " +
            "(#{comission.ticket}, #{comission.date}, #{comission.timeStart}, #{comission.timeEnd}, #{patientId}, (SELECT id FROM room WHERE name = #{comission.room}))")
    @Options(useGeneratedKeys = true, keyProperty = "comission.id")
    void insertComission(@Param("patientId") int patientId, @Param("comission") Comission comission);

    @Insert({"<script>",
            "INSERT INTO comission_doctor (doctorId, comissionId) VALUES",
            "<foreach item='doctor' collection= 'list' separator=', '>",
            "(#{doctor.id}, #{comissionId})",
            "</foreach>",
            "</script>"})
    void insertComissionDoctor(@Param("comissionId") int comissionId, @Param("list") List<Doctor> doctors);

    void delete(String comission);

    @Select("SELECT comission.id as id, comission.ticket as ticket, comission.timeStart as timeStart, comission.timeEnd as timeEnd, comission.patientId as patientId, comission.date as date, room.name as room, comission.id as id, comission_doctor.doctorId as doctorId FROM comission, room, comission_doctor WHERE patientId = #{patientId} AND room.id = comission.roomId AND comission.id = comission_doctor.comissionId")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "patient", column = "patientId", javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.mybatis.mappers.PatientMapper.getByUserId", fetchType = FetchType.LAZY)),
            @Result(property = "appointments", column = "{date=date, doctorId=doctorId, timeStart=timeStart, timeEnd=timeEnd, patientId=patientId}", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.AppointmentMapper.getForComission", fetchType = FetchType.LAZY)),
            @Result(property = "doctors", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.DoctorMapper.getByComissionId", fetchType = FetchType.LAZY))
    })
    List<Comission> getByPatientId(int userId);

    @Insert("DELETE FROM comission WHERE id = #{id}")
    Integer deleteComission(int id);

    @Delete("DELETE FROM comission")
    Integer clear();

    @Delete("DELETE FROM comission_doctor")
    Integer clearComissionDoctor();
}
