import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public abstract class MyList<E> implements java.util.List<E> {
	private List<String> e;

	//#combine lists

	public boolean combineLists(java.util.List<String> list1, java.util.List<String> list2) {
		java.util.List<String> list = list1;
		for (String frmList2 : list2) {
			list.add(frmList2);
		}
		return true;
	}
	
	@Override
	public boolean add(E e){
		java.util.List<String> list = this.e;
		java.util.List<String> list2 = (List<String>) e;
		for (String frmList2 : list2) {
			list.add(frmList2);
		}
		return true;		
	}
	
}
