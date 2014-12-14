package mf2;

import java.util.Random;

public class ComparatorRunner {

	public static void main(String[] args) {
		Random rand = new Random();
		BigInteger[] toSort = {
				new BigInteger(100, rand.nextInt(10000)),
				new BigInteger(100, rand.nextInt(10000)),
				new BigInteger(100, rand.nextInt(10000)),
				new BigInteger(100, rand.nextInt(10000)),
				new BigInteger(100, rand.nextInt(10000)),
				new BigInteger(100, rand.nextInt(10000))
		};
		System.out.println("Not sorted:"+Integer.toString(toSort.length));
		for(BigInteger bi: toSort){
			System.out.println(bi.toString());
		}
		
		MyComparator comp = new MyComparator();
		comp.quickSort(toSort);
		
		System.out.println("Sorted:");
		for(BigInteger bi: toSort){
			System.out.println(bi.toString());
		}
	}

}
