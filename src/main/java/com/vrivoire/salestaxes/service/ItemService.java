package com.vrivoire.salestaxes.service;

import java.util.Map;

import com.vrivoire.salestaxes.model.Item;

/**
 *
 * @author Vincent
 */
public interface ItemService {

    Map<String, Item> loadItems();
}
