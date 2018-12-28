package com.hr.global.util;

import java.util.Random;

public class RandomFunc {
	
	/**
	 * ָ��һ���������͵�size������0��size-1��һ���������
	 * @param size
	 * @return
	 */
	public static Integer[] getRandomOrder(int size){
		if(size<=0){
			return null;
		}
		Integer[] result=new Integer[size];
		for(int i=0; i<size; i++){
			result[i]=i;
		}
		long seed=System.currentTimeMillis();
		Random random=new Random(seed);
		int rand;
		int temp;
		for(int i=size-1; i>0; i--){
			rand=random.nextInt(i);
			temp=result[i];
			result[i]=result[rand];
			result[rand]=temp;
		}
		return result;
	}
	
	/**
	 * ��ȡ����1��������������n��������֮�������һ������
	 * @param n
	 * @return
	 */
	public static int randIntWithoutZero(Integer n){
		if(n<=1){
			return n;
		}else{
			long seed=System.currentTimeMillis();
			Random random=new Random(seed);
			int randNum=random.nextInt(n);
			while(randNum==0){
				randNum=random.nextInt(n);
			}
			return randNum;
		}
	}
}
