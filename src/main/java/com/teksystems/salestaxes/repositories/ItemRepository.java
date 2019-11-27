package com.teksystems.salestaxes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teksystems.salestaxes.model.Item;

/**
 *
 * @author Vincent
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

}
