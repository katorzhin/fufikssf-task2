package com.epam.rd.java.basic.practice2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListImpl implements List {

    private Node<Object> head;

    private int size = 0;

    @Override
    public void clear() {

        Node<Object> temp = head;

        while (temp != null) {
            head = temp.next;
            temp = head;
        }

        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    public Iterator<Object> iterator() {
        return new IteratorImpl();
    }

    private class IteratorImpl implements Iterator<Object> {

        private Node<Object> lastReturned;
        private Node<Object> nextNode = head;
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = nextNode;
            if (cursor < size) {
                nextNode = nextNode.next;
            }
            cursor++;
            return lastReturned.item;
        }

        @Override
        public void remove() {

            Node<Object> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (nextNode == lastReturned) {
                nextNode = lastNext;
            } else {
                cursor--;
            }
            lastReturned = null;

        }

    }

    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }

    @Override
    public void addFirst(Object element) {

        Node<Object> newNode = new Node<>(element, null);

        newNode.next = head;

        head = newNode;

        ++size;
    }

    @Override
    public void addLast(Object element) {

        Node<Object> newNode = new Node<>(element, null);

        newNode.next = null;

        if (head == null)
            head = newNode;
        else {
            Node<Object> last = head;

            while (last.next != null)
                last = last.next;

            last.next = newNode;
        }

        ++size;
    }

    @Override
    public void removeFirst() {

        if (head == null)
            return;

        Node<Object> currNode = head;

        head = currNode.next;

        --size;
    }

    @Override
    public void removeLast() {

        if (head == null)
            return;

        Node<Object> currNode = head;
        Node<Object> prev = new Node<>(null, null);

        if (currNode.next == null) {
            head = null;
            return;
        }

        while (true) {

            if (currNode.next == null) {
                prev.next = null;

                break;
            } else {
                prev = currNode;
                currNode = currNode.next;
            }
        }
        --size;
    }

    @Override
    public Object getFirst() {

        if (head == null)
            return null;

        return head.item;
    }

    @Override
    public Object getLast() {

        if (head == null)
            return null;

        Node<Object> currNode = head;

        while (true) {

            if (currNode.next == null)
                return currNode.item;
            else
                currNode = currNode.next;
        }
    }

    @Override
    public Object search(Object element) {

        Node<Object> currNode = head;

        if (currNode != null && element.equals(currNode.item))
            return currNode.item;

        while (currNode != null && !element.equals(currNode.item))
            currNode = currNode.next;

        if (currNode == null)
            return null;

        return currNode.item;
    }

    @Override
    public boolean remove(Object element) {
        if (element == null) {
            for (Node<Object> x = head; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<Object> x = head; x != null; x = x.next) {
                if (element.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {

        int iMax = size - 1;
        if (iMax == -1) {
            return "[]";
        }
        Object[] array = this.toArray();

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(array[i]);
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    public static void main(String[] args) {

        ListImpl listImpl = new ListImpl();
        listImpl.addFirst("First");
        listImpl.addLast("something1");
        listImpl.size();
        listImpl.getFirst();
        listImpl.getLast();
        listImpl.removeFirst();
        listImpl.removeLast();
        listImpl.search("something4");
        listImpl.remove("something2");
        listImpl.clear();

    }

    private Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<Object> x = head; x != null; x = x.next) {
            result[i++] = x.item;
        }
        return result;
    }

    private void unlink(Node<Object> x) {
        final Node<Object> next = x.next;
        final Node<Object> prev = findPrevNode(x);

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next != null) {
            x.next = null;
        }

        x.item = null;
        size--;
    }

    private Node<Object> findPrevNode(Object x) {
        Node<Object> curNode = head;
        Node<Object> prevNode = null;
        while (!x.equals(curNode)) {
            prevNode = curNode;
            if (curNode.next != null) {
                curNode = curNode.next;
            } else {
                break;
            }
        }
        return prevNode;
    }
}
