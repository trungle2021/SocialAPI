//package com.social.server.services.ElasticSearch.implement;
//
//import com.social.server.entities.User.Users;
//import com.social.server.services.ElasticSearch.UserElasticsearchService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.client.elc.NativeQuery;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.SearchHit;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.query.Query;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserElasticsearchTemplate implements UserElasticsearchService {
//    private final ElasticsearchOperations elasticsearchOperations;
//    @Override
//    public Page<Users> findByUsername(String username, Pageable pageable) {
//
//        Query query = NativeQuery.builder()
//                .withQuery(q->q.match(
//                        m->m.field("username")
//                                .query(username))
//                ).withPageable(pageable)
//                .build();
//
//        SearchHits<Users> searchHits = elasticsearchOperations.search(query, Users.class);
//
//        return new PageImpl<>(searchHits.stream().map(SearchHit::getContent).toList());
//    }
//}
