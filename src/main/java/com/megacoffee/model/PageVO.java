package com.megacoffee.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 페이징 vo
 */
public class PageVO {
    private int page = 1;
	private int dataCount = 10;
	private int pageCount = 10;
	private int totalDataCount = 0;

	private List<Integer> pageList;

	/**
	 * 현재 page 번호
	 * @return
	 */
	public int getPage() {
		return page;
	}
	/**
	 * 현재 page 번호
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
		init();
	}

	/**
	 * 한 page의 data 갯수
	 * @return
	 */
	public int getDataCount() {
		return dataCount;
	}
	/**
	 * 한 page의 data 갯수
	 * @param dataCount
	 */
	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
		init();
	}

	/**
	 * 한 화면에 보여지는 page 갯수
	 * @return
	 */
	public int getPageCount() {
		return pageCount;
	}
	/**
	 * 한 화면에 보여지는 page 갯수
	 * @param pageCount
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
		init();
	}

	/**
	 * 전체 data 갯수
	 * @return
	 */
	public int getTotalDataCount() {
		return totalDataCount;
	}
	/**
	 * 전체 data 갯수
	 * @param totalDataCount
	 */
	public void setTotalDataCount(int totalDataCount) {
		this.totalDataCount = totalDataCount;
		init();
	}

	/**
	 * 첫 page 번호
	 * @return
	 */
	public int getFirstPageNumber()
	{
		return 1;
	}
	
	/**
	 * 마지막 page 번호
	 * @return
	 */
	public int getLastPageNumber()
	{
		return this.lastPageNumber;
	}
	
	/**
	 * 현재 화면에 보여지는 시작 page 번호
	 * @return
	 */
	public int getStartPageNumber()
	{
		return this.startPageNumber;
	}
	
	/**
	 * 현재 화면에 보여지는 끝 page 번호
	 * @return
	 */
	public int getEndPageNumber()
	{
		return this.endPageNumber;
	}
	
	/**
	 * 이전 화면에 보여지는 시작 page 번호
	 * @return
	 */
	public int getPreviousPageNumber()
	{
		return this.previousPageNumber;
	}
	
	/**
	 * 다음 화면에 보여지는 시작 page 번호
	 * @return
	 */
	public int getNextPageNumber()
	{
		return this.nextPageNumber;
	}
	
	/**
	 * 현재 보여지는 page의 번호 목록
	 * @return
	 */
	public List<Integer> getPageList()
	{
		return this.pageList;
	}
	
	public int getStartDataNumber()
	{
		return this.startDataNumber;
	}

	private int lastPageNumber;
	private int startPageNumber;
	private int endPageNumber;
	private int previousPageNumber;
	private int nextPageNumber;
	private int startDataNumber;
	private void init()
	{

		this.startDataNumber = (getPage() - 1) * getDataCount();

		//마지막 페이지 번호
		if(this.getDataCount() > 0)
		this.lastPageNumber = Math.max(this.getTotalDataCount()/this.getDataCount() + (this.getTotalDataCount()%this.getDataCount()==0 ? 0 : 1), 1);
		
		//다음 페이지 번호
		int target = (page/pageCount) - (page%pageCount==0 ? 1 : 0);
		int i = (target + 1) * pageCount + 1;
		this.nextPageNumber = Math.min(getLastPageNumber(), i);

		//이전 페이지 번호
		target = (page/pageCount) - (page%pageCount==0 ? 1 : 0);
		i = target * pageCount;
		this.previousPageNumber = Math.max(getFirstPageNumber(), i);

		//끝 페이지 번호
		i = page / pageCount * pageCount + (page % pageCount == 0 ? 0 : pageCount);
		this.endPageNumber = Math.min(getLastPageNumber(), i);

		//시작 페이지 번호
		target = (page/pageCount) - (page%pageCount==0 ? 1 : 0);
		i = target * pageCount + 1;
		this.startPageNumber = Math.max(getFirstPageNumber(), i);
		
		int count = this.endPageNumber - this.startPageNumber;
		this.pageList = new LinkedList<Integer>();
		for(int j = 0; j <= count; j++)
		{
			this.pageList.add((getStartPageNumber()+j));
		}
		
		if(this.pageList.size() == 0)
		{
			this.pageList.add(1);
		}
	}
}
