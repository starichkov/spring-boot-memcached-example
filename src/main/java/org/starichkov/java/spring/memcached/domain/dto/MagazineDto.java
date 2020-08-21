package org.starichkov.java.spring.memcached.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Vadim Starichkov
 * @since 20.08.2020 18:20
 */
@NoArgsConstructor
@Getter
@Setter
public class MagazineDto implements Serializable {

    private Long id;
    private String title;
    private Integer issue;
}
