package com.ejtrio.springdatarest.adapter.hateoas;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Static class to find out whether a relation type is defined by the IANA.
 * Removed item from the collection.
 *
 * @see http://www.iana.org/assignments/link-relations/link-relations.xhtml
 */
@UtilityClass
public class IanaRels {

    private static final Collection<String> RELS;

    static {

        Collection<String> rels = new HashSet<String>();

        rels.addAll(Arrays.asList("about", "alternate", "appendix", "archives", "author", "blocked-by", "bookmark",
                "canonical", "chapter", "collection", "contents", "convertedFrom", "copyright", "create-form", "current",
                "describedby", "describes", "disclosure", "dns-prefetch", "duplicate", "edit", "edit-form", "edit-media",
                "enclosure", "first", "glossary", "help", "hosts", "hub", "icon", "index", "last", "latest-version",
                "license", "lrdd", "memento", "monitor", "monitor-group", "next", "next-archive", "nofollow", "noreferrer",
                "original", "payment", "pingback", "preconnect", "predecessor-version", "prefetch", "preload", "prerender",
                "prev", "preview", "previous", "prev-archive", "privacy-policy", "profile", "related", "restconf", "replies",
                "search", "section", "self", "service", "start", "stylesheet", "subsection", "successor-version", "tag",
                "terms-of-service", "timegate", "timemap", "type", "up", "version-history", "via", "webmention",
                "working-copy", "working-copy-of"));

        RELS = Collections.unmodifiableCollection(rels);
    }

    /**
     * Returns whether the given relation type is defined by the IANA.
     *
     * @param rel the relation type to check
     * @return
     */
    public static boolean isIanaRel(String rel) {
        return rel == null ? false : RELS.contains(rel);
    }
}
