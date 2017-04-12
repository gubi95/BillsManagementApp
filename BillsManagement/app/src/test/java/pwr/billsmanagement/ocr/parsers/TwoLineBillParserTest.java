package pwr.billsmanagement.ocr.parsers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Squier on 12.04.2017.
 */
public class TwoLineBillParserTest {

    BillParser billParser;

    @Before
    public void init() {
        billParser = new TwoLineBillParser("asdf", new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void filterByLength() throws Exception {
        assertEquals("abcde hijk", billParser.filterByLength("abcde fg hijk", 2));
    }

    @Test
    public void checkIfWordIsPrice_WordIsNotPrice() throws Exception {
        assertEquals(false, billParser.lineIsPrice("ASDF"));
    }

    @Test
    public void checkIfWordIsPrice_WordIsPrice() {
        assertEquals(true, billParser.lineIsPrice("1 * 1,99 zl. 4,99 D"));
    }

}