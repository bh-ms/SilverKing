package com.ms.silverking.cloud.dht.common;

import com.ms.silverking.time.SystemTimeSource;

public class SystemTimeUtil {
    public static final SystemTimeSource systemTimeSource 
                            = SystemTimeSource.createWithMillisOrigin(DHTConstants.nanoOriginTimeInMillis);  
    
    public static final long systemTimeNanosToEpochMillis(long nanos) {
		//absTimeNanos = (absTimeMillis - nanoOriginTimeMillis) * nanosPerMilli;
    	return (nanos / 1000000) + DHTConstants.nanoOriginTimeInMillis;
    }
}
