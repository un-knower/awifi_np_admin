package com.awifi.np.admin.entity;
/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月17日 上午9:47:21
 * 创建作者：沈叶峰
 * 文件名称：NPPage.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 

	public class NPPage {
		
		private int pageSize; //每页显示记录数
		private int totalPage;		//总页数
		private int totalRecord;	//总记录数
		private int page;	//当前页
		private int currentResult;	//当前记录起始索引
		private boolean entityOrField;	//true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	 	private  List<Integer> pageList; //分页条 
	 	private Map<String,Object> params;
		
		public NPPage(){
			this.pageSize = 20;
		}
		
		public int getTotalPage() {
			if(totalRecord%pageSize==0)
				totalPage = totalRecord/pageSize;
			else
				totalPage = totalRecord/pageSize+1;
			return totalPage;
		}
		
		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}
		
		public int getTotalRecord() {
			return totalRecord;
		}
		
		public void setTotalRecord(int totalRecord) {
			this.totalRecord = totalRecord;
		}
		
		public int getPage() {
			if(page<=0)
				page = 1;
			if(page>getTotalPage())
				page = getTotalPage();
			return page;
		}
		
		public void setPage(int page) {
			this.page = page;
		}
		
		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

 

		public int getCurrentResult() {
			currentResult = (getPage()-1)*getPageSize();
			if(currentResult<0)
				currentResult = 0;
			return currentResult;
		}
		
		public void setCurrentResult(int currentResult) {
			this.currentResult = currentResult;
		}
		
		public boolean isEntityOrField() {
			return entityOrField;
		}
		
		public void setEntityOrField(boolean entityOrField) {
			this.entityOrField = entityOrField;
		}
	 
		public void setPageList(List<Integer> pageList) {
			this.pageList = pageList;
		}

		public List<Integer> getPageList() {
			int showTag = 5;//分页标签显示数量
			int startTag = 1;
			if(page>showTag){
				startTag = page-1;
			}
			setPageList(new ArrayList<Integer>()); 
			int endTag = startTag+showTag-1;
			for(int i=startTag; i<=totalPage && i<=endTag; i++){
				pageList.add(i);
			}
			
			return pageList;
		}

		public Map<String, Object> getParams() {
			return params;
		}

		public void setParams(Map<String, Object> params) {
			this.params = params;
		}
}