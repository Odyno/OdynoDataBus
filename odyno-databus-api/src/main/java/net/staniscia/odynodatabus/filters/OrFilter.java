package net.staniscia.odynodatabus.filters;

import java.io.Serializable;

public class OrFilter<T extends Serializable> extends Filter<T> {
	private final Filter<T>[] filters;

	public OrFilter(final Filter<T>... filters) {
		this.filters = filters;
	}

	@Override
	public boolean passes(T object) {
		for (Filter<T> filter : filters) {
			if (filter.passes(object))
				return true; // short circuit
		}
		return false;
	}
}