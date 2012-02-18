package net.fiive.intern.random;

import java.util.Arrays;
import java.util.List;

class NotSoRandomIteratorStub extends RandomIterator<Integer> {

	private List<Integer> items;
	private int currentIndex = 0;

	public NotSoRandomIteratorStub(List<Integer> items) {
		super(Arrays.asList(1, 2));
		this.items = items;
	}

	@Override
	public boolean hasNext() {
		return currentIndex < items.size();
	}

	@Override
	public Integer next() {
		Integer itemToReturn = items.get(currentIndex);
		currentIndex++;
		return itemToReturn;
	}
}
