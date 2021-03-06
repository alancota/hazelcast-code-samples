import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICountDownLatch;

/**
 * @deprecated {@code HazelcastInstance.getCountDownLatch()} may lose strong consistency
 * in case of network failures and server failures.
 * Please use {@code HazelcastInstance.getCPSubsystem().getCountDownLatch()} instead.
 * You can see a code sample for the new impl in the cp-subsystem code samples module.
 */
@Deprecated
public class Leader {

    public static void main(String[] args) throws Exception {
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        ICountDownLatch latch = hz.getCountDownLatch("countDownLatch");

        System.out.println("Starting");
        // we init the latch with 1, since we only need to complete a single step.
        latch.trySetCount(1);

        // do some sleeping to simulate doing something
        Thread.sleep(30000);

        // now we do a countdown which notifies all followers
        latch.countDown();
        System.out.println("Leader finished");

        // we need to clean up the latch
        latch.destroy();
    }
}
