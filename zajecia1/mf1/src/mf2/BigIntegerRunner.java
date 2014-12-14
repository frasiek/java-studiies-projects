package mf2;

public class BigIntegerRunner {

	public static void main(String[] args) {
		BigInteger bi = new BigInteger(100,25251L);
		System.out.println(bi.toString());
		
		BigInteger bi2 = BigInteger.parseBigInt("0000000000200");
		System.out.println(bi2.toString());
		
		BigInteger bi3 = BigInteger.parseBigInt("0000000000100");
		System.out.println(bi3.toString());
		
		bi2.add(bi3);
		System.out.println(bi2.toString());
		
		bi2.sub(bi3);
		System.out.println(bi2.toString());
		
		
		BigInteger bi4 = BigInteger.parseBigInt("0101000000123");
		System.out.println(bi4.toString());
		bi4.multiply(3);
		System.out.println(bi4.toString());
		
		
		System.out.println(bi2.compareTo(bi3));
	}

}
