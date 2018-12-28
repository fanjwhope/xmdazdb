package com.hr.global.util;

import java.util.Comparator;

/**
 * ≤Àµ•≈≈–Ú
 * 
 * @author ª∆∑…
 * 
 */
public class MenuComparator implements Comparator<MenuNode> {

	public int compare(MenuNode o1, MenuNode o2) {
		return  o1.getTabIndex() -  o2.getTabIndex();
	}
}
