package mf2;

public class BigInteger implements Comparable<BigInteger>{
	protected int[] no;

	public BigInteger(int numOfDigits){
		this.initialize(numOfDigits);
	}

	public BigInteger(int numOfDigits, Long value){
		this.initialize(numOfDigits);

		String valueStr = value.toString();
		this.loadString(valueStr);
	}
	
	public BigInteger(int numOfDigits, int value){
		this.initialize(numOfDigits);

		String valueStr = Integer.toString(value);
		this.loadString(valueStr);
	}

	protected void initialize(int numOfDigits){
		this.no = new int[numOfDigits];

		for(int i = 0; i<this.no.length; i++){
			this.no[i] = 0;
		}
	}

	public void add(BigInteger bi){
		try{
			for(int i = no.length-1; i>=0; i--){
				no[i] += bi.no[i];
			}
			normalizeNo();
		} catch(Exception e){
			System.out.println("Cannot add! "+e.getMessage());
		}
	}

	public void sub(BigInteger bi){
		try{
			for(int i = no.length-1; i>=0; i--){
				no[i] -= bi.no[i];
			}
			normalizeNo();
		} catch(Exception e){
			System.out.println("Cannot sub! "+e.getMessage());
		}
	}

	public void multiply(Integer p){
		for(int i = no.length-1; i>=0; i--){
			no[i] *= p;
		}

		normalizeNo();
	}

	private void normalizeNo(){
		boolean normalized = true;
		for(int i = no.length-1; i>=0; i--){
			if(no[i]>=10){
				no[i-1] += 1;
				no[i] -= 10;
				normalized = false;
			}
			if(no[i]<0){
				no[i-1] -= 1;
				no[i] += 10;
				normalized = false;
			}
		}
		if(!normalized){
			normalizeNo();
		}
	}

	public String toString(){
		String out = new String();
		boolean foundGraterThanZero = false;
		for(int i: this.no){
			if(i==0 && !foundGraterThanZero){
				continue;
			}
			foundGraterThanZero = true;
			out += (Integer.toString(i));
		}
		return out;
	}

	public static BigInteger parseBigInt(String str){
		BigInteger bi = new BigInteger(str.length());
		bi.loadString(str);

		return bi;
	}

	protected void loadString(String valueStr){
		int lastIndex = no.length-1;
		for(int i = valueStr.length()-1; i >=0 ; i--){
			this.no[lastIndex--] = Character.getNumericValue(valueStr.charAt(i));
		}
	}

	@Override
	public int compareTo(BigInteger o) {
		int myLength = no.toString().length();
		int oLength = o.toString().length();
		if(myLength > oLength){
			return 1;
		} 
		if(myLength < oLength){
			return -1;
		}
		if(no.toString().equals(o.toString())){
			return 0;
		}
		return compareToByInsideNumber(o);
	
	}
	
	private int compareToByInsideNumber(BigInteger o){
		BigInteger myNormalized = BigInteger.parseBigInt(this.toString());
		BigInteger oNormalized = BigInteger.parseBigInt(o.toString());
		
		for(int i=0; i < myNormalized.no.length; i++){
			if(myNormalized.no[i] > oNormalized.no[i]){
				return 1;
			}
			if(myNormalized.no[i] < oNormalized.no[i]){
				return -1;
			}
		}
		return 0;
	}


}
