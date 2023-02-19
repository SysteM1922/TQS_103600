package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

public class TqsStackTest {

	TqsStack<Integer> stack;

	@BeforeEach
	public void setUp() {
		stack = new TqsStack<Integer>();
	}

	@AfterEach
	public void tearDown() {
		stack = null;
	}

	@Test
	public void testIsEmpty() {
		assertTrue(stack.isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals(0, stack.size());
	}

	@Test
	public void testPush() {
		stack.push(1);
		assertEquals(1, stack.size());
	}

	@Test
	public void testPop() {
		stack.push(1);
		stack.push(2);
		assertEquals(2, stack.pop());
		assertEquals(1, stack.size());
	}

	@Test
	public void testPeek() {
		stack.push(1);
		stack.push(2);
		assertEquals(2, stack.peek());
		assertEquals(2, stack.size());
	}

	@Test
	public void testInitialStackIsEmpty() {
		assertTrue(stack.isEmpty());
	}

	@Test
	public void testInitialStackSizeIsZero() {
		assertEquals(0, stack.size());
	}

	@Test
	public void testPushSomeElementsAndCheckSize() {
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertEquals(3, stack.size());
	}

	@Test
	public void testPushAndPopElements() {
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertEquals(3, stack.pop());
		assertEquals(2, stack.pop());
		assertEquals(1, stack.pop());
	}

	@Test
	public void testPushPeekAndSize() {
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertEquals(3, stack.size());
		assertEquals(3, stack.peek());
		assertEquals(3, stack.size());
	}

	@Test
	public void testPopAllElementsAndSizeIsZero() {
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertEquals(3, stack.size());
		stack.pop();
		stack.pop();
		stack.pop();
		assertEquals(0, stack.size());
	}

	@Test
	public void testPopEmptyStack() {
		assertThrows(NoSuchElementException.class, () -> {
			stack.pop();
		});
	}

	@Test
	public void testPeekEmptyStack() {
		assertThrows(NoSuchElementException.class, () -> {
			stack.peek();
		});
	}

}