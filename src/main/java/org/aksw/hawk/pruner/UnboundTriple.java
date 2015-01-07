package org.aksw.hawk.pruner;

import java.util.Set;

import org.aksw.hawk.querybuilding.SPARQLQuery;

import com.google.common.collect.Sets;

public class UnboundTriple implements ISPARQLQueryPruner {
	private int maximalUnboundTriplePatterns = 1;

	private Set<SPARQLQuery> prune(Set<SPARQLQuery> queryStrings, int maximalUnboundTriplePatterns) {
		Set<SPARQLQuery> returnSet = Sets.newHashSet();
		// discard queries with more than x unbound triples away
		for (SPARQLQuery sparqlQuery : queryStrings) {
			int numberOfUnboundTriplePattern = 0;
			String[] split = new String[3];
			for (String triple : sparqlQuery.constraintTriples) {
				split = triple.split(" ");
				if (split[0].startsWith("?") && split[1].startsWith("?") && split[2].startsWith("?")) {
					numberOfUnboundTriplePattern++;
				}
			}
			if (numberOfUnboundTriplePattern <= maximalUnboundTriplePatterns) {
				returnSet.add(sparqlQuery);
			}
		}
		return returnSet;
	}

	@Override
	public Set<SPARQLQuery> prune(Set<SPARQLQuery> queries) {
		return prune(queries, maximalUnboundTriplePatterns);
	}

}