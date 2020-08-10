package net.thumbtack.school.hospital.mybatis.mappers;

import net.thumbtack.school.hospital.model.Admin;
import org.apache.ibatis.annotations.*;

public interface AdminMapper {

    @Insert({"INSERT INTO admin (position, userId) VALUES "
            + "(#{admin.position}, #{admin.id})"})
    Integer insertAdmin(@Param("admin") Admin admin);

    @Update("UPDATE admin SET position = #{admin.position} " +
            "WHERE userId = #{admin.id}")
    void updateAdmin(@Param("admin") Admin admin);

    @Select("SELECT admin.userId AS id, user.login AS login, user.password AS password, user.firstName AS firstName, " +
            "user.lastName AS lastName, user.patronymic AS patronymic, admin.position AS position FROM " +
            "admin LEFT JOIN user ON admin.userId = user.id WHERE admin.userId = #{userId}")
    Admin getByUserId(@Param("userId") int userId);

    @Delete("DELETE FROM admin WHERE userId != 1")
    Integer clear();
}
