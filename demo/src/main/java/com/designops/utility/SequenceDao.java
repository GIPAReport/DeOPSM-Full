package com.designops.utility;

import com.designops.exception. SequenceException;
public interface SequenceDao {
long getNextSequenceId(String key) throws SequenceException;
}
