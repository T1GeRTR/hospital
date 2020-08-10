package net.thumbtack.school.hospital.mybatis.mappers;

import net.thumbtack.school.hospital.model.Session;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

public interface SessionMapper {
    @Insert({"INSERT INTO session (sessionId, userId) VALUES "
            + "(#{session.sessionId}, (SELECT id FROM user WHERE id = #{patientId}))"})
    void insertSession(@Param("session") Session session, @Param("patientId") int patientId);

    @Delete("DELETE FROM session")
    Integer clear();
}
