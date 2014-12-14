package mf2;

public class MyComparator<T> {
	
	private Comparable<T> tmp;
	private Comparable<T>[] toSort;
	
	public Comparable<T>[] quickSort(Comparable<T>[] arr){
		if(arr.length<=1){
			return arr;
		}
		this.toSort = arr;
		this.sort(0,arr.length-1);
		
		return arr;
	}
	
	protected void sort(int l, int r){
		while(l<r){
			int i = devide(l,r);
			if((i - l) <= (r - i) ){
				 sort(l, i-1);
				 l = i+1;
			 } else {
				 sort(i, r);
				 r = i-1;
			 }
		}
	}
	
	protected int devide(int l, int r){
		int middleIndex = (l+r)/2;
		Comparable<T> middleItem = toSort[middleIndex];
		
		for(int i = l; i <= r; i++){
			if( middleItem.compareTo((T) toSort[i]) > 0){
				for(int j = r; j>i; j--){
					if(middleItem.compareTo((T) toSort[j]) < 0){
						//podmianka!
					}
				}
			}
		}
		
		for(int i = l; i <= r; i++){
			if(toSort[i].equals(middleItem)){
				return i;
			}
		}
		return 0;
	}
	
}