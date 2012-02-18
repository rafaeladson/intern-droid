package net.fiive.intern.random;

/**
 * This is a wrapper around a iterator that will make the iterator continuously provides new items, instead of throwing
 * {@link java.util.NoSuchElementException} when the iterator runs out of items.
 *
 * It receives a builder which should always returns new Iterators when an iterator reaches its end.
 */
public class CircularItemCursor<T> {

	private RandomIterator.Builder<T> iteratorBuilder;
	private RandomIterator<T> iterator;
	private T currentItem;

	public CircularItemCursor(RandomIterator.Builder<T> iteratorBuilder) {
		this.iteratorBuilder = iteratorBuilder;
		this.iterator = iteratorBuilder.build();
		goToNext();
	}

	/**
	 * When you call this, getCurrent() will then return the next item.
	 */
	public void goToNext() {
		if ( !iterator.hasNext()) {
			iterator = iteratorBuilder.build();
		}
		if ( iterator.hasNext()) {
			currentItem = iterator.next();
		} else {
			throw new IllegalStateException("Error: iteratorBuilder instantiated an iterator that has no items!");
		}
	}

	public T getCurrent() {
		if ( currentItem != null ) {
			return currentItem;
		} else {
			throw new IllegalStateException("Error: item is null. This should never happen");
		}
	}


}
