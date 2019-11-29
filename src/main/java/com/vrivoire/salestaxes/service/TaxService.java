package com.vrivoire.salestaxes.service;

import java.util.List;

import com.vrivoire.salestaxes.model.Tax;

/**
 *
 * @author Vincent
 */
public interface TaxService {

    List<Tax> loadAll();
}
