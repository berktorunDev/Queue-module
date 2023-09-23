package project.factory;

import java.util.Queue;

/**
 * An interface for creating queues of a specified type.
 *
 * @param <T> The type of elements that the queues created by this factory will
 *            hold.
 */
public interface QueueFactory<T> {

    /**
     * Creates a new queue of the specified type.
     *
     * @return A queue instance.
     */
    Queue<T> createQueue();
}
