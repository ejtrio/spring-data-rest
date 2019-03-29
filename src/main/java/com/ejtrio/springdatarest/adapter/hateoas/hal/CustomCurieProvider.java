package com.ejtrio.springdatarest.adapter.hateoas.hal;

import com.ejtrio.springdatarest.adapter.hateoas.IanaRels;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

/**
 * Custom implementation of {@link CurieProvider} rendering a single configurable {@link UriTemplate} based curie.
 * This implementation removes the word item from the IanaRels collection
 */
public class CustomCurieProvider implements CurieProvider {

    private final Map<String, UriTemplate> curies;
    private final String customCurie;

    /**
     * Creates a new {@link CustomCurieProvider} for the given name and {@link UriTemplate}. The curie will be used to
     * expand previously unprefixed, non-IANA link relations.
     *
     * @param name must not be {@literal null} or empty.
     * @param uriTemplate must not be {@literal null} and contain exactly one template variable.
     */
    public CustomCurieProvider(String name, UriTemplate uriTemplate) {
        this(Collections.singletonMap(name, uriTemplate));
    }

    /**
     * Creates a new {@link CustomCurieProvider} for the given curies. If more than one curie is given, no custom curie
     * will be registered. Use {@link #CustomCurieProvider(Map, String)} to define which of the provided curies shall be
     * used as the custom one.
     *
     * @param curies must not be {@literal null}.
     * @see #CustomCurieProvider(String, UriTemplate)
     * @since 0.19
     */
    public CustomCurieProvider(Map<String, UriTemplate> curies) {
        this(curies, null);
    }

    /**
     * Creates a new {@link CustomCurieProvider} for the given curies using the one with the given name as custom, which
     * means to expand unprefixed, non-IANA link relations.
     *
     * @param curies must not be {@literal null}.
     * @param customCurieName can be {@literal null}.
     * @since 0.19
     */
    public CustomCurieProvider(Map<String, UriTemplate> curies, String customCurieName) {

        Assert.notNull(curies, "Curies must not be null!");

        for (Map.Entry<String, UriTemplate> entry : curies.entrySet()) {

            String name = entry.getKey();
            UriTemplate template = entry.getValue();

            Assert.hasText(name, "Curie name must not be null or empty!");
            Assert.notNull(template, "UriTemplate must not be null!");
            Assert.isTrue(template.getVariableNames().size() == 1,
                    String.format("Expected a single template variable in the UriTemplate %s!", template.toString()));
        }

        this.customCurie = StringUtils.hasText(customCurieName) ? customCurieName
                : curies.size() == 1 ? curies.keySet().iterator().next() : null;
        this.curies = Collections.unmodifiableMap(curies);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.hal.CurieProvider#getCurieInformation()
     */
    @Override
    public Collection<? extends Object> getCurieInformation(Links links) {

        List<CustomCurieProvider.Curie> result = new ArrayList<CustomCurieProvider.Curie>(curies.size());

        for (Map.Entry<String, UriTemplate> source : curies.entrySet()) {

            String name = source.getKey();
            UriTemplate template = source.getValue();

            result.add(new CustomCurieProvider.Curie(name, getCurieHref(name, template)));
        }

        return Collections.unmodifiableCollection(result);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.hal.CurieProvider#getNamespacedRelFrom(org.springframework.hateoas.Link)
     */
    @Override
    public String getNamespacedRelFrom(Link link) {
        return getNamespacedRelFor(link.getRel());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.hal.CurieProvider#getNamespacedRelFrom(java.lang.String)
     */
    @Override
    public String getNamespacedRelFor(String rel) {

        boolean prefixingNeeded = customCurie != null && !IanaRels.isIanaRel(rel) && !rel.contains(":");
        return prefixingNeeded ? String.format("%s:%s", customCurie, rel) : rel;
    }

    /**
     * Returns the href for the {@link CustomCurieProvider.Curie} instance to be created. Will prepend the current application URI (servlet
     * mapping) in case the template is not an absolute one in the first place.
     *
     * @param name will never be {@literal null} or empty.
     * @param template will never be {@literal null}.
     * @return the {@link String} to be used as href in the {@link CustomCurieProvider.Curie} to be created, must not be {@literal null}.
     */
    protected String getCurieHref(String name, UriTemplate template) {

        if (template.toString().startsWith("http")) {
            return template.toString();
        }

        String applicationUri = ServletUriComponentsBuilder.fromCurrentServletMapping().build().expand().toString();
        return applicationUri.concat(template.toString());
    }

    /**
     * Value object to get the curie {@link Link} rendered in JSON.
     */
    protected static class Curie extends Link {

        private static final long serialVersionUID = 1L;

        private final String name;

        public Curie(String name, String href) {

            super(href, "curies");
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
