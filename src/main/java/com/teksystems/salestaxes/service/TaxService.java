package com.teksystems.salestaxes.service;

import java.util.List;

import com.teksystems.salestaxes.model.Tax;

/**
 *
 * @author Vincent
 */
public interface TaxService {

    List<Tax> loadAll();
}
