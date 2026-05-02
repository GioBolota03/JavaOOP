package com.epam.rd.autocode.dllist;

import java.util.Optional;

public class DoublyLinkedListImpl {

	private Node head;
	private Node tail;

	private static class Node {
		Object value;
		Node prev;
		Node next;

		Node(Object value) {
			this.value = value;
		}
	}

	public boolean addFirst(Object e) {
		if (e == null) return false;

		Node newNode = new Node(e);

		if (head == null) {
			head = tail = newNode;
		} else {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		return true;
	}

	public boolean addLast(Object e) {
		if (e == null) return false;

		Node newNode = new Node(e);

		if (tail == null) {
			head = tail = newNode;
		} else {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		return true;
	}

	public Optional<Object> remove(Object e) {
		Node current = head;

		while (current != null) {
			if (current.value.equals(e)) {
				Object val = current.value;

				if (current.prev != null) {
					current.prev.next = current.next;
				} else {
					head = current.next;
				}

				if (current.next != null) {
					current.next.prev = current.prev;
				} else {
					tail = current.prev;
				}

				return Optional.of(val);
			}
			current = current.next;
		}

		return Optional.empty();
	}

	public void delete(int index) {
		if (index < 0) throw new IndexOutOfBoundsException();

		Node current = head;
		int i = 0;

		while (current != null && i < index) {
			current = current.next;
			i++;
		}

		if (current == null) throw new IndexOutOfBoundsException();

		if (current.prev != null) {
			current.prev.next = current.next;
		} else {
			head = current.next;
		}

		if (current.next != null) {
			current.next.prev = current.prev;
		} else {
			tail = current.prev;
		}
	}

	public boolean set(int index, Object e) {
		if (e == null) return false;

		Node current = head;
		int i = 0;

		while (current != null && i < index) {
			current = current.next;
			i++;
		}

		if (current == null) throw new IndexOutOfBoundsException();

		current.value = e;
		return true;
	}

	public int size() {
		int count = 0;
		Node current = head;

		while (current != null) {
			count++;
			current = current.next;
		}

		return count;
	}

	public Object[] toArray() {
		int size = size();
		Object[] result = new Object[size];

		Node current = head;
		int i = 0;

		while (current != null) {
			result[i++] = current.value;
			current = current.next;
		}

		return result;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node current = head;

		while (current != null) {
			sb.append(current.value);
			if (current.next != null) {
				sb.append(" ");
			}
			current = current.next;
		}

		return sb.toString();
	}
}