package com.github.javactic.futures;

import org.junit.Assert;
import org.junit.Test;

import com.github.javactic.Bad;
import com.github.javactic.Good;


public class OrPromiseTest {

    @Test
    public void doubleComplete() {
        OrPromise<String,String> p = OrPromise.make();
        OrFuture<String, String> future = p.complete(Good.of("good")).future();
        try {
            p.complete(Bad.of("bad"));
            Assert.fail("double complete");
        } catch (IllegalStateException e) {
            // expected
        }
        Assert.assertEquals("good", future.value().get().get());
    }
    
    @Test
    public void tries() {
        OrPromise<String,String> p = OrPromise.make();
        Assert.assertTrue(p.tryFailure("bad"));
        Assert.assertFalse(p.trySuccess("good"));
        OrFuture<String, String> future = p.future();
        Assert.assertEquals("bad", future.value().get().getBad());
    }
}