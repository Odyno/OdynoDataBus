package net.staniscia.odynodatabus.filters;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Classe per il filtraggio degli oggetti
 * 
 * @author Alessandro Staniscia
 * 
 * @param <T>
 */
public abstract class Filter<T> {

	/**
	 * Metodo su cui inserire la logica di filtro
	 * 
	 * @param object
	 * @return
	 */
	public abstract boolean passes(T object);

	public Class<T> getManagedType() {
		Class<T> result = null;
		Type type = this.getClass().getGenericSuperclass();

		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			Type[] fieldArgTypes = pt.getActualTypeArguments();
			result = extracted(fieldArgTypes);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private Class<T> extracted(Type[] fieldArgTypes) {
		return (Class<T>) fieldArgTypes[0];
	}

	public Iterator<T> filter(Iterator<T> iterator) {
		return new FilterIterator(iterator);
	}

	public Iterable<T> filter(final Iterable<T> iterable) {
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return filter(iterable.iterator());
			}
		};
	}

	private class FilterIterator implements Iterator<T> {
		private Iterator<T> iterator;
		private T next;

		private FilterIterator(Iterator<T> iterator) {
			this.iterator = iterator;
			toNext();
		}

		public boolean hasNext() {
			return next != null;
		}

		public T next() {
			if (next == null)
				throw new NoSuchElementException();
			T returnValue = next;
			toNext();
			return returnValue;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		private void toNext() {
			next = null;
			while (iterator.hasNext()) {
				T item = iterator.next();
				if (item != null && passes(item)) {
					next = item;
					break;
				}
			}
		}
	}
}
