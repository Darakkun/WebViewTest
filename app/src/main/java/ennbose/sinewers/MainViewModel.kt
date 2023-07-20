package ennbose.sinewers

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import ennbose.sinewers.data.Link
import ennbose.sinewers.data.LinkDao
import ennbose.sinewers.data.LinkDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel() : ViewModel() {


    private lateinit var db: LinkDatabase

    var link: String? = null

    private lateinit var linkDao: LinkDao

    fun initDatabase(context: Context) {
        db = Room.databaseBuilder(
            context,
            LinkDatabase::class.java, "link_database"
        ).build()
        linkDao = db.linkDao()
        checkLink()
    }

    var score: Int = 0

    fun checkCorrect(questionNumber: Int, answer: Int): Boolean {
        return if (currentCorrect(questionNumber) == answer) {
            score++
            true
        } else false
    }

    private fun currentCorrect(questionNumber: Int): Int {
        when (questionNumber) {
            1 -> return 3
            2 -> return 4
            3 -> return 2
            4 -> return 4
            5 -> return 2
            6 -> return 3
            7 -> return 1
            8 -> return 4
            9 -> return 1
            10 -> return 3
        }
        return 0
    }

    fun checkLink() {
        link = null

        runBlocking(Dispatchers.IO) {
            val temp = linkDao.getLink()
            if (!temp.isNullOrEmpty())
                link = temp[0].value
        }
    }

    fun insertLink(newLink: String) {
        viewModelScope.launch(Dispatchers.IO) {
            linkDao.insertLink(Link(0, newLink))
        }
    }


    companion object {

        fun viewModelWithFragment(fragmentActivity: FragmentActivity): MainViewModel {
            class MainViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return try {
                        modelClass.getConstructor(MainViewModel::class.java)
                            .newInstance()
                    } catch (e: NoSuchMethodException) {
                        @Suppress("UNCHECKED_CAST")
                        return MainViewModel() as T
                    }
                }
            }

            val factory = MainViewModelFactory()
            return ViewModelProvider(fragmentActivity, factory)[MainViewModel::class.java]
        }

        fun viewModelWithActivity(activity: MainActivity): MainViewModel {
            class MainViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return try {
                        modelClass.getConstructor(MainViewModel::class.java)
                            .newInstance()
                    } catch (e: NoSuchMethodException) {
                        @Suppress("UNCHECKED_CAST")
                        return MainViewModel() as T
                    }
                }
            }

            val factory = MainViewModelFactory()
            return ViewModelProvider(activity, factory)[MainViewModel::class.java]
        }
    }

}