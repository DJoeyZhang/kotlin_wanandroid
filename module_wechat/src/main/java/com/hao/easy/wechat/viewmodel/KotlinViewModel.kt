package com.hao.easy.wechat.viewmodel

import com.hao.easy.base.Config
import com.hao.easy.base.extensions.main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.wechat.model.Article
import com.hao.easy.wechat.repository.Api
import kotlin.properties.Delegates

class KotlinViewModel : BaseArticleViewModel() {

    private var key = "kotlin"

    private var refresh by Delegates.observable(Config.refresh) { _, old, new ->
        if (old != new) {
            invalidate()
        }
    }

    override fun onResume() {
        super.onResume()
        refresh = Config.refresh
    }

    override fun pageSize() = 10

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {
        Api.search(page - 1, key).main().subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }
}