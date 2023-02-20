package yellowc.example.foulette.data.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import yellowc.example.foulette.data.local.dao.HistoryDao
import yellowc.example.foulette.data.local.entity.HistoryEntity

class HistoryPagingSource(
    private val historyDao: HistoryDao
):PagingSource<Int, HistoryEntity>() {
    override fun getRefreshKey(state: PagingState<Int, HistoryEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryEntity> {
        val page = params.key ?: 1
        return try {
            val items = historyDao.getHistoryList(page,params.loadSize)
            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}