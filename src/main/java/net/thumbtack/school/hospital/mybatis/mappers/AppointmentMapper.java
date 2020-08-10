package net.thumbtack.school.hospital.mybatis.mappers;

import net.thumbtack.school.hospital.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentMapper {
    @Insert({"<script>",
            "INSERT INTO appointment (ticket, timeStart, timeEnd, patientId, day_scheduleId) VALUES",
            "<foreach item='appointment' collection= 'list' separator=', '>",
            "(#{appointment.ticket}, #{appointment.timeStart}, #{appointment.timeEnd}, null, #{day_scheduleId})",
            "</foreach>",
            "</script>"})
    @Results({
            @Result(property = "patient", column = "patientId", javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.mybatis.mappers.PatientMapper.getByUserId", fetchType = FetchType.LAZY)),
            @Result(property = "daySchedule", column = "day_scheduleId", javaType = DaySchedule.class,
                    one = @One(select = "net.thumbtack.school.hospital.mybatis.mappers.DayScheduleMapper.getById", fetchType = FetchType.LAZY))
    })
    Integer insertAppointments(@Param("list") List<Appointment> list, @Param("day_scheduleId") int day_scheduleId);

    @Select("SELECT id, ticket, timeStart, timeEnd, appointmentState, patientId FROM appointment WHERE day_scheduleId = #{day_scheduleId}")
    @Results({
            @Result(property = "patient", column = "patientId", javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.mybatis.mappers.PatientMapper.getByUserId", fetchType = FetchType.LAZY)),
            @Result(property = "daySchedule", column = "day_scheduleId", javaType = DaySchedule.class,
                    one = @One(select = "net.thumbtack.school.hospital.mybatis.mappers.DayScheduleMapper.getById", fetchType = FetchType.LAZY))
    })
    List<Appointment> getByScheduleId(int day_scheduleId);

    @Update({"UPDATE appointment SET appointmentState = 'TICKET', patientId = #{patientId} WHERE id = #{id} AND appointmentState = 'FREE'"})
    Integer update(@Param("id") int id, @Param("patientId") int patientId);

    @Select({"<script>",
            "SELECT appointment.id as id, appointment.ticket AS ticket, appointment.timeStart AS timeStart, appointment.timeEnd AS timeEnd, appointment.patientId AS patientId, " +
                    "appointment.appointmentState AS appointmentState, day_schedule.doctorId AS doctorId, day_schedule.date AS date FROM appointment LEFT JOIN " +
                    "day_schedule ON day_schedule.id = appointment.day_scheduleId LEFT JOIN doctor ON doctor.Id = day_schedule.doctorId" +
                    "<where>" +
                    "<if test ='time != null'> AND appointment.timeStart = #{time}",
            "</if>",
            "<if test ='true'> AND appointment.appointmentState = 'FREE'",
            "</if>",
            "<if test ='doctorId != null'> AND day_schedule.doctorId = #{doctorId}" +
                    "</if>",
            "<if test ='speciality != null'> AND doctor.specialityId = (SELECT id FROM speciality WHERE name = #{speciality})" +
                    "</if>",
            "<if test ='date != null'> AND day_schedule.date = #{date}",
            "</if>",
            "</where>" + "</script>"})
    List<Appointment> getWithParams(@Param("doctorId") Integer doctorId, @Param("speciality") String speciality, @Param("date") LocalDate date, @Param("time") LocalTime time);

    @Delete("UPDATE appointment SET appointmentState = 'FREE', patientId = null WHERE id = #{id} AND (patientId = " +
            "#{userId} OR #{userId} IN (SELECT userId FROM admin))")
    Integer delete(@Param("id") int id, @Param("userId") int userId);

    @Select("SELECT * FROM appointment WHERE patientId = #{patientId} AND appointmentState = 'TICKET'")
    @Results({
            @Result(property = "patient", column = "patientId", javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.mybatis.mappers.PatientMapper.getByUserId", fetchType = FetchType.LAZY)),
            @Result(property = "daySchedule", column = "day_scheduleId", javaType = DaySchedule.class,
                    one = @One(select = "net.thumbtack.school.hospital.mybatis.mappers.DayScheduleMapper.getById", fetchType = FetchType.LAZY))
    })
    List<Appointment> getByPatientId(int patientId);

    @Select("SELECT * FROM appointment WHERE id = #{id}")
    @Results({
            @Result(property = "patient", column = "patientId", javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.mybatis.mappers.PatientMapper.getByUserId", fetchType = FetchType.LAZY)),
            @Result(property = "daySchedule", column = "day_scheduleId", javaType = DaySchedule.class,
                    one = @One(select = "net.thumbtack.school.hospital.mybatis.mappers.DayScheduleMapper.getById", fetchType = FetchType.LAZY))
    })
    Appointment getById(int id);

    @Select("SELECT * FROM appointment WHERE day_scheduleId IN (SELECT id FROM day_schedule WHERE doctorId = #{doctorId} " +
            "AND date = #{date}) AND GREATEST(#{timeStart}, timeStart) < LEAST(#{timeEnd}, timeEnd)")
    @Results({
            @Result(property = "patient", column = "patientId", javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.mybatis.mappers.PatientMapper.getByUserId", fetchType = FetchType.LAZY)),
            @Result(property = "daySchedule", column = "day_scheduleId", javaType = DaySchedule.class,
                    one = @One(select = "net.thumbtack.school.hospital.mybatis.mappers.DayScheduleMapper.getById", fetchType = FetchType.LAZY))
    })
    List<Appointment> getForComission(@Param("date") LocalDate date, @Param("doctorId") Integer doctorId, @Param("timeStart") LocalTime timeStart, @Param("timeEnd") LocalTime timeEnd, @Param("patientId") int patientId);

    @Update({"<script>",
            "UPDATE appointment SET appointmentState = 'BUSY', patientId = #{patientId} WHERE appointmentState = 'FREE' AND id IN",
            "<foreach item='item' index='index' collection='list'" +
                    " open='(' separator=',' close=')'>" +
                    " #{item}" +
                    "</foreach>" +
            "</script>"})
    Integer updateBusy(@Param("list") List<Integer> list, @Param("patientId") int patientId);

    @Update("UPDATE appointment SET appointmentState = 'FREE', patientId = null WHERE patientId = #{patientId} AND appointmentState = 'BUSY' AND day_scheduleId IN (SELECT id FROM day_schedule WHERE doctorId IN (SELECT doctorId FROM comission_doctor WHERE comissionId = #{id}) " +
            "AND date = (SELECT date FROM comission WHERE id = #{id}))")
    Integer updateFree(@Param("patientId") int patientId, @Param("id") int id);

    @Delete("DELETE FROM appointment")
    Integer clear();
}
