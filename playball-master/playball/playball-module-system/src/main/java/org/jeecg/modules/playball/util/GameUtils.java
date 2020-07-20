package org.jeecg.modules.playball.util;

import java.util.*;

import org.jeecg.modules.playball.entity.PlayballTeam;

public class GameUtils {
	
	//保持第一位不变，后面位置循环走动
	public static <T> List<T> change(List<T> list) {
		T str = list.get(list.size()-1);
		list.remove(list.size()-1);
		list.add(1, str);
		return list;
	}
	
	public static <T> List<List<Map<String, T>>> createGroup(List<T> teamList){
		//list长度为偶数方可
		List<List<Map<String, T>>> macthList = new ArrayList<List<Map<String, T>>>();
		
		for(int index=1; index < teamList.size(); index++) {
			//System.out.println("第"+index+"次比赛阵容:");
			List<Map<String, T>> tempList = new ArrayList<Map<String, T>>();
			for(int i=0;i<teamList.size()/2;i++) {
			//System.out.print(team.get(i)+" vs "+team.get(team.size()-1-i));
				Map<String, T> map = new HashMap<String,T>();       //数据采用的哈希表结构
		        //给map中添加元素
		        map.put("team", teamList.get(i));
		        map.put("opponent", teamList.get(teamList.size()-1-i));
		        tempList.add(map);
			}
			teamList = change(teamList);
			macthList.add(tempList);
		}
		return macthList;
	}
	
	public static <T> List<Map<String, T>> createGroupEx(List<T> teamList){
		//list长度为偶数方可,此方法和上面的区分是该方法直接用list装，不需区分
		List<Map<String, T>> macthList = new ArrayList<Map<String, T>>();
		for(int index=1; index < teamList.size(); index++) {
			//System.out.println("第"+index+"次比赛阵容:");
			//Map<String, T> tempList = new HashMap<String, T>();
			for(int i=0;i<teamList.size()/2;i++) {
				Map<String, T> map = new HashMap<String,T>();       //数据采用的哈希表结构
		        //给map中添加元素
		        map.put("team", teamList.get(i));
		        map.put("opponent", teamList.get(teamList.size()-1-i));
		        macthList.add(map);
			}
			teamList = change(teamList);
		}
		return macthList;
	}
	
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
	
	//分小组
	public static <T> List<List<T>> grouping(List<T> list, int groupNum){
		
		int person = list.size()/groupNum; //每个小组有多少人
		int temp = list.size()%groupNum;//还剩余多少人没有分组
		int skip = 0;

		List<List<T>> groupList = new ArrayList<List<T>>();
		for(int i=0;i<groupNum;i++) {
			List<T> group = new ArrayList<T>();
			//subList出来的部分相当于引用，还是指向原内存，所有直接赋值后面的list插入会报错
			group.addAll(list.subList(skip, skip+person));
			skip = skip+person;
			groupList.add(group);
			//System.out.println("第"+i+"组:"+group);
		}
		//将剩余的人一次放入前面的分组中
		for(int i=0;i<temp;i++) {
			groupList.get(i).addAll(list.subList(skip,skip+1));
			skip = skip+1;
		}
		return groupList;
	}
	
	//数组合并同类向
	public static <T> List<List<T>> groupDataByCondition(List<T> srcList, Comparator<? super T> comparator) {

		List<List<T>> resultList = new ArrayList<>();

		for (int i = 0; i < srcList.size(); i++) {
			boolean isFindInCurrentGroups = false;
			//1.在现有组中查找
			 for (int groupIndex = 0; groupIndex < resultList.size(); groupIndex++) {
			     if (resultList.get(groupIndex).size() == 0 ||
			             comparator.compare(resultList.get(groupIndex).get(0), srcList.get(i)) == 0) {
			         //没有直接添加或者与第j组第一个匹配
			         resultList.get(groupIndex).add(srcList.get(i));
			         isFindInCurrentGroups = true;
			         break;
			     }
			 }
			 //2.在现有组中没查找到
			 if (!isFindInCurrentGroups) {
			     List<T> newGroupList = new ArrayList<>();
			     newGroupList.add(srcList.get(i));
			     resultList.add(newGroupList);
			 }
		}

		return resultList;
	}
}
