package net.fiive.intern.random;

import java.util.List;

public interface RandomItemRepository<T> {

	public List<T> findUpToNItems(int n);
}
