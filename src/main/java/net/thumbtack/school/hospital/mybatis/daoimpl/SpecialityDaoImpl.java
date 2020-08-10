package net.thumbtack.school.hospital.mybatis.daoimpl;

import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.dao.SpecialityDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SpecialityDaoImpl extends DaoImplBase implements SpecialityDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);

    @Override
    public Integer getByName(String name) throws ServerException {
        LOGGER.debug("DAO getByName");
        try (SqlSession sqlSession = getSession()) {
            try {
                Integer roomId = getSpecialityMapper(sqlSession).getByName(name);
                if (roomId == null) {
                    throw new ServerException(ErrorCode.SPECIALITY_NOT_FOUND, name);
                }
                return roomId;
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get by name: {}", name, e);
                throw new ServerException(ErrorCode.DATABASE_ERROR);
            }
        }
    }
}
