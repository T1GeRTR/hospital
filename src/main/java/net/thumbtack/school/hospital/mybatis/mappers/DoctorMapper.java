package net.thumbtack.school.hospital.mybatis.mappers;

import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.view.DoctorView;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.time.LocalDate;
import java.util.List;

public interface DoctorMapper {

    @Insert({"INSERT INTO doctor (specialityId, roomId, userId) SELECT" +
            " (SELECT id FROM `speciality` WHERE name = #{doctor.speciality}) ," +
            " (SELECT id FROM `room` WHERE name = #{doctor.room})," +
            " #{doctor.id}"})
    void insertDoctor(@Param("doctor") Doctor doctor);

    @Select("SELECT doctor.userId as id, user.login AS login, user.password AS password, user.firstName AS firstName, " +
            "user.lastName AS lastName, user.patronymic AS patronymic, speciality.name AS speciality, " +
            "room.name AS room, user.type AS type, MIN(day_schedule.date) AS dateStart, MAX(day_schedule.date) AS dateEnd FROM user, doctor, speciality, " +
            "room, day_schedule WHERE doctor.userId = #{doctorId} AND user.id = doctor.userId AND speciality.id " +
            "= doctor.specialityId AND room.id = doctor.roomId AND day_schedule.date >= #{dateStart} AND day_schedule.date <= #{dateEnd}")
    @Results({
            @Result(property = "schedule", column = "{doctorId=id, dateStart=dateStart, dateEnd=dateEnd}", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.DayScheduleMapper.getByDoctorIdWithDate", fetchType = FetchType.LAZY))
    })
    DoctorView getByIdWithSchedule(@Param("doctorId") int id, @Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);

    @Select({"<script>",
            "SELECT doctor.userId AS id, user.login AS login, user.password AS password, user.firstName AS firstName," +
                    "user.lastName AS lastName, user.patronymic AS patronymic, speciality.name AS speciality," +
                    "room.name AS room,  user.type AS type, (SELECT MIN(date) FROM day_schedule WHERE doctorId = doctor.userId AND date >= #{dateStart}) AS dateStart, (SELECT MAX(date) " +
                    "FROM day_schedule WHERE doctorId = doctor.userId AND date &lt;= #{dateEnd}) AS dateEnd FROM user LEFT JOIN doctor ON user.id = doctor.userId LEFT " +
                    "JOIN speciality ON speciality.id = doctor.specialityId LEFT JOIN room ON room.id = doctor.roomId LEFT JOIN day_schedule " +
                    "ON day_schedule.doctorId = doctor.userId WHERE " +
                    "day_schedule.date >= #{dateStart} " +
                    "AND day_schedule.date &lt;= #{dateEnd} " +
                    "<if test ='speciality != null'>AND speciality.name = #{speciality} " +
                    "</if>",
            "GROUP BY id </script>"})
    @Results({
            @Result(property = "schedule", column = "{doctorId=id, dateStart=dateStart, dateEnd=dateEnd}", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.DayScheduleMapper.getByDoctorIdWithDate", fetchType = FetchType.LAZY))
    })
    List<DoctorView> getAllDoctorsWithParams(@Param("speciality") String speciality, @Param("dateStart") LocalDate date, @Param("dateEnd") LocalDate dateEnd);

    @Delete("DELETE FROM day_schedule WHERE doctorId = #{id} AND date >= #{date}")
    Integer delete(@Param("id") int id, @Param("date") LocalDate date);

    @Select("SELECT doctor.userId AS id, user.login AS login, user.password AS password, user.firstName AS firstName, " +
            "user.lastName AS lastName, user.patronymic AS patronymic, speciality.name AS speciality, " +
            "room.name AS room, user.type AS type, doctor.userId as id, appointment.id as appointmentId FROM user, doctor, speciality, " +
            "room, appointment WHERE doctor.userId = (SELECT doctorId FROM day_schedule WHERE id = (SELECT day_scheduleId FROM appointment " +
            "WHERE id = #{appointmentId})) AND user.id = doctor.userId AND speciality.id = doctor.specialityId AND room.id = doctor.roomId AND appointment.id = #{appointmentId}")
    @Results({
            @Result(property = "schedule", column = "{doctorId=id, appointmentId=appointmentId}", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.DayScheduleMapper.getByAppointmentId", fetchType = FetchType.LAZY))
    })
    Doctor getByAppointmentId(int appointmentId);

    @Select("SELECT doctor.userId AS id, doctor.id AS doctorId, user.login AS login, user.password AS password, user.firstName AS firstName, " +
            "user.lastName AS lastName, user.patronymic AS patronymic, speciality.name AS speciality, " +
            "room.name AS room, user.type AS type FROM doctor LEFT JOIN user ON doctor.userId = user.id LEFT JOIN " +
            "room ON doctor.roomId = room.id, speciality WHERE doctor.id IN (SELECT doctorId FROM comission_doctor WHERE comissionId = #{comissionId})")
    @Results({
            @Result(property = "schedule", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.DayScheduleMapper.getByDoctorId", fetchType = FetchType.LAZY))
    })
    List<Doctor> getByComissionId(int comissionId);

    @Select("SELECT doctor.userId AS id, doctor.userId AS doctorId, user.login AS login, user.password AS password, user.firstName AS firstName, " +
            "user.lastName AS lastName, user.patronymic AS patronymic, speciality.name AS speciality, " +
            "room.name AS room, user.type AS type FROM user, doctor, speciality, " +
            "room WHERE doctor.userId = #{userId} AND user.id = doctor.userId AND speciality.id " +
            "= doctor.specialityId AND room.id = doctor.roomId")
    @Results({
            @Result(property = "schedule", column = "doctorId", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.DayScheduleMapper.getByDoctorId", fetchType = FetchType.LAZY))
    })
    Doctor getByUserId(int userId);

    @Delete("DELETE FROM doctor")
    Integer clear();
}
