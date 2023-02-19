package com.example.foulette.data.remote.datasourceimpl

import com.example.foulette.data.remote.api.JsoupMenuService
import com.example.foulette.data.remote.response.jsoup.JsoupMenuResponse
import com.example.foulette.domain.models.Result
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import timber.log.Timber

class JsoupMenuServiceImpl(private val remoteDataSource: RemoteDataSourceImpl) : JsoupMenuService {

    override suspend fun getMenu(url: String): List<JsoupMenuResponse> {
        Timber.e("데스트 임플 시작")
        //val response = jsoupMenuService.getMenu(url)
        val doc = Jsoup.connect(url).get().body()

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


        var num = 0
        for (element in nameElements.indices) {
            val menuName = nameElements[element].text()
            val menuPrice = priceElements[element].text()
            val menu = JsoupMenuResponse(
                menu_name = menuName,
                menu_price = menuPrice
            )
            menuList.add(menu)
            num+=1
            Timber.e("테스트 : serviceImpl -> ${num}")
        }

        val test = "https://map.naver.com/v5/search/%EC%86%8C%ED%98%B8%EC%A0%95%EC%B2%AD%EA%B3%84%EC%82%B0%EC%A0%90"
        val doc1: Document = Jsoup.connect(test).get()
        val menuElements = doc.select(".place_section_content ._3xPm2")
        for (menu in menuElements) {
            Timber.e("테스트 : 소호정 "+menu.text())
        }


        return menuList
    }
}