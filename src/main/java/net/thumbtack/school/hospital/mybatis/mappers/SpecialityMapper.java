package net.thumbtack.school.hospital.mybatis.mappers;

import org.apache.ibatis.annotations.Select;

public interface SpecialityMapper {
    @Select("SELECT id FROM speciality WHERE name = #{name}")
    Integer getByName(String name);
}
