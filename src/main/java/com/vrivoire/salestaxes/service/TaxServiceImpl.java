package com.vrivoire.salestaxes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrivoire.salestaxes.model.Tax;

import com.vrivoire.salestaxes.repository.TaxRepository;

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
