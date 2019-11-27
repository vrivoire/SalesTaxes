package com.teksystems.salestaxes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teksystems.salestaxes.model.Tax;
import com.teksystems.salestaxes.repositories.TaxRepository;

/**
 *
 * @author Vincent
 */
@Service
public class TaxServiceImpl implements TaxService {

    @Autowired
    private TaxRepository taxRepository;

    @Override
    public List<Tax> loadAll() {
        return taxRepository.findAll();
    }

}
