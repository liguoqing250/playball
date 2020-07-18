package org.jeecg.modules.playball.util;

import java.util.ArrayList;
import java.util.List;

import org.jeecg.modules.playball.entity.PlayballTeam;

public class GameUtils {
	
	public static <T> List<T> disorganizeList(List<T> sourceList){
		//创建一个新集合
		List<T> newList=new ArrayList<T>(); 		 				
  	  	int size=sourceList.size();
  	  	//根据list长度确定循环次数
  	  	for(int i=0;i<size;i++){
  	  		// 创建一个随机数，范围是0到list当前元素个数
  	  		int random=(int)(Math.random()*sourceList.size());
  	  		//把下标为随机出的数的元素添加到newList
  	  		newList.add(sourceList.get(random));
  	  		//从list中删除刚才添加到newList中的元素，以避免随机出重复的数
  	  		sourceList.remove(random);
  	  	}
  	  	
  	  	return newList;
	}
	
	//比赛生成，两两比赛
	public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {
		if (null == source || source.size() == 0 || n <= 0)
			return null;
		List<List<T>> result = new ArrayList<List<T>>();

		int sourceSize = source.size();
		int size = (source.size() / n) + 1;
		for (int i = 0; i < size; i++) {
			List<T> subset = new ArrayList<T>();
			for (int j = i * n; j < (i + 1) * n; j++) {
				if (j < sourceSize) {
					subset.add(source.get(j));
				}
			}
           if(subset.size()>0){
              result.add(subset);
           }
		}
		return result;
	}
}
