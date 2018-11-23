package com.tc.website.elasticsearch.test;

import com.tc.website.elasticsearch.domain.User;
import com.tc.website.elasticsearch.repository.UserRepository;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/spring-context*.xml")
public class ElasticsearchTest {

    @Autowired
    private Client client;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    UserRepository userRepository;

    @Test
    public void createIndex() {
        try {

            // boolean indexFlagd = elasticsearchTemplate.deleteIndex(User.class);
            // 创建index
            boolean indexFlag = elasticsearchTemplate.createIndex(User.class);
            System.out.println("createIndex = " + indexFlag);

            // 创建映射
            boolean mapFlag = elasticsearchTemplate.putMapping(User.class);
            System.out.println("putMapping = " + mapFlag);

            // 添加数据
            User user = new User();
            user.setId("1");
            user.setUser("余永周");
            user.setDesc("云南农业大学毕业");
            user.setTitle("总经理");
            user =  userRepository.save(user);
            System.out.println("userRepository = " + user.toString());
            user.setId("2");
            user.setUser("余永周2");
            user.setDesc("云南农业大学毕业2");
            user.setTitle("总经理2");
            user =  userRepository.save(user);
            System.out.println("userRepository = " + user.toString());
            user.setId("3");
            user.setUser("3444");
            user.setDesc("云南农业大学毕业3");
            user.setTitle("总经理3");
            user =  userRepository.save(user);
            System.out.println("userRepository = " + user.toString());

            user.setId("余");
            user.setUser("4");
            user.setDesc("云南农业大学毕业4");
            user.setTitle("总经理4");
            user =  userRepository.save(user);
            System.out.println("userRepository = " + user.toString());

            user.setId("王晶晶");
            user.setUser("5");
            user.setDesc("云南农业大学毕业5");
            user.setTitle("总经理5");
            user =  userRepository.save(user);
            System.out.println("userRepository = " + user.toString());


            // 查询数据
            Optional<User> optionalUser =  userRepository.findById("1");
            System.out.println("findById = " + optionalUser.get().toString());

            // 分页查询数据
            Pageable pageable = new PageRequest(0, 10);
            Page<User> pageUser = userRepository.findAll(pageable);
            System.out.println("findAll Page<User> this size = " + pageUser.getNumberOfElements() + " , total = " + pageUser.getTotalElements());

            // 分页按条件查询
            // 需要查询的字段必须加入Index索引
            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    // 完全匹配查询
                    .must(QueryBuilders.termQuery("id", "1"))
                    // 通配符 * 查询
                    .must(QueryBuilders.wildcardQuery("user", "余永*"));
            Page<User> pageUser2 = userRepository.search(queryBuilder, pageable);
            System.out.println(" search Page<User> this size = " + pageUser2.getNumberOfElements() + " , total = " + pageUser2.getTotalElements());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
