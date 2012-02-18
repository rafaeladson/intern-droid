package net.fiive.intern.basic;

import com.google.common.base.Preconditions;

import java.util.Collection;

public class PreconditionsExtensions {

	public static <T> void checkCollectionDoesNotContainNull(Collection<T> collection) {
		checkCollectionDoesNotContainNull(collection, "Argument cannot contain null");
	}

	public static <T> void checkCollectionDoesNotContainNull(Collection<T> collection, String message) {
		Preconditions.checkNotNull(collection);
		for ( T item : collection ) {
			if ( item == null ) {
				throw new NullPointerException(message);
			}
		}
	}
}
