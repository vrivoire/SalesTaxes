package com.teksystems.salestaxes;

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

import com.teksystems.salestaxes.model.Item;
import com.teksystems.salestaxes.model.Sale;
import com.teksystems.salestaxes.model.SaleLine;
import com.teksystems.salestaxes.model.Tax;
import com.teksystems.salestaxes.service.ItemService;
import com.teksystems.salestaxes.service.TaxService;

/**
 *
 * @author VincentRivoire
 */
@EnableJpaRepositories
class SalesTax {

    private static final Log LOGGER = LogFactory.getLog(SalesTax.class);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("####0.00");
    private TaxService taxService;
    private ItemService itemService;

    // For unit tests
    SalesTax() {
    }

    public SalesTax(ConfigurableApplicationContext context) {
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

            LOGGER.info(output);

        } catch (IOException ex) {
            LOGGER.error(ex, ex);
        }
    }

    private Properties loadScript() throws IOException {
        Properties properties = new Properties();
        properties.load(SalesTax.class.getResourceAsStream("/salesTaxes.properties"));
        return properties;
    }

    private String runScript(Properties properties, Tax tax, Tax dutyTax, Map<String, Item> items) throws NumberFormatException {
        StringBuilder sb = new StringBuilder();
        for (int count = 1; count < properties.size() + 1; count++) {
            String property = properties.getProperty("basket" + count);
            StringTokenizer stringTokenizer = new StringTokenizer(property, ",");
            Sale sale = new Sale(tax, dutyTax);
            while (stringTokenizer.hasMoreTokens()) {
                int quantity = Integer.decode(stringTokenizer.nextToken());
                String itemName = stringTokenizer.nextToken();
                sale.addLine(quantity, items.get(itemName));
            }
            sb.append(getInputText(sale, count));
            sb.append(getOutputText(sale, count));
        }
        return sb.toString();
    }

    private String getInputText(Sale sale, int count) {
        StringBuilder sb = new StringBuilder().append('\n');
        sb.append("-----------------------------------------------------------\n");
        sb.append("INPUT\n");
        sb.append("-----------------------------------------------------------\n");
        sb.append("Input ").append(count).append(":").append('\n');
        for (SaleLine saleLine : sale) {
            Item item = saleLine.getItem();
            sb.append(saleLine.getQuantity()).append(" ").append(item.getDescription()).append(" at ").append(formatNumber(item.getPrice())).append('\n');
        }
        sb.append("-----------------------------------------------------------");
        return sb.toString();
    }

    private String getOutputText(Sale sale, int count) {
        StringBuilder sb = new StringBuilder().append('\n');
        sb.append("-----------------------------------------------------------\n");
        sb.append("OUTPUT\n");
        sb.append("-----------------------------------------------------------\n");
        sb.append("Output ").append(count).append(":\n");
        float salesTaxes = 0;
        float total = 0;
        for (SaleLine saleLine : sale) {

            int quantity = saleLine.getQuantity();
            Item item = saleLine.getItem();

            float finalItemPrice = item.getPrice() * quantity;
            float taxRate = getTaxRate(sale, item);

            float salesTax = finalItemPrice * (taxRate / 100);
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
        sb.append("-----------------------------------------------------------\n");
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

    String formatNumber(float number) {
        return DECIMAL_FORMAT.format(number);
    }

    float round(float value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    float roundUpto5(float num) {
        num *= 100;
        num = (int) num;
        num = (float) Math.ceil(num / 5.00f) * 5.00f;
        num = (int) num;
        num /= 100;
        return num;
    }
}
