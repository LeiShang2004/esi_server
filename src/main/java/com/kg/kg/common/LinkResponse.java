package com.kg.kg.common;

import java.util.List;

public class LinkResponse {
    private List<Link> links;

    // Constructors, getters, and setters
    public LinkResponse() {}

    public LinkResponse(List<Link> links) {
        this.links = links;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    // Nested class to hold source and target
    public static class Link {
        private String source;
        private String target;

        // Constructors, getters, and setters
        public Link() {}

        public Link(String source, String target) {
            this.source = source;
            this.target = target;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }
    }
}
