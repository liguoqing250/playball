package org.jeecg.modules.appapi.service.impl;

import org.jeecg.modules.appapi.entity.PlayballNews;
import org.jeecg.modules.appapi.mapper.NewsMapper;
import org.jeecg.modules.appapi.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资讯表 服务实现类
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-30
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, PlayballNews> implements NewsService {

}
