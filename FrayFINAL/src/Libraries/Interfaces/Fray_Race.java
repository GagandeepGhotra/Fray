/**
 * 
 */
package Libraries.Interfaces;

import java.util.ArrayList;

import Libraries.ObjectClasses.FrayCard;

/**
 * @author gagan
 *
 */
public interface Fray_Race {
	
	ArrayList<FrayCard> addRaceCards();
	
	@Override
	String toString();
}
