package com.vrivoire.salestaxes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vrivoire.salestaxes.model.Tax;

/**
 *
 * @author Vincent
 */
public interface TaxRepository extends JpaRepository<Tax, Long> {

}
