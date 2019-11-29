package com.vrivoire.salestaxes;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.beans.BeansException;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.vrivoire.salestaxes.model.Item;
import com.vrivoire.salestaxes.model.Tax;

import com.vrivoire.salestaxes.repositories.ItemRepository;
import com.vrivoire.salestaxes.repositories.TaxRepository;

/**
 *
 * @author Vincent
 */
@SpringBootApplication
@EnableJpaRepositories
public class Main {

    private static final Logger LOG = LogManager.getLogger(Main.class);
    private static TaxRepository taxRepository = null;
    private static ItemRepository itemRepository = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOG.info("Java version: " + System.getProperty("java.runtime.version"));
        try {
            ConfigurableApplicationContext context = new SpringApplicationBuilder(Main.class).web(WebApplicationType.NONE).run(args);
            insertBaseData(context);
            LOG.info("---------------------------------------------");
            SalesTaxes salesTax = new SalesTaxes(context);
            salesTax.start();
        } catch (Exception ex) {
            LOG.fatal(ex.getMessage(), ex);
            System.exit(-1);
        }
    }

    /**
     *
     * @param context
     * @throws Exception
     */
    public static void insertBaseData(ConfigurableApplicationContext context) throws Exception {

        try {
            taxRepository = context.getBean(TaxRepository.class);
            Tax tax = new Tax("Basic sales tax", 10.00f, false);
            taxRepository.saveAndFlush(tax);
            tax = new Tax("Import duty", 5.00f, true);
            taxRepository.saveAndFlush(tax);

            itemRepository = context.getBean(ItemRepository.class);
            Item item = new Item("book", "Book", false, false, 12.49f);
            itemRepository.saveAndFlush(item);

            item = new Item("music CD", "Music CD", false, true, 14.99f);
            itemRepository.saveAndFlush(item);

            item = new Item("chocolate bar", "Chocolate bar", false, false, 0.85f);
            itemRepository.saveAndFlush(item);

            item = new Item("imported box of chocolates", "Imported box of chocolates", true, false, 10.00f);
            itemRepository.saveAndFlush(item);

            item = new Item("imported bottle of perfume1", "Imported bottle of perfume", true, true, 47.50f);
            itemRepository.saveAndFlush(item);

            item = new Item("imported bottle of perfume2", "Imported bottle of perfume", true, true, 27.99f);
            itemRepository.saveAndFlush(item);

            item = new Item("bottle of perfume", "Bottle of perfume", false, true, 18.99f);
            itemRepository.saveAndFlush(item);

            item = new Item("packet of headache pills", "Packet of headache pills", false, false, 9.75f);
            itemRepository.saveAndFlush(item);

            item = new Item("box of imported chocolates", "Box of imported chocolates", true, false, 11.25f);
            itemRepository.saveAndFlush(item);

        } catch (BeansException ex) {
            LOG.error("Failed to create some data.", ex);
            throw ex;
        }
    }
}
