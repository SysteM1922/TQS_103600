/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.lang.IllegalArgumentException;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[] { 50, 60 });
        setD = new BoundedSetOfNaturals(2);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    //@Disabled("TODO revise test logic")
    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        assertThrows(IllegalArgumentException.class, () -> setB.add(11));
        assertFalse(setB.contains(11), "add: added element not found in set.");
        assertEquals(6, setB.size(), "add: elements count not as expected.");

        assertThrows(IllegalArgumentException.class, () -> setC.add(50), "add: duplicate element detected.");

        assertThrows(IllegalArgumentException.class, () -> setD.add(-2), "add: not natural number detected.");
    }

    //@Disabled("TODO revise to test the construction from invalid arrays")
    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[] { 10, -20, -30 };

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @Test
    public void testIntersects() {
        assertTrue(setB.intersects(setC), "intersects: expected intersection between setB and setC");
        assertFalse(setB.intersects(setA), "intersects: expected no intersection between setB and setA");
    }

}
