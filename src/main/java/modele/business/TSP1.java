package modele.business;

import java.util.Collection;
import java.util.Iterator;
import modele.xmldata.Graphe;

/**
 *
 * @author C.Solnon
 */
public class TSP1 extends TemplateTSP {

	@Override
	protected int bound(Integer sommetCourant, Collection<Integer> nonVus) {
		return 0;
	}

	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, Collection<Integer> nonVus, Graphe g) {
		return new IteratorSeq(nonVus, sommetCrt, g);
	}

}
