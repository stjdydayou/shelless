package com.axungu.common.paginator.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * 包含“分页”信息的List
 * 
 * <p>
 * 要得到总页数请使用 toPaginator().getTotalPages();
 * </p>
 * 
 * @author badqiu
 * @author miemiedev
 */
public class PageList<E> extends ArrayList<E> implements Serializable {

	private static final long serialVersionUID = 1412759446332294208L;

	private Paginator paginator;

	public PageList() {
	}

	public PageList(Collection<? extends E> c) {
		super(c);
	}

	public PageList(Collection<? extends E> c, Paginator p) {
		super(c);
		this.paginator = p;
	}

	public PageList(Paginator p) {
		this.paginator = p;
	}

	/**
	 * 得到分页器，通过Paginator可以得到总页数等值
	 * 
	 * @return
	 */
	public Paginator getPaginator() {
		return paginator;
	}

	public PageResult<E> getPageResult() {
		return new PageResult<E>(this);
	}

	/**
	 * 因hessian 序列化问题会丢失分页数据修复 反序列化时读取第一条数据，为分页信息
	 
	public boolean add(E e) {
		if (super.size() == 0 && paginator == null) {
			if (e instanceof Paginator) {
				paginator = (Paginator) e;
				return true;
			} else {
				return super.add(e);
			}
		}
		return super.add(e);
	}*/

	/**
	 * 创建新的序列化对象，并放入分页信息
	 
	private Object writeReplace() throws ObjectStreamException {
		ArrayList<Object> list = new ArrayList<Object>();
		if (paginator != null)
			list.add(paginator);
		list.addAll(this);
		return list;
	}*/

}
