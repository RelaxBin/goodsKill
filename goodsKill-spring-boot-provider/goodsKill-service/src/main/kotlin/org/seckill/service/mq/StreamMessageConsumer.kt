package org.seckill.service.mq

import org.seckill.api.dto.SeckillMockRequestDto
import org.seckill.api.enums.SeckillSolutionEnum
import org.seckill.service.inner.SeckillExecutor
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer
import javax.annotation.Resource

/**
 * spring cloud stream消息消费者
 *
 * @author techa03
 * @date 2020/7/18
 */
@Configuration
open class StreamMessageConsumer {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Resource
    private lateinit var seckillExecutor: SeckillExecutor

    @Bean
    open fun seckill(): Consumer<SeckillMockRequestDto> {
        return Consumer { payload ->
            log.info("收到秒杀请求:$payload")
            val userPhone = payload.phoneNumber
            val seckillId = payload.seckillId
            seckillExecutor.dealSeckill(seckillId, userPhone, SeckillSolutionEnum.RABBIT_MQ.name)
        }

    }
}