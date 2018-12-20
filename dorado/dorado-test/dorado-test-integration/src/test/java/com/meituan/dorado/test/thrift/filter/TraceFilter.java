/*
 * Copyright 2018 Meituan Dianping. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.meituan.dorado.test.thrift.filter;

import com.meituan.dorado.common.RpcRole;
import com.meituan.dorado.rpc.handler.filter.Filter;
import com.meituan.dorado.rpc.handler.filter.FilterHandler;
import com.meituan.dorado.rpc.meta.RpcInvocation;
import com.meituan.dorado.rpc.meta.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SPI配置
 */
public class TraceFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(TraceFilter.class);

    @Override
    public RpcResult filter(RpcInvocation invocation, FilterHandler nextHandler) throws Throwable {
        FilterTest.invokeChainStr.append(this.getClass().getSimpleName());

        logger.info("TraceTestFilter");
        long start = System.currentTimeMillis();
        RpcResult result = nextHandler.handle(invocation);
        long end = System.currentTimeMillis();
        logger.info("TraceTestFilter cost={}", (end - start) / 1000);
        return result;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public RpcRole getRole() {
        return RpcRole.MULTIROLE;
    }
}
