package com.teksystems.salestaxes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teksystems.salestaxes.model.Tax;

/**
 *
 * @author Vincent
 */
public interface TaxRepository extends JpaRepository<Tax, Long> {

}
