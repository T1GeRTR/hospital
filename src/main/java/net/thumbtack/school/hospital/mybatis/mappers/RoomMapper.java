package net.thumbtack.school.hospital.mybatis.mappers;

import org.apache.ibatis.annotations.Select;

public interface RoomMapper {
    @Select("SELECT id FROM room WHERE name = #{name}")
    Integer getByName(String name);
}
