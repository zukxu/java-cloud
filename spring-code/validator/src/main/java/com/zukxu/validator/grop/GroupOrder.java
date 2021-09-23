package com.zukxu.validator.grop;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/9 0009 10:42
 */
@GroupSequence({GroupA.class, GroupB.class, Default.class})
public interface GroupOrder {}
