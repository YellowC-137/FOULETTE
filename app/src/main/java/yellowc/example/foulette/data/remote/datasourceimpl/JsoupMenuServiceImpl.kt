package yellowc.example.foulette.data.remote.datasourceimpl


import org.jsoup.Jsoup
import yellowc.example.foulette.data.remote.api.JsoupMenuService
import yellowc.example.foulette.data.remote.response.jsoup.JsoupMenuResponse

class JsoupMenuServiceImpl(private val remoteDataSource: RemoteDataSourceImpl) : JsoupMenuService {

    override suspend fun getMenu(url: String): List<JsoupMenuResponse> {
        val doc = Jsoup.connect(url).get().body()

        val menuList = mutableListOf<JsoupMenuResponse>()
        val subList = listOf("jnwQZ","mpoxR")
        val nameElements = doc.select("div[class=place_section_content] ul[class=jnwQZ] li a")
        val priceElements = doc.select("div[class=place_section_content] ul[class=jnwQZ] li em")

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