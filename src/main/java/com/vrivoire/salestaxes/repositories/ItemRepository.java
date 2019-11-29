package com.vrivoire.salestaxes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vrivoire.salestaxes.model.Item;

/**
 *
 * @author Vincent
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

}
