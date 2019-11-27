package com.teksystems.salestaxes.service;

import java.util.Map;

import com.teksystems.salestaxes.model.Item;

/**
 *
 * @author Vincent
 */
public interface ItemService {

    Map<String, Item> loadItems();
}
