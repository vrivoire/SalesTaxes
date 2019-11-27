/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teksystems.salestaxes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teksystems.salestaxes.model.Item;
import com.teksystems.salestaxes.repositories.ItemRepository;

/**
 *
 * @author Vincent
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Map<String, Item> loadItems() {
        List<Item> findAll = itemRepository.findAll();
        Map<String, Item> items = new HashMap<>();
        findAll.forEach((item) -> {
            items.put(item.getName(), item);
        });
        return items;
    }

}
