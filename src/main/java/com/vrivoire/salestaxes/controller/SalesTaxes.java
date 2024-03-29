package com.vrivoire.salestaxes.controller;

import com.vrivoire.salestaxes.model.Item;
import com.vrivoire.salestaxes.model.Sale;
import com.vrivoire.salestaxes.model.SaleLine;
import com.vrivoire.salestaxes.model.Tax;
import com.vrivoire.salestaxes.service.ItemService;
import com.vrivoire.salestaxes.service.TaxService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author VincentRivoire
 */
@EnableJpaRepositories
public class SalesTaxes {

    private static final Log LOG = LogFactory.getLog(SalesTaxes.class);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("####0.00");
    private static final String SCRIPT = "/salesTaxes.properties";
    private TaxService taxService;
    private ItemService itemService;

    // Package for unit tests
    SalesTaxes() {
    }

    public SalesTaxes(ConfigurableApplicationContext context) {
        taxService = context.getBean(TaxService.class);
        itemService = context.getBean(ItemService.class);
    }

    public void start() {
        try {
            Tax tax = null;
            Tax dutyTax = null;
            List<Tax> taxes = taxService.loadAll();
            for (Tax taxe : taxes) {
                if (taxe.isImported()) {
                    dutyTax = taxe;
                } else {
                    tax = taxe;
                }
            }

            Map<String, Item> items = itemService.loadItems();

            Properties properties = loadScript();

            String output = runScript(properties, tax, dutyTax, items);

            LOG.info(output);

        } catch (IOException ex) {
            LOG.error(ex, ex);
        }
    }

    private Properties loadScript() throws IOException {
        LOG.info("Loading script at " + SCRIPT);
        Properties properties = new Properties();
        properties.load(SalesTaxes.class.getResourceAsStream(SCRIPT));
        return properties;
    }

    private String runScript(Properties properties, Tax tax, Tax dutyTax, Map<String, Item> items) {
        LOG.info("Running script");

        StringBuilder in = new StringBuilder();
        StringBuilder out = new StringBuilder();

        for (int count = 1; count < properties.size() + 1; count++) {
            String property = properties.getProperty("basket" + count);
            StringTokenizer stringTokenizer = new StringTokenizer(property, ",");
            Sale sale = new Sale(tax, dutyTax);
            while (stringTokenizer.hasMoreTokens()) {
                int quantity = Integer.decode(stringTokenizer.nextToken());
                String itemName = stringTokenizer.nextToken();
                sale.addLine(quantity, items.get(itemName));
            }
            in.append(getInputText(sale, count));
            out.append(getOutputText(sale, count));
        }
        in.append("-----------------------------------------------------------");
        return in.toString() + out.toString();
    }

    private String getInputText(Sale sale, int count) {
        StringBuilder sb = new StringBuilder().append('\n');
        sb.append("Input ").append(count).append(":").append('\n');
        for (SaleLine saleLine : sale) {
            Item item = saleLine.getItem();
            sb.append(saleLine.getQuantity())
                    .append(" ")
                    .append(item.getDescription())
                    .append(" at ")
                    .append(formatNumber(item.getPrice()))
                    .append('\n');
        }
        return sb.toString();
    }

    private String getOutputText(Sale sale, int count) {
        StringBuilder sb = new StringBuilder().append('\n');
        sb.append("Output ").append(count).append(":\n");
        float salesTaxes = 0;
        float total = 0;
        for (SaleLine saleLine : sale) {

            int quantity = saleLine.getQuantity();
            Item item = saleLine.getItem();

            float finalItemPrice = item.getPrice() * quantity;
            float taxRate = getTaxRate(sale, item);

            float salesTax = finalItemPrice * (taxRate / 100.00f);
            salesTaxes += salesTax;
            salesTaxes = roundUpto5(salesTaxes);
            finalItemPrice = finalItemPrice + salesTax;
            if (item.isImported()) {
                finalItemPrice = roundUpto5(finalItemPrice);
            }

            total += finalItemPrice;
            sb.append(quantity).append(" ").append(item.getDescription()).append(": ").append(formatNumber(round(finalItemPrice, 2))).append('\n');
        }
        sb.append("Sales Taxes: ").append(formatNumber(salesTaxes)).append(' ');
        sb.append("Total: ").append(formatNumber(round(total, 2))).append('\n');
        return sb.toString();
    }

    public float getTaxRate(Sale sale, Item item) {
        float taxRate = 0.00f;
        if (item.isTaxable() && item.isImported()) {
            taxRate = sale.getBasicSalesTax().getRate() + sale.getDutyTax().getRate();
        } else if (item.isTaxable() && !item.isImported()) {
            taxRate = sale.getBasicSalesTax().getRate();
        } else if (!item.isTaxable() && item.isImported()) {
            taxRate = sale.getDutyTax().getRate();
            //} else if (!item.isTaxable() && !item.isImported()) {
            // no taxes
        }
        return taxRate;
    }

    // Package for unit tests
    String formatNumber(float number) {
        return DECIMAL_FORMAT.format(number);
    }

    // Package for unit tests
    float round(float value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    // Package for unit tests
    float roundUpto5(float num) {
        num *= 100;
        num = (int) num;
        num = (float) Math.ceil(num / 5.00f) * 5.00f;
        num = (int) num;
        num /= 100;
        return num;
    }
}
