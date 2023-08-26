package com.social.server.services.Elasticsearch;

public interface ElasticsearchService<T> {
    void save(T object);
}
