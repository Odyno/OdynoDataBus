/**
 * Simple implementation of DataBus
 */
package net.staniscia.odynodatabus.imp.mem;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;
import net.staniscia.odynodatabus.DataDistributionService;
import net.staniscia.odynodatabus.DataDistributionServiceStatus;
import net.staniscia.odynodatabus.DataSubscriber;
import net.staniscia.odynodatabus.Envelop;
import net.staniscia.odynodatabus.filters.Filter;

public class MemoryDDS implements DataDistributionService, Runnable {

    private static final Logger LOGGER = Logger.getLogger(MemoryDDS.class.getName());
    private static final long TIME_LIMIT_TO_ATTENDS = 1;
    private final List<DataSubscriber<?, ?>> subscribers = new LinkedList<DataSubscriber<?, ?>>();
    private final LinkedBlockingQueue<Envelop<?>> memorydata = new LinkedBlockingQueue<Envelop<?>>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final ReadWriteLock rwloaLock = new ReentrantReadWriteLock();
    private final Lock read = rwloaLock.readLock();
    private final Lock write = rwloaLock.writeLock();
    private final DataDistributionServiceStatus myStatus;

    public MemoryDDS() {
        super();
        myStatus = DataDistributionServiceStatus.BOOTING;
    }

    public void init() {
        executor.execute(this);
    }

    public void stop() {
        executor.shutdownNow();
    }

    private void notifyData(final Envelop<?> objectType) {
        executor.execute(new Runnable() {
            public void run() {
                memorydata.add(objectType);
            }
        });
    }

    @Override
    public <T extends Serializable> MemDataPublisher<T> getDataPublisher(Class<T> objectType) {
        return new MemDataPublisher<T>(this);
    }

    @Override
    public <T extends Serializable> boolean registerSubscriber(DataSubscriber<T, ? extends Filter<T>> subscriver) {
        subscriver.onChangeSystemStatus(DataDistributionServiceStatus.BOOTING);
        write.lock();
        try {
            final boolean reason = subscribers.add(subscriver);
            subscriver.onChangeSystemStatus(myStatus);
            return reason;
        } finally {
            write.unlock();
        }
    }

    @Override
    public <T extends Serializable> boolean unRegisterSubscriber(DataSubscriber<T, ? extends Filter<T>> subscriver) {
        write.lock();
        try {
            return subscribers.remove(subscriver);
        } finally {
            write.unlock();
        }
    }

    /**
     * none
     */
    @Override
    public void run() {
        try {
            while (DataDistributionServiceStatus.BOOTING.equals(myStatus)
                    || DataDistributionServiceStatus.RUNNING.equals(myStatus)) {
                Envelop<?> data = memorydata.poll(TIME_LIMIT_TO_ATTENDS, TimeUnit.SECONDS);
                if (data != null) {
                    read.lock();
                    try {
                        for (DataSubscriber<?, ?> listener : subscribers) {
                            notifyAtListner(listener, data);
                        }
                    } finally {
                        read.unlock();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void notifyAtListner(final DataSubscriber<?, ?> listener, final Envelop data) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Filter f = listener.getFilter();
                LOGGER.finest("\ndata: "
                        + data
                        + "data.getDataType(): "
                        + data.getContentType()
                        + " listener: "
                        + listener
                        + " f.getManagedType: "
                        + f.getManagedType()
                        + " isAssignagle: "
                        + data.getContentType().isAssignableFrom(f.getManagedType())
                        + " isEquals: "
                        + data.getContentType().equals(f.getManagedType()));
                if (data.getContentType().isAssignableFrom(f.getManagedType())) {

                    if (f.passes(data.getContent())) {
                        listener.handle(data);
                    }
                }
            }
        });
    }
}
