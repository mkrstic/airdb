package app.web.model;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The Class DataCollection.
 *
 * @param <T> the generic type
 */
public class CollectionWrapper<T> {

	/** The data. */
	private Collection<T> data;
	
	/** The count. */
	private long count;
	
	/** The total. */
	private long total;
	
	private long elapsed;

	/**
	 * Instantiates a new data collection.
	 *
	 * @param data the data
	 * @param count the count
	 * @param total the total
	 */
	public CollectionWrapper(final Collection<T> data, final long count, final long total) {
		super();
		this.data = data;
		this.count = count;
		this.total = total;
	}
	
	public CollectionWrapper(final Collection<T> data, final long count, final long total, long elapsed) {
		super();
		this.data = data;
		this.count = count;
		this.total = total;
		this.elapsed = elapsed;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(final long count) {
		this.count = count;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(final long total) {
		this.total = total;
	}
	

	public long getElapsed() {
		return elapsed;
	}

	public void setElapsed(long elapsed) {
		this.elapsed = elapsed;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Collection<T> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(Collection<T> data) {
		this.data = data;
	}
	
}
