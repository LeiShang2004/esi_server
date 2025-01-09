package com.kg.kg.common;


public class searchResponse {
    private PaperResponse paperResponse;
    private LinkResponse linkResponse;

    // Constructors, getters, and setters
    public searchResponse() {}

    public searchResponse(PaperResponse paperResponse, LinkResponse linkResponse) {
        this.paperResponse = paperResponse;
        this.linkResponse = linkResponse;
    }

    public PaperResponse getPaperResponse() {
        return paperResponse;
    }

    public void setPaperResponse(PaperResponse paperResponse) {
        this.paperResponse = paperResponse;
    }

    public LinkResponse getLinkResponse() {
        return linkResponse;
    }

    public void setLinkResponse(LinkResponse linkResponse) {
        this.linkResponse = linkResponse;
    }
}
