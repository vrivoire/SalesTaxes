package com.vrivoire.salestaxes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrivoire.salestaxes.model.Item;

import com.vrivoire.salestaxes.repository.ItemRepository;

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
