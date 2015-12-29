package com.fmtech.empf.utils;

import java.util.Stack;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/28 09:43
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/28 09:43  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public final class MainThreadStack<T> extends Stack<T> {

    public final boolean isEmpty() {
        Utils.ensureOnMainThread();
        return super.isEmpty();
    }

    public final T peek() {
        Utils.ensureOnMainThread();
        return super.peek();
    }

    public final T pop() {
        Utils.ensureOnMainThread();
        return super.pop();
    }

    public final T push(T paramT) {
        Utils.ensureOnMainThread();
        return super.push(paramT);
    }

}
