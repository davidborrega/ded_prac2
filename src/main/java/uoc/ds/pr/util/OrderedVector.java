package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.exceptions.FullContainerException;
import edu.uoc.ds.exceptions.InvalidPositionException;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;

import java.util.Comparator;

public class OrderedVector<E> implements FiniteContainer<E> {

    private E[] elements;
    private int numberOfElements;
    private Comparator<E> comparator;

    public OrderedVector(int max, Comparator<E> comparator) {
        this.comparator = comparator;
        this.elements = (E[]) new Object[max];
        this.numberOfElements = 0;
    }

    @Override
    public boolean isFull() {
        return this.numberOfElements == this.elements.length;
    }

    @Override
    public boolean isEmpty() {
        return this.numberOfElements == 0;
    }

    @Override
    public int size() {
        return this.numberOfElements;
    }

    @Override
    public Iterator<E> values() {
        return new IteratorArrayImpl<E>(this.elements, this.numberOfElements, 0);
    }

    // Get element by position
    public E get(int position) {
        if (position < 0 || position >= this.numberOfElements) {
            return null;
        }
        return this.elements[position];
    }

    // Add elem into ordered vector
    public void add(E elem) {
        if (this.isFull()) {
            throw new FullContainerException();
        }
        this.elements[this.numberOfElements] = elem;
        this.numberOfElements++;
        // ordering vector
        this.orderVector(elem);
    }

    private void orderVector(E newElement) {
        E elem;
        int position = this.numberOfElements - 1;
        while (position > 0) {
            elem = this.get(position-1);
            if (this.comparator.compare(newElement, elem) < 0) {
                this.elements[position] = elem;
                this.elements[position-1] = newElement;
            } else {
                break;
            }
            position--;
        }
    }

    // Update element of vector
    public void update(E elem) {
        int position = this.getPosition(elem);
        if (position == -1) {
            throw new InvalidPositionException();
        }
        // First, remove current position to add new elem with the updated information to will be ordered inside vector.
        this.updateVector(elem, position);
    }

    private int getPosition(E elem) {
        int position = 0;
        for (Iterator<E> it = this.values(); it.hasNext();) {
            if (it.next().equals(elem)) {
                return position;
            }
            position++;
        }
        return -1;
    }

    private void updateVector(E elem, int position) {
        this.delete(position);
        this.add(elem);
    }

    // Delete a position of vector
    public void delete(int position) {
        for (int i = position; i < this.numberOfElements-1; i++) {
            this.elements[i] = this.elements[i+1];
        }
        this.elements[this.numberOfElements-1] = null;
        this.numberOfElements--;
    }

}
