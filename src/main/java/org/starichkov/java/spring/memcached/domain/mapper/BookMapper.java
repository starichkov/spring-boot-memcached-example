package org.starichkov.java.spring.memcached.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.starichkov.java.spring.memcached.domain.entity.Book;
import org.starichkov.java.spring.memcached.domain.dto.BookDto;

/**
 * @author Vadim Starichkov
 * @since 20.08.2020 18:26
 */
@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDto map(Book book);

    Book map(BookDto dto);
}
