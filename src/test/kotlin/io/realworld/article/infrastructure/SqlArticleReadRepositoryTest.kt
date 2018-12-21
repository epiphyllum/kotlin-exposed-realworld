package io.realworld.article.infrastructure

import io.realworld.article.domain.ArticleGen
import io.realworld.article.domain.ArticleWriteRepository
import io.realworld.article.domain.TagGen
import io.realworld.article.domain.TagWriteRepository
import io.realworld.shared.TestDataConfiguration
import io.realworld.shared.TestTransactionConfiguration
import io.realworld.user.infrastructure.TestUserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest(
        classes = [
            ArticleConfiguration::class,
            TestDataConfiguration::class,
            TestTransactionConfiguration::class
        ]
)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)
@Transactional
internal class SqlArticleReadRepositoryTest {

    @Autowired
    lateinit var testUserRepository: TestUserRepository

    @Autowired
    lateinit var tagWriteRepository: TagWriteRepository

    @Autowired
    lateinit var articleWriteRepository: ArticleWriteRepository

    @Autowired
    lateinit var sqlArticleReadRepository: SqlArticleReadRepository

    @Test
    fun `should find article by id`() {
        val author = testUserRepository.insert()
        val tag = tagWriteRepository.save(TagGen.build())
        val article = articleWriteRepository.save(ArticleGen.build(author, listOf(tag)))

        assertThat(sqlArticleReadRepository.findBy(article.id)).isEqualTo(article)
    }

}
