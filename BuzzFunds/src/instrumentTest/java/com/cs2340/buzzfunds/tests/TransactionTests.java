package com.cs2340.buzzfunds.tests;

import android.test.suitebuilder.annotation.SmallTest;
import com.cs2340.buzzfunds.Transaction;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Jeremy on 4/24/14.
 */
public class TransactionTests extends TestCase {

    @SmallTest
    public void testConstructorWithValidData() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate testInsDate = fmt.parseLocalDate("2013-04-20");
        LocalDate testEffDate = fmt.parseLocalDate("2013-07-04");
        Transaction txn = new Transaction("Test Transaction", 13.37, "deposit", "Test", testInsDate, testEffDate);
        Assert.assertEquals(txn.getName(), "Test Transaction");
        Assert.assertEquals(txn.getAmount(), 13.37);
        Assert.assertEquals(txn.getType(), "d");
        Assert.assertEquals(txn.getDate().toString(fmt), "2013-07-04");
    }
}
