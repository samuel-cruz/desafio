package br.com.meta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author samuel-cruz
 *
 * @param <E> Entity
 */
@NoRepositoryBean
public interface BaseRepository<E> extends JpaRepository<E, Integer> {

}