package com.vrivoire.salestaxes.model;

/**
 *
 * @author VincentRivoire
 */
public class SaleLine {

    private final int QUANTITY;
    private final Item ITEM;

    /**
     *
     * @param quantity
     * @param item
     */
    public SaleLine(int quantity, Item item) {
        QUANTITY = quantity;
        ITEM = item;
    }

    /**
     *
     * @return
     */
    public int getQuantity() {
        return QUANTITY;
    }

    /**
     *
     * @return
     */
    public Item getItem() {
        return ITEM;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SaleLine [");
        builder.append("QUANTITY=").append(QUANTITY);
        builder.append(", ITEM=").append(ITEM);
        builder.append("]");
        return builder.toString();
    }

}
