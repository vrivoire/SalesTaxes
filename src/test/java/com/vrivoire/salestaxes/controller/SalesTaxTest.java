package com.vrivoire.salestaxes.controller;

import com.vrivoire.salestaxes.model.Item;
import com.vrivoire.salestaxes.model.Sale;
import com.vrivoire.salestaxes.model.Tax;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author VincentRivoire
 */
public class SalesTaxTest {

    private static final Log LOG = LogFactory.getLog(SalesTaxTest.class);

    private Item item;
    private Tax basicSalesTax;
    private Tax dutyTax;
    private Sale sale;

    /**
     * Tests the SalesTaxes class
     */
    public SalesTaxTest() {
    }

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {

    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
        basicSalesTax = new Tax();
        basicSalesTax.setId(1L);
        basicSalesTax.setName("Basic sales tax");
        basicSalesTax.setIsImported(false);
        basicSalesTax.setRate(10.00f);

        dutyTax = new Tax();
        dutyTax.setId(2L);
        dutyTax.setName("Import duty");
        dutyTax.setIsImported(true);
        dutyTax.setRate(5.00f);

        item = new Item();
        item.setId(1L);
        item.setName("test item");
        item.setDescription("Item for test");
        item.setIsTaxable(true);
        item.setIsImported(true);
        item.setPrice(1.00f);

        sale = new Sale(basicSalesTax, dutyTax);
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getTaxRate method, of class SalesTaxes.
     */
    @Test
    public void testGetTaxRate() {
        System.out.println("getTaxRate");

        SalesTaxes instance = new SalesTaxes();

        item.setIsImported(true);
        item.setIsTaxable(true);
        float expResult = 15.00f;
        float result = instance.getTaxRate(sale, item);
        Assert.assertEquals(expResult, result, 0.00f);

        item.setIsImported(true);
        item.setIsTaxable(false);
        expResult = 5.00f;
        result = instance.getTaxRate(sale, item);
        Assert.assertEquals(expResult, result, 0.00f);

        item.setIsImported(false);
        item.setIsTaxable(true);
        expResult = 10.00f;
        result = instance.getTaxRate(sale, item);
        Assert.assertEquals(expResult, result, 0.00f);

        item.setIsImported(false);
        item.setIsTaxable(false);
        expResult = 0.00f;
        result = instance.getTaxRate(sale, item);
        Assert.assertEquals(expResult, result, 0.00f);
    }

    /**
     * Test of formatNumber method, of class SalesTaxes.
     */
    @Test
    public void testFormatNumber() {
        System.out.println("formatNumber");

        float number = 1.23456f;
        SalesTaxes instance = new SalesTaxes();
        String expResult = "1.23";
        String result = instance.formatNumber(number);
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of round method, of class SalesTaxes.
     */
    @Test
    public void testRound() {
        System.out.println("round");
        float value = 1.125f;
        int places = 2;
        SalesTaxes instance = new SalesTaxes();
        float expResult = 1.13f;
        float result = instance.round(value, places);
        Assert.assertEquals(expResult, result, 0.00);
    }

    /**
     * Test of roundUpto5 method, of class SalesTaxes.
     */
    @Test
    public void roundUpto5() {
        System.out.println("roundUpto5");
        SalesTaxes instance = new SalesTaxes();

        float value = 1.11f;
        float expResult = 1.15f;
        float result = instance.roundUpto5(value);
        Assert.assertEquals(expResult, result, 0.00);

        value = 1.16f;
        expResult = 1.20f;
        result = instance.roundUpto5(value);
        Assert.assertEquals(expResult, result, 0.00);
    }
}
