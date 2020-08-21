package org.starichkov.java.spring.memcached.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.starichkov.java.spring.memcached.domain.entity.Magazine;
import org.starichkov.java.spring.memcached.domain.dto.MagazineDto;

/**
 * @author Vadim Starichkov
 * @since 20.08.2020 18:29
 */
@Mapper
public interface MagazineMapper {

    MagazineMapper INSTANCE = Mappers.getMapper(MagazineMapper.class);

    MagazineDto map(Magazine magazine);

    Magazine map(MagazineDto dto);
}
