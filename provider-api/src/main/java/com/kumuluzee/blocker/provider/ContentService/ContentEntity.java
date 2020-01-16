package com.kumuluzee.blocker.provider.ContentService;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "block_content")
@NamedQueries(value =
    {
        @NamedQuery(name = "ContentEntity.selectAll", query = "SELECT ce FROM ContentEntity ce")
    })
public class ContentEntity implements Serializable {

    @Id
    @Column(name = "entryid")
    private Integer entryid;

    @Column(name = "entity")
    private String entity;

    @Column(name = "relatedterm")
    private String relatedterm;

    @Column(name = "relevance")
    private Integer relevance;

    public Integer getEntryId() {
        return entryid;
    }

    public void setEntryId(Integer id) {
        this.entryid = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getRelatedTerm() {
        return relatedterm;
    }

    public void setRelatedTerm(String relatedterm) {
        this.relatedterm = relatedterm;
    }

    public Integer getRelevance() {
        return relevance;
    }

    public void setRelevance(Integer relevance) {
        this.relevance = relevance;
    }

    public String to_string(){
        return "Entry id: " + entryid + ", Entity: " + entity + ", Related term: " + relatedterm + ", Relevance: " + relevance;
    }

    public String to_json(){
        return "{\"entry_id\":\"" + entryid + "\", \"entity\":\"" + entity + "\", \"related_term\":\"" + relatedterm + "\", \"relevance\":" + relevance + "}";
    }
}