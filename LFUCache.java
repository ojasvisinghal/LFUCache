import java.util.*;
import java.util.Map.Entry;

public class LFUCache {

	int capacity;
	HashMap<Integer,Object> cache;
	TreeMap<Integer,LinkedList<Integer>> freqMap;
	

 	
	LFUCache(int capacity){
		
		this.capacity = (capacity <0) ? 0: capacity;;
		cache = new HashMap<Integer,Object>();
		freqMap =  new TreeMap<>();

	}
	
	public void removeKey(int key, LinkedList<Integer> list,int f) {
		
		list.remove((Integer)key);
		if(list.isEmpty()) {
			freqMap.remove(f);
		}
		}
	public int getLastF(int key) {
		for(Entry<Integer,LinkedList<Integer>> entry: freqMap.entrySet()) {
			LinkedList<Integer> l = entry.getValue();
			if(l.contains(key)) {
				int f = entry.getKey();
				//remove key
				removeKey(key,l,f);
				return f;
			}
		}
		return 0;
	}
	public void setF(int f, int key) {
		if(!freqMap.containsKey(f)) {
			freqMap.put(f, new  LinkedList<>());
		}
		freqMap.get(f).addLast((Integer)key);
	}
	public void incrementF(int key) {
		int fr = getLastF(key);
		setF(fr+1,key);
	}
	
	public Object get(int key) {
		
		if(capacity == 0 && cache.size() == 0) {
			return -1;
		}
		
		if(cache.containsKey(key)) {
			//increment freqq
			incrementF(key);
			return cache.get(key);
		}else {
			return null;
		}
		
	}
	
	public void put(int key , Object value) {
		
		if(capacity == 0) {
			return ;
		}
		
			if(cache.size() < capacity) {
				setF(1, key);
			}else {
				Entry<Integer,LinkedList<Integer>> minE = freqMap.firstEntry();
				LinkedList<Integer> minL = minE.getValue();
				cache.remove(minL.removeFirst());
				setF(1,key);
				if(minL.isEmpty()) {
					freqMap.remove(minE.getKey());
				}
			}
		cache.put(key,value);
		
	}
	
	public static void main(String args[]) {
		
		
		LFUCache cache = new LFUCache(3);
		cache.put(1, 3);
		cache.put(2, 3);
		cache.put(3, 3);
		cache.put(4, "eeee");
		cache.put(1, 3.4);
//		cache.get(1);
		System.out.println(cache.get(2));
		
		
		
	}
}
