package com.example.foulette.data.remote.datasourceimpl

import com.example.foulette.data.remote.api.JsoupMenuService
import com.example.foulette.data.remote.response.jsoup.JsoupMenuResponse
import com.example.foulette.domain.models.Result
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import timber.log.Timber

class JsoupMenuServiceImpl(private val remoteDataSource: RemoteDataSourceImpl) : JsoupMenuService {

    override suspend fun getMenu(url: String): List<JsoupMenuResponse> {
        //val response = jsoupMenuService.getMenu(url)
        val doc = Jsoup.connect(url).get()
        val menuList = mutableListOf<JsoupMenuResponse>()
        val subList = listOf("jnwQZ","mpoxR")
        //div place_section_content
        //ul ZUYk_
        //li P_Yxm 쭉
        //span zPfVt 가게 설명
        val menuElement : Element? = doc.select("div.place_section_content:has(ul.jnwQZ)").first()
        val subMenu : Elements = menuElement?.select("ul.jnwQZ > li")?: Elements()
        val nameElements = doc.select("div[class=place_section_content] ul[class=jnwQZ] li a")
        val priceElements = doc.select("div[class=place_section_content] ul[class=jnwQZ] li em")

        Timber.e("테스트 : serviceImpl")

        for (element in nameElements.indices) {
            val menuName = nameElements[element].text()
            val menuPrice = priceElements[element].text()
            val menu = JsoupMenuResponse(
                menu_name = menuName,
                menu_price = menuPrice
            )
            menuList.add(menu)
        }
        return menuList
    }
}