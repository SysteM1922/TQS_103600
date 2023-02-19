package com.example;

import java.util.LinkedList;

public class TqsStack<T> {
	
	private LinkedList<T> collection;

	public TqsStack() {
		this.collection = new LinkedList<T>();
	}

	public boolean isEmpty() {
		return collection.isEmpty();
	}

	public int size() {
		return collection.size();
	}

	public T pop() {
		return collection.removeLast();
	}

	public void push(T element) {
		collection.add(element);
	}

	public T peek() {
		return collection.getLast();
	}
}
