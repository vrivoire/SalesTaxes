package com.vrivoire.salestaxes.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author VincentRivoire
 */
public class Sale implements Iterable<SaleLine> {

    private final Tax BASIC_SALES_TAX;
    private final Tax DUTY_TAX;
    private final List<SaleLine> LINES = new ArrayList<>();

    /**
     *
     * @param basicSalesTax
     * @param dutyTax
     */
    public Sale(Tax basicSalesTax, Tax dutyTax) {
        this.BASIC_SALES_TAX = basicSalesTax;
        this.DUTY_TAX = dutyTax;
    }

    /**
     *
     * @param quantity
     * @param item
     */
    public void addLine(int quantity, Item item) {
        SaleLine saleLine = new SaleLine(quantity, item);
        LINES.add(saleLine);
    }

    @Override
    public Iterator<SaleLine> iterator() {
        return LINES.iterator();
    }

    /**
     *
     * @return
     */
    public Tax getBasicSalesTax() {
        return BASIC_SALES_TAX;
    }

    /**
     *
     * @return
     */
    public Tax getDutyTax() {
        return DUTY_TAX;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Sale [");
        LINES.stream().forEach((line) -> {
            builder.append(line);
        });
        builder.append("]");
        return builder.toString();
    }

}
