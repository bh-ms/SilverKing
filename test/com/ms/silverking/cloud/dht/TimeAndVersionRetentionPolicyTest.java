package com.ms.silverking.cloud.dht;

import static com.ms.silverking.cloud.dht.TestUtil.getImplementationType;
import static com.ms.silverking.cloud.dht.ValueRetentionPolicy.ImplementationType.SingleReverseSegmentWalk;
import static com.ms.silverking.testing.AssertFunction.checkHashCodeEquals;
import static com.ms.silverking.testing.AssertFunction.checkHashCodeNotEquals;
import static com.ms.silverking.testing.AssertFunction.test_FirstEqualsSecond_FirstNotEqualsThird;
import static com.ms.silverking.testing.AssertFunction.test_Getters;
import static com.ms.silverking.testing.AssertFunction.test_NotEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ms.silverking.cloud.dht.TimeAndVersionRetentionPolicy.Mode;

public class TimeAndVersionRetentionPolicyTest {

	private static final Mode mCopy = Mode.wallClock;
	private static final Mode mDiff = Mode.mostRecentValue;
	
	private static final int mvCopy = 1;
	private static final int mvDiff = 2;
	
	private static final long tssCopy = 86_400;
	private static final long tssDiff = 86_399;
	
	private static final TimeAndVersionRetentionPolicy defaultPolicy     =     TimeAndVersionRetentionPolicy.template;
	private static final TimeAndVersionRetentionPolicy defaultPolicyCopy = new TimeAndVersionRetentionPolicy(mCopy, mvCopy, tssCopy);
	private static final TimeAndVersionRetentionPolicy defaultPolicyDiff = new TimeAndVersionRetentionPolicy(mDiff, mvDiff, tssDiff);
	
	private Mode getMode(TimeAndVersionRetentionPolicy policy) {
		return policy.getMode();
	}
	
	private int getMinVersions(TimeAndVersionRetentionPolicy policy) {
		return policy.getMinVersions();
	}
	
	private long getTimeSpanSeconds(TimeAndVersionRetentionPolicy policy) {
		return policy.getTimeSpanSeconds();
	}
	
	private long getTimeSpanMillis(TimeAndVersionRetentionPolicy policy) {
		return policy.getTimeSpanMillis();
	}

	private long getTimeSpanNanos(TimeAndVersionRetentionPolicy policy) {
		return policy.getTimeSpanNanos();
	}
	
	@Test
	public void testGetters() {
		Object[][] testCases = {
			{SingleReverseSegmentWalk, getImplementationType(defaultPolicy)},
			{Mode.wallClock,           getMode(defaultPolicy)},
			{1,                        getMinVersions(defaultPolicy)},
			{86_400L,                  getTimeSpanSeconds(defaultPolicy)},
			{86_400_000L,              getTimeSpanMillis(defaultPolicy)},
			{86_400_000_000_000L,      getTimeSpanNanos(defaultPolicy)},
			{mDiff,                    getMode(defaultPolicyDiff)},
			{mvDiff,                   getMinVersions(defaultPolicyDiff)},
			{tssDiff,                  getTimeSpanSeconds(defaultPolicyDiff)},
			{tssDiff * 1_000L,         getTimeSpanMillis(defaultPolicyDiff)},
			{tssDiff * 1_000_000_000L, getTimeSpanNanos(defaultPolicyDiff)},
		};
		
		test_Getters(testCases);
	}

	@Test
	public void testHashCode() {
		checkHashCodeEquals(   defaultPolicy, defaultPolicy);
		checkHashCodeEquals(   defaultPolicy, defaultPolicyCopy);
		checkHashCodeNotEquals(defaultPolicy, defaultPolicyDiff);
	}
	
	@Test
	public void testEqualsObject() {
		TimeAndVersionRetentionPolicy[][] testCases = {
			{defaultPolicy,     defaultPolicy,     defaultPolicyDiff},
			{defaultPolicyCopy, defaultPolicy,     defaultPolicyDiff},
			{defaultPolicyDiff, defaultPolicyDiff, defaultPolicy},
		};
		test_FirstEqualsSecond_FirstNotEqualsThird(testCases);
		
		test_NotEquals(new Object[][]{
			{defaultPolicy, InvalidatedRetentionPolicy.template},
			{defaultPolicy,   PermanentRetentionPolicy.template},
		});
	}

	@Test
	public void testToStringAndParse() {
		TimeAndVersionRetentionPolicy[] testCases = {
			defaultPolicy,
			defaultPolicyCopy,
			defaultPolicyDiff,
		};
		
		for (TimeAndVersionRetentionPolicy testCase : testCases)
			checkStringAndParse(testCase);
	}
	
	private void checkStringAndParse(TimeAndVersionRetentionPolicy policy) {
		assertEquals(policy, TimeAndVersionRetentionPolicy.parse( policy.toString() ));
	}

}
