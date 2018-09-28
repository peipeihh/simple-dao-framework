package com.pphh.dfw.core.transform;

import com.pphh.dfw.core.sqlb.ISqlBuilder;

import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public interface ITransformer {

    List<ShardTask> generate(ISqlBuilder sqlBuilder);

}
