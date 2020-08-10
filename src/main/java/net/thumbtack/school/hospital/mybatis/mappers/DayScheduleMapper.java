package net.thumbtack.school.hospital.mybatis.mappers;

import net.thumbtack.school.hospital.model.DaySchedule;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.time.LocalDate;
import java.util.List;

public interface DayScheduleMapper {
    @Insert({"<script>",
            "INSERT INTO day_schedule (date, doctorId) VALUES",
            "<foreach item='item' collection= 'list' separator=', '>",
            "(#{item.date}, #{doctorId})",
            "</foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "list.id")
    void insertDaySchedule(@Param("list") List<DaySchedule> list, @Param("doctorId") int doctorId);

    @Select("SELECT id, date, doctorId FROM day_schedule WHERE doctorId = #{doctorId} AND date >= #{dateStart} AND date <= #{dateEnd}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "appointments", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.AppointmentMapper.getByScheduleId", fetchType = FetchType.LAZY))})
    List<DaySchedule> getByDoctorIdWithDate(@Param("doctorId") int doctorId, @Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);

    @Select("SELECT id, date, doctorId FROM day_schedule WHERE doctorId = #{doctorId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "appointments", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.AppointmentMapper.getByScheduleId", fetchType = FetchType.LAZY))})
    List<DaySchedule> getByDoctorId(int doctorId);

    @Select("SELECT id, date FROM day_schedule WHERE id = (SELECT day_scheduleId FROM appointment WHERE id = #{appointmentId}) AND doctorId = #{doctorId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "appointments", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.AppointmentMapper.getByScheduleId", fetchType = FetchType.LAZY))})
    DaySchedule getByAppointmentId(@Param("doctorId")int doctorId, @Param("appointmentId") int appointmentId);

    @Select("SELECT id, date FROM day_schedule WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "appointments", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mybatis.mappers.AppointmentMapper.getByScheduleId", fetchType = FetchType.LAZY))})
    DaySchedule getById(int id);

    @Delete("DELETE from day_schedule WHERE date >= #{dateStart} AND date <= #{dateEnd} AND doctorId = #{doctorId}")
    void deleteDaySchedule(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd, @Param("doctorId") int doctorId);

    @Delete("DELETE FROM day_schedule")
    Integer clear();
}
