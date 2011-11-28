package com.abiamiel.model;

public class FilteringOptions {
	
	public enum SortedOptions {
	
		NO_SORTED(1), ASC_SORTED(2), DESC_SORTED(3); 
		
		private int option;
		
		SortedOptions(int option){
			this.option = option;
		}
		
		public SortedOptions negate(){
			switch (option) {
			case 1:
				return SortedOptions.ASC_SORTED;
			case 2:
				return SortedOptions.DESC_SORTED;
			case 3:
				return SortedOptions.ASC_SORTED;
			}
			return SortedOptions.NO_SORTED;
		}
	}
	
	private SortedOptions orderAtSort;
	private SortedOptions titleSort;
	private SortedOptions customerSort;
	
	public FilteringOptions() {
		orderAtSort = SortedOptions.NO_SORTED;
		titleSort = SortedOptions.NO_SORTED;
		customerSort = SortedOptions.NO_SORTED;
	}

	public SortedOptions getOrderAtSort() {
		return orderAtSort;
	}

	public void setOrderAtSort(SortedOptions orderAtSort) {
		this.orderAtSort = orderAtSort;
	}

	public SortedOptions getTitleSort() {
		return titleSort;
	}

	public void setTitleSort(SortedOptions titleSort) {
		this.titleSort = titleSort;
	}

	public SortedOptions getCustomerSort() {
		return customerSort;
	}

	public void setCustomerSort(SortedOptions customerSort) {
		this.customerSort = customerSort;
	}
}
