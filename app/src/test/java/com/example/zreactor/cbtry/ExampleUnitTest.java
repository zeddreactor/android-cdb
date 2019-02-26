package com.example.zreactor.cbtry;

import org.junit.Test;

import java.io.IOException;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws IOException  {
        String teststrg = clickSubmitButton.connectToCDB("", "");
        String correct = "[\"_replicator\",\"_users\",\"asha-fusion-dev\",\"mytst\",\"newdb\"]";

        assertEquals(correct, teststrg);
    }


}

