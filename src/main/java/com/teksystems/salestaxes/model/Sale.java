package com.teksystems.salestaxes.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author VincentRivoire
 */
public class Sale implements Iterable<SaleLine> {

    private final Tax basicSalesTax;
    private final Tax dutyTax;
    private final List<SaleLine> lines = new ArrayList<>();

    /**
     *
     * @param basicSalesTax
     * @param dutyTax
     */
    public Sale(Tax basicSalesTax, Tax dutyTax) {
        this.basicSalesTax = basicSalesTax;
        this.dutyTax = dutyTax;
    }

    /**
     *
     * @param quantity
     * @param item
     */
    public void addLine(int quantity, Item item) {
        SaleLine saleLine = new SaleLine(quantity, item);
        lines.add(saleLine);
    }

    @Override
    public Iterator<SaleLine> iterator() {
        return lines.iterator();
    }

    /**
     *
     * @return
     */
    public Tax getBasicSalesTax() {
        return basicSalesTax;
    }

    /**
     *
     * @return
     */
    public Tax getDutyTax() {
        return dutyTax;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Sale [");
        lines.stream().forEach((line) -> {
            builder.append(line);
        });
        builder.append("]");
        return builder.toString();
    }

}
