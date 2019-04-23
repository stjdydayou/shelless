package com.dliyun.platform.common.exception;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 4109764123357142460L;

    /**
     * 
     * 空构造
     */
    public ServiceException() {
        super("DaoException 异常");
    }

    /**
     * 
     * 自定义错误日志
     * @param e
     */
    public ServiceException(String e) {
        super(e);
    }

    /**
     * 只抛错误信息
     * @param e
     */
    public ServiceException(Throwable e) {
        super(e);
    }

    /**
     * 两者皆抛
     * @param er
     * @param e
     */
    public ServiceException(String er, Throwable e) {
        super(er, e);
    }

}
