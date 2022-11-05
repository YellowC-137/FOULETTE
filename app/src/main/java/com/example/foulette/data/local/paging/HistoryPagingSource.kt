package com.example.foulette.data.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.foulette.data.local.dao.HistoryDao
import com.example.foulette.domain.mapper.HistoryMapper.mappingHistory
import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.util.PAGING_DELAY
import com.example.foulette.util.STARTING_KEY
import kotlinx.coroutines.delay

class HistoryPagingSource(
    private val historyDao: HistoryDao
):PagingSource<Int,HistoryResult>() {
    override fun getRefreshKey(state: PagingState<Int, HistoryResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryResult> {
        val page = params.key ?: STARTING_KEY

        if (page != STARTING_KEY) delay(PAGING_DELAY)

        return try {
            val measureResult = historyDao.getAll().mappingHistory()
            //mapping?
            LoadResult.Page(
                data = measureResult,
                prevKey = if (page == STARTING_KEY) null else page - 1,
                nextKey = if (measureResult.isEmpty()) null else page + 1
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}