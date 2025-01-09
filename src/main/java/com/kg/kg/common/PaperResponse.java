package com.kg.kg.common;

import com.kg.kg.entities.KgPaper;

import java.util.List;

public class PaperResponse {
    private List<KgPaper> papers;

    // Constructors, getters, and setters
    public PaperResponse() {}

    public PaperResponse(List<KgPaper> papers) {
        this.papers = papers;
    }

    public List<KgPaper> getPapers() {
        return papers;
    }

    public void setPapers(List<KgPaper> papers) {
        this.papers = papers;
    }
}
