package com.vrivoire.salestaxes;

import com.vrivoire.salestaxes.controller.SalesTaxes;
import com.vrivoire.salestaxes.model.Item;
import com.vrivoire.salestaxes.model.Tax;
import com.vrivoire.salestaxes.repository.ItemRepository;
import com.vrivoire.salestaxes.repository.TaxRepository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author Vincent
 */
@SpringBootApplication
@EnableJpaRepositories
public class Main {

    private static final Logger LOG = LogManager.getLogger(Main.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOG.info("Java version: " + System.getProperty("java.runtime.version"));
        try {
            ConfigurableApplicationContext context = new SpringApplicationBuilder(Main.class).web(WebApplicationType.NONE).run(args);
            insertBaseData(context);

            SalesTaxes salesTaxes = new SalesTaxes(context);
            salesTaxes.start();
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
        LOG.info("-------------------- Creating data -------------------------");

        TaxRepository taxRepository = context.getBean(TaxRepository.class);
        Tax tax = new Tax("Basic sales tax", 10.00f, false);
        taxRepository.saveAndFlush(tax);
        LOG.info(tax);

        tax = new Tax("Import duty", 5.00f, true);
        taxRepository.saveAndFlush(tax);
        LOG.info(tax);

        ItemRepository itemRepository = context.getBean(ItemRepository.class);
        Item item = new Item("book", "Book", false, false, 12.49f);
        itemRepository.saveAndFlush(item);
        LOG.info(item);

        item = new Item("musicCD", "Music CD", false, true, 14.99f);
        itemRepository.saveAndFlush(item);
        LOG.info(item);

        item = new Item("chocolatebar", "Chocolate bar", false, false, 0.85f);
        itemRepository.saveAndFlush(item);
        LOG.info(item);

        item = new Item("importedboxofchocolates", "Imported box of chocolates", true, false, 10.00f);
        itemRepository.saveAndFlush(item);
        LOG.info(item);

        item = new Item("importedbottleofperfume1", "Imported bottle of perfume", true, true, 47.50f);
        itemRepository.saveAndFlush(item);
        LOG.info(item);

        item = new Item("importedbottleofperfume2", "Imported bottle of perfume", true, true, 27.99f);
        itemRepository.saveAndFlush(item);
        LOG.info(item);

        item = new Item("bottleofperfume", "Bottle of perfume", false, true, 18.99f);
        itemRepository.saveAndFlush(item);
        LOG.info(item);

        item = new Item("packetofheadachepills", "Packet of headache pills", false, false, 9.75f);
        itemRepository.saveAndFlush(item);
        LOG.info(item);

        item = new Item("boxofimportedchocolates", "Box of imported chocolates", true, false, 11.25f);
        itemRepository.saveAndFlush(item);
        LOG.info(item);

        LOG.info("---------------------------------------------");
    }
}
